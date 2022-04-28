package com.example.posservice.service;

import com.example.posservice.dto.*;
import com.example.posservice.exception.SpringPosException;
import com.example.posservice.mapper.CustomerMapper;
import com.example.posservice.mapper.EmployeeMapper;
import com.example.posservice.mapper.UserMapper;
import com.example.posservice.model.*;
import com.example.posservice.repo.*;
import com.example.posservice.repo.repo_provider.AppUserRepoProvider;
import com.example.posservice.repo.repo_provider.AuthorityRepoProvider;
import com.example.posservice.repo.repo_provider.VerificationTokenRepoProvider;
import com.example.posservice.security.CurrentUserHolder;
import com.example.posservice.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Transactional
@Service
public class AppUserService {

    private final AppUserRepo appUserRepo;
    private final CustomerRepo customerRepo;
    private final EmployeeRepo employeeRepo;
    private final AuthorityRepo authorityRepo;
    private final VerificationTokenRepo verificationTokenRepo;
    private final AppUserRepoProvider appUserRepoProvider;
    private final JwtProvider jwtProvider;
    private final VerificationTokenRepoProvider verificationTokenRepoProvider;
    private final AuthorityRepoProvider authorityRepoProvider;
    private final CustomerMapper customerMapper;
    private final UserMapper userMapper;
    private final EmployeeMapper employeeMapper;
    private final CurrentUserHolder currentUserHolder;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(LoginRequest loginRequest) {

        List<_Authority> authorities = this.authorityRepoProvider.findByUsername(loginRequest.getUsername());

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                .map(a -> new SimpleGrantedAuthority(a.getAuthority()))
                .collect(Collectors.toSet());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                simpleGrantedAuthorities);

        // Authentication with username and password
        Authentication authentication = authenticationManager
                .authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        currentUserHolder.setUser((User) authentication.getPrincipal());

        // Generate Token and send it to client
        String token = this.jwtProvider.generateToken(authentication);
        String role = ((User) authentication.getPrincipal()).getAuthorities().toArray()[0].toString();

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .expiresAt(Instant.now().plusMillis(this.jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .role(role)
                .build();
    }

    public void logout() {
        // check user is logged in or not
        currentUserHolder.getCurrentUser();
        currentUserHolder.setUser(null);
    }

    public _User getCurrentUser() {
        String username = this.currentUserHolder.getCurrentUser().getUsername();
        return this.appUserRepoProvider.findFirstUserByUsername(username);
    }

    public void signUpEmployee(EmployeeDto employeeDto) {
        if (!isUsernameDuplicated(employeeDto.getUsername())) {
            _User user = employeeMapper.mapToUser(employeeDto);
            this.authorityRepo.saveAll(user.getAuthorities());
            this.appUserRepo.save(user);

            Employee targetEmployee = employeeMapper.mapToEmployee(employeeDto);
            targetEmployee.setUser(user);
            this.employeeRepo.save(targetEmployee);

            generateVerificationTokenAndSendMail(user);
        } else {
            throw new SpringPosException("User Already Exists");
        }
    }

    public void signUpCustomer(CustomerDto customerDto) {

        if (!isUsernameDuplicated(customerDto.getUsername())) {
            _User user = customerMapper.mapToUser(customerDto);

            this.authorityRepo.saveAll(user.getAuthorities());
            this.appUserRepo.save(user);

            Customer targetCustomer = customerMapper.mapToCustomer(customerDto);
            targetCustomer.setUser(user);
            this.customerRepo.save(targetCustomer);

            generateVerificationTokenAndSendMail(user);
        } else {
            throw new SpringPosException("User Already Exists");
        }
    }

    private String generateVerificationToken(_User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        this.verificationTokenRepo.save(verificationToken);
        return token;
    }

    private void generateVerificationTokenAndSendMail(_User user) {
        String token = generateVerificationToken(user);

        this.mailService.sendMail(new NotificationEmail(
                "Please Activate your Account",
                user.getEmail(),
                "please click on the below url to activate your account : \n" +
                        "http://localhost:8080/api/account/accountVerification/" + token));
    }

    private boolean isUsernameDuplicated(String username) {
        return appUserRepo.findFirstByUsername(username).isPresent();
    }

    public void activateAccount(String token) {
        VerificationToken verificationToken = this.verificationTokenRepoProvider
                .findVerificationTokenByToken(token);

        activateUser(verificationToken.getUser());
    }

    private void activateUser(_User user) {
        _User storedUser = getUserByUserName(user.getUsername());

        storedUser.setEnabled(true);
    }

    public void changeCredential(ChangeableCredentialInfo changeableCredentialInfo) {
        if (isLoggedIn()) {
            _User storedUser = getUserByUserName(currentUserHolder.getCurrentUser().getUsername());

            if (!passwordEncoder.matches(changeableCredentialInfo.getOldPassword(), storedUser.getPassword())) {
                throw new SpringPosException("Your Old Password is incorrect");
            }

            if (passwordEncoder.matches(changeableCredentialInfo.getNewPassword(), storedUser.getPassword())) {
                throw new SpringPosException("New Password and Old Password are the same");
            }

            // Refresh token
            this.verificationTokenRepo.deleteByUserId(storedUser.getId());
            // Send Mail
            generateVerificationTokenAndSendMail(storedUser);

            storedUser.setEmail(changeableCredentialInfo.getEmail());
            storedUser.setPassword(this.passwordEncoder.encode(changeableCredentialInfo.getNewPassword()));
            storedUser.setEnabled(false);

            // update User's Info
            this.appUserRepo.save(storedUser);

        } else {
            throw new SpringPosException("There is no logged User");
        }
    }

    public _User getUserByUserName(String username) {
        return this.appUserRepoProvider.findFirstUserByUsername(username);
    }

    public boolean isLoggedIn() {
        return this.currentUserHolder.getUser().isPresent();
    }

    public void blockAccount(String username) {
        _User storedUser = getUserByUserName(username);
        storedUser.setEnabled(false);
    }

    public UserResponse findUserByUsername(String username) {
        return userMapper.mapToUserResponse(getUserByUserName(username));
    }
}
