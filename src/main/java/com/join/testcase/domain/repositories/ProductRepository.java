package com.join.testcase.domain.repositories;

import com.join.testcase.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @NonNull
    @Override
    Page<Product> findAll(@NonNull Pageable pageable);
}
