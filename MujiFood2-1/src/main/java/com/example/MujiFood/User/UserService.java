package com.example.MujiFood.User;

import com.example.MujiFood.DataNotFoundException;

import com.example.MujiFood.User.SiteUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;



@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	

	//회원 데이터 생성
	public SiteUser create(String username,String email,String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		return user;
		
	}
	
	
	//회원 데이터 조회
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
		if(siteUser.isPresent()) {
			return siteUser.get();
		}else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
	
	public SiteUser saveOrUpdateUser(SiteUser user) {
        return userRepository.save(user);
    }

    public Optional findUserById(Long id) {
        return userRepository.findById(id);
    }
    
    
}

