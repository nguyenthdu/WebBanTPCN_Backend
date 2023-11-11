package com.example.backend.repositories;

import com.example.backend.entities.TypeFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeFoodRepository extends JpaRepository<TypeFood, Long> {
	TypeFood findTypeFoodById(Long typeFoodId);
	
	TypeFood findTypeFoodByName(String nameTypeFood);
}
