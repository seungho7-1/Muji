package com.example.MujiFood.Food;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.MujiFood.User.SiteUser;

import jakarta.persistence.CascadeType;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 고유 번호
	
	@Column(length = 50)
	private String foodname; // 식품명
	
	@Column(length = 30)
	private String kind; // 종류
	
	private String createDate; // 제조일자
	
	private LocalDateTime modifyDate; // 최종 수정 날짜

	@ManyToOne// 작성자와의 관계 설정
	private SiteUser siteuser;
	
	private String imagePath;
}
