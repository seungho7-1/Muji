package com.example.MujiFood;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.MujiFood.Food.FoodService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.MujiFood.Food.Food;
import com.example.MujiFood.Food.FoodRepository;

@SpringBootTest
class MujiFood21ApplicationTests {
	
	@Autowired
	private FoodService foodService;


	@Test
	void testJpa() {
	
	}
}
