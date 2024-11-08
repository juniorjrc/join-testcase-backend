package com.join.testcase.domain.repositories;

import com.join.testcase.domain.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @NonNull
    @Override
    Page<Category> findAll(@NonNull Pageable pageable);
}
