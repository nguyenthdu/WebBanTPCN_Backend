package com.example.backend.repositories;

import com.example.backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findCategoryByName(String categoryName);
	
	Category findCategoryById(Long categoryId);
}
