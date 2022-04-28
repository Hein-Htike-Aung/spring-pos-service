package com.example.posservice.model;

import com.example.posservice.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance
@DiscriminatorValue("EMPLOYEE")
public class Employee extends Member {

    @NotNull
    private Instant dob;
    @NotNull
    private String maritalStatus;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne(fetch = FetchType.LAZY)
    private _User user;
}
