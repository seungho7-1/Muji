package com.example.MujiFood.Question;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;

import com.example.MujiFood.User.SiteUser;
import com.example.MujiFood.User.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import com.example.MujiFood.Answer.AnswerFormDTO;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import java.util.List;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	private final QuestionService questionService;
	private final UserService userService;
	
	@GetMapping("/list")
	//@ResponseBody
	public String list(Model model,@RequestParam(value="page",defaultValue="0") int page,@RequestParam(value="kw",defaultValue="")String kw) {
		Page<Question> paging = this.questionService.getList(page,kw);
		model.addAttribute("paging",paging);
		model.addAttribute("kw",kw);
		//List<Question> questionList = this.questionService.getList();
		//model.addAttribute("questionList",questionList);
		return "question_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model,@PathVariable("id") Integer id,AnswerFormDTO answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question",question);
		return "question_detail";
		
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionFormDTO questionForm,Model model) {
		model.addAttribute("questionForm",questionForm);
	    return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionFormDTO questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteuser = userService.getUser(principal.getName());
        questionService.create(questionForm.getSubject(), questionForm.getContent(), siteuser);
        return "redirect:/question/list";
    }
	
	//Get 방식 링크가 추가
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionFormDTO questionForm,@PathVariable("id") Integer id,Principal principal) {
		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
		}
		
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "question_form";
	}
	
	//수정후 저장하기 버튼을 누른 후 POST요청 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionFormDTO questionForm,BindingResult bindingResult,@PathVariable("id") Integer id,Principal principal) {
		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
		}
		
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		//제목,내용 수정
		
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return String.format("redirect:/question/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
	}
	
	
}

