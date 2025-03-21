package com.example.MujiFood.NaverUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private NaverUserService userService;
    
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                        OAuth2User oAuth2User) throws IOException {
    	 // 사용자 정보 가져오기
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        String username = (String) properties.get("nickname");
        String profileImage = (String) properties.get("profile_image");

        // 사용자 정보 저장
        userService.saveUser(username, profileImage);

        // 리디렉션 또는 응답 처리
    }
}
