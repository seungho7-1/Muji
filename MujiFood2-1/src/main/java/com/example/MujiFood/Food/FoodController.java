package com.example.MujiFood.Food;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.MujiFood.User.SiteUser;
import com.example.MujiFood.User.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequestMapping("/mujifood")
@RequiredArgsConstructor
@Controller
public class FoodController {
	
	
	private final FoodService foodService;
	private final UserService userService;
	
	@GetMapping("/newfoodlist")
	public String newfoodlist(Model model,@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="kw",defaultValue=" ") String kw,Principal principal) {
		Page<Food> paging = this.foodService.getFoodList(page, kw);
		model.addAttribute("paging",paging);
		model.addAttribute("kw",kw);
		return "newfood_list";
	}
	@GetMapping("/newfoodlistA")
	public String newfoodlistA(Model model,@RequestParam(value="page",defaultValue="0")int page,@RequestParam(value="kw",defaultValue=" ") String kw,Principal principal) {
		Page<Food> paging = this.foodService.getFoodList(page, kw);
		model.addAttribute("paging",paging);
		model.addAttribute("kw",kw);
		return "newfood_listA";
	}
	
	@GetMapping("/newfoodlistB")
	public String newfoodlistB(Model model,@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="kw",defaultValue=" ") String kw,Principal principal) {
		Page<Food> paging = this.foodService.getFoodList(page, kw);
		model.addAttribute("paging",paging);
		model.addAttribute("kw",kw);
		return "newfood_listB";
	}
	@GetMapping("/newfoodlistC")
	public String newfoodlistC(Model model,@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="kw",defaultValue=" ") String kw,Principal principal) {
		Page<Food> paging = this.foodService.getFoodList(page, kw);
		model.addAttribute("paging",paging);
		model.addAttribute("kw",kw);
		return "newfood_listC";
	}
	
	@GetMapping("/newfoodlistD")
	public String newfoodlistD(Model model,@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="kw",defaultValue=" ") String kw,Principal principal) {
		Page<Food> paging = this.foodService.getFoodList(page, kw);
		model.addAttribute("paging",paging);
		model.addAttribute("kw",kw);
		return "newfood_listD";
	}
	
	//식품 리스트 조회 메서드
	@GetMapping("/list")
	public String list(Model model,@RequestParam(value="page",defaultValue="0")int page,@RequestParam(value="kw",defaultValue=" ") String kw,Principal principal) {
		Page<Food> paging = this.foodService.getFoodList(page, kw);
		model.addAttribute("paging",paging);
		model.addAttribute("kw",kw);
		
		return "food_list";
	}
	
	//식픔 리스트 상세 조회 메서드
	@GetMapping("/list/{id}")
	public String detailList(Model model , @PathVariable("id") Integer id) {
		Food food = this.foodService.getFooddetail(id);
		model.addAttribute("food",food);
		return "food_detail_list";
	}
	
	
	//식품 추가 메서드     
	@GetMapping("/create")
	public String foodCreate(FoodForm foodFormDto,Model model) {
		List<String> kind = Arrays.asList("과자", "초콜릿", "캔디", "음료", "밀키트", "국", "반찬", "스프/카레/소스");
	    model.addAttribute("kind", kind);
	    model.addAttribute("foodFormdto",foodFormDto); // FoodFormDTO 객체 생성
		return "food_create_form";
	}
	
	// @RequestParam(value="foodName") String foodName,@RequestParam(value="kind") String kind,
	// @RequestParam(value="CreateDate") String CreateDate
	// @Valid FoodForm foodForm,BindingResult bindingResult
	@PostMapping("/create")
    public String foodCreate(@Valid FoodForm foodFormdto, BindingResult bindingResult, Model model, Principal principal, @RequestParam(value = "image", required = false) MultipartFile image) {
        // 유효성 검사 오류가 있는지 확인
        if (bindingResult.hasErrors()) {
            List<String> kind = Arrays.asList("과자", "초콜릿", "캔디", "음료", "밀키트", "국", "반찬", "스프/카레/소스");
            model.addAttribute("kind", kind);
            model.addAttribute("foodFormdto", foodFormdto);
            return "food_create_form";
        }
        try {
            SiteUser siteUser = this.userService.getUser(principal.getName());
            String imagePath = foodService.uploadImage(image); // 이미지 업로드 및 URL 생성
            this.foodService.create(foodFormdto.getFoodName(), foodFormdto.getKind(), foodFormdto.getCreateDate(), siteUser, imagePath); // 이미지 URL 추가
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("createFailed", "이미 등록된 식품입니다.");
            List<String> kind = Arrays.asList("과자", "초콜릿", "캔디", "음료", "밀키트", "국", "반찬", "스프/카레/소스");
            model.addAttribute("kind", kind);
            model.addAttribute("foodForm", foodFormdto);
            return "food_create_form"; // 폼으로 돌아가기
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("createFailed", e.getMessage());
            List<String> kind = Arrays.asList("과자", "초콜릿", "캔디", "음료", "밀키트", "국", "반찬", "스프/카레/소스");
            model.addAttribute("kind", kind);
            model.addAttribute("foodForm", foodFormdto);
            return "food_create_form"; // 폼으로 돌아가기
        }

        return "redirect:/mujifood/list"; // 등록 후 리스트로 리다이렉트
    }

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String foodModify(@PathVariable("id") Integer id,Model model,Principal principal) {
		Food food = this.foodService.getFooddetail(id);// id로 모든 데이터 조회.
		 if (!food.getSiteuser().getUsername().equals(principal.getName())) { //권한 검증
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
	        }
		model.addAttribute("food",food);
		List<String> kind = Arrays.asList("과자", "초콜릿", "캔디", "음료", "밀키트", "국", "반찬", "스프/카레/소스");
	    model.addAttribute("kind", kind);
		return "food_modify_form";
	}
	
	@PostMapping({"/modify/{id}"})
    public String foodModify(@PathVariable("id") Integer id, @RequestParam("foodname") String foodName, @RequestParam("kind") String kind, @RequestParam("createDate") String createDate, Principal principal, @RequestParam(value = "image", required = false) MultipartFile image) {
        Food food = this.foodService.getFooddetail(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        String imagePath = food.getImagePath(); // 기존 이미지 경로 유지
        if (image != null && !image.isEmpty()) {
            try {
                imagePath = foodService.uploadImage(image); // 새 이미지 업로드 및 경로 설정
            } catch (Exception e) {
                // 이미지 업로드 실패 처리
                e.printStackTrace();
                // 오류 메시지를 모델에 추가하고 수정 폼으로 다시 이동
                return "food_modify_form";
            }
        }
        this.foodService.modify(food, foodName, kind, createDate, siteUser, imagePath);
        return "redirect:/mujifood/list";
    }
	//식품 보충 리스트 추가하기
	@GetMapping("/suppliments")
	public String supplimentlist(Model model,@RequestParam(value="page",defaultValue="0")int page,@RequestParam(value="kw",defaultValue=" ") String kw,Principal principal) {
		Page<Food> paging = this.foodService.getFoodList(page, kw);
		model.addAttribute("paging",paging);
		model.addAttribute("kw",kw);
		
		return "food_supplimentList";
	}
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	//보충 리스트
	@GetMapping("/supplimentLists")
	public String supplimentlist2(Model model,@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="kw",defaultValue=" ") String kw,Principal principal) {
		Page<Food> paging = this.foodService.getFoodList(page, kw);
		model.addAttribute("paging",paging);
		model.addAttribute("kw",kw);
		
		return "food_suppliment";
	}
	//식품 리스트 삭제
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String foodDelete(@PathVariable("id") Integer id) {
		Food food = this.foodService.getFooddetail(id);
		this.foodService.delete(food);
		return "redirect:/mujifood/list";
	}
}
