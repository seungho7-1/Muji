package com.example.MujiFood;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	
	
	
	@GetMapping("/")
	public String root() {
		return "redirect:/mujifood/home";
	}
}
