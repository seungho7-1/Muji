package com.example.MujiFood.NaverUser;

import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
@RequiredArgsConstructor
@Controller
public class NaverUserController {
	
	@GetMapping("/kakao")
	public String kakaoLogin() {
		return "kakaologin";
	}
	
	@GetMapping("/oauth/kakao")
	public String kakaoOauthLogin() {
		return "kakaologin";
	}
	

}
