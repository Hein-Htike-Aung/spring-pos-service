package com.example.posservice.service;

import com.example.posservice.dto.BrandDto;
import com.example.posservice.exception.SpringPosException;
import com.example.posservice.mapper.BrandMapper;
import com.example.posservice.model.Brand;
import com.example.posservice.model.Product;
import com.example.posservice.repo.BrandRepo;
import com.example.posservice.repo.repo_provider.BrandRepoProvider;
import com.example.posservice.repo.repo_provider.ProductRepoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandService {

    private final BrandRepo brandRepo;
    private final BrandMapper brandMapper;
    private final BrandRepoProvider brandRepoProvider;
    private final ProductRepoProvider productRepoProvider;

    public List<Brand> findBrandsByCategoryId(Long categoryId) {
        return this.brandRepoProvider.findBrandsByCategoryId(categoryId);
    }

    public List<BrandDto> getAll() {
        return this.brandRepo.findAll().stream()
                .map(brandMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public BrandDto getById(Long id) {
        return this.brandMapper.mapToDto(findBrandById(id));
    }

    public Brand findBrandById(Long id) {
        return this.brandRepoProvider.findById(id);
    }

    public void save(BrandDto brandDto) {
        if (!isExisted(brandDto.getBrandName())) {
            this.brandRepo.save(this.brandMapper.mapToBrand(brandDto));
        } else {
            throw new SpringPosException("Already Exists!");
        }
    }

    public void saveList(Collection<BrandDto> brandDtoCollection) {
        Set<BrandDto> brandDtoSet = new HashSet<>(brandDtoCollection);

        brandDtoSet.removeIf((b) -> isExisted(b.getBrandName()));

        if (brandDtoSet.size() != 0) {
            this.brandRepo.saveAll(brandDtoSet
                    .stream().map(this.brandMapper::mapToBrand).collect(Collectors.toList()));
        }
    }

    public boolean isExisted(String name) {
        return this.brandRepo.findByBrandName(name).isPresent();
    }

    public List<BrandDto> getByCategoryId(Long categoryId) {
        return this.findBrandsByCategoryId(categoryId).stream()
                .map(this.brandMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long brandId) {
        if (isDeletable(brandId)) {
            this.brandRepo.deleteById(brandId);
        } else {
            throw new SpringPosException("This Brand cannot be deleted");
        }
    }

    private boolean isDeletable(Long brandId) {
        List<Product> productsByBrandId = this.productRepoProvider.findProductByBrandId(brandId);
        return productsByBrandId.isEmpty();
    }

}
