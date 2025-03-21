package com.example.MujiFood.NaverUser;
import com.example.MujiFood.DataNotFoundException;
import com.example.MujiFood.NaverUser.NaverUserRepository;

import com.example.MujiFood.User.SiteUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NaverUserService {
	private NaverUserRepository userRepository;

    public NaverUser saveUser(String profile_nickname, String picture) {
    	NaverUser naverUser = new NaverUser();
    	naverUser.setProfile_nickname(profile_nickname);
    	naverUser.setPicture(picture);
        return userRepository.save(naverUser);
    }
}
