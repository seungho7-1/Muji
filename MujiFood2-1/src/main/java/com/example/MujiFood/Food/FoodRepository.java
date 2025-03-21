package com.example.MujiFood.Food;

import com.example.MujiFood.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodRepository extends JpaRepository<Food, Integer> {
	Page<Food> findAll(Pageable pageable);
	Page<Food> findAll(Specification<Food> spec,Pageable pageable);
	//Page<Food> findByfoodname(Specification<Food> spec,Pageable pageable);
}
