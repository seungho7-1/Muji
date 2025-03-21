package com.example.MujiFood.User;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser,Long> {
	Optional<SiteUser> findByUsername(String username);
	Optional findById(Long id);
}
