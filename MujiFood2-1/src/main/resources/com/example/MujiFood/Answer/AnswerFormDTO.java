package com.example.MujiFood.Answer;

import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class AnswerFormDTO {
	@NotEmpty(message = "내용은 필수 항목입니다.")
	private String content;
}
