package com.example.backend.repositories;

import com.example.backend.entities.Category;
import com.example.backend.entities.TypeFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findCategoryByName(String categoryName);
	
	List<Category> findByTypeFood(TypeFood typeFood);
}
