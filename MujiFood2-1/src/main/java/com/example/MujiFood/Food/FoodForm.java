package com.example.MujiFood.Food;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FoodForm {
	@NotEmpty(message="식품 이름은 필수 항목입니다.")
	private String foodName;
	
	@NotEmpty(message="식품 이름은 필수 항목입니다.")
	private String kind;
	
	@NotEmpty(message="유통기한은 필수 항목입니다.")
	private String createDate;

}
