package com.example.MujiFood.Food;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

import com.example.MujiFood.DataNotFoundException;
import com.example.MujiFood.User.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
@RequiredArgsConstructor
@Service
public class FoodService {
	
	private final FoodRepository foodrepository;
	
	
	//식품 리스트 모든 데이터 조회 메서드  & 최신순 조회
	public Page<Food> getFoodList(int page,String kw){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("modifyDate"));
		sorts.add(Sort.Order.desc("foodname"));
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		Specification<Food> spec = search(kw);
		return this.foodrepository.findAll(spec,pageable);
	}
	
	/*//식품 리스트 이름순으로 조회
	public Page<Food> getFoodNameList(int page,String kw){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("foodname"));
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		Specification<Food> spec = search(kw);
		return this.foodrepository.findByfoodname(spec,pageable);
	}*/
	
	//식품 id로 리스트 조회 메서드
	public Food getFooddetail(Integer id) {
		Optional<Food> food = this.foodrepository.findById(id);
		if(food.isPresent()) {
			return food.get();
		}else {
			throw new DataNotFoundException("food data not found");
		}
	}
	//식품 날짜 조회 메서드
	public Food dataTrans(Integer id) {
		Optional<Food> food = this.foodrepository.findById(id);
		if(food.isPresent()) {
			return food.get();
			
		}else {
			throw new DataNotFoundException("food data not found");
		}
	}

	public void create(String foodName, String kind, String createDate, SiteUser user,String imagePath) {
	    Food food = new Food();
	    food.setFoodname(foodName);
	    food.setKind(kind);
	    food.setCreateDate(createDate);
	    food.setModifyDate(LocalDateTime.now());
	    food.setSiteuser(user);
	    food.setImagePath(imagePath);
	    this.foodrepository.save(food);
	  }
	
	 public void modify(Food food, String foodName, String kind, String createDate, SiteUser user,String imagePath) {
		    food.setFoodname(foodName);
		    food.setKind(kind);
		    food.setCreateDate(createDate);
		    food.setModifyDate(LocalDateTime.now());
		    food.setSiteuser(user);
	        food.setImagePath(imagePath); // 이미지 경로 설정

		    this.foodrepository.save(food);
		  }
	//삭제

	public void delete(Food food) {
		this.foodrepository.delete(food);

		// TODO Auto-generated method stub
		
	}
	
	private Specification<Food> search(String kw) {
	    return new Specification<>() {
	        private static final long serialVersionUID = 1L;

	        @Override
	        public Predicate toPredicate(Root<Food> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            query.distinct(true);  // 중복을 제거

	            if (kw == null || kw.isEmpty()) {
	                return cb.conjunction();  // 검색어가 없을 때 모든 결과 반환
	            }

	            // foodName이 검색어를 포함하는 조건
	            return cb.like(cb.lower(q.get("foodname")), "%" + kw.toLowerCase() + "%");
	        }
	    };
	}
	
	
	
	@Value("${file.upload.dir}")
    private String uploadDir;

    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String filename = uuid + "_" + originalFilename;

        Path filePath = Paths.get(uploadDir, filename);

        // 디렉토리 존재 여부 확인 및 생성
        if (!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        Files.copy(file.getInputStream(), filePath);

        return "/image/" + filename;
    }
	
}
