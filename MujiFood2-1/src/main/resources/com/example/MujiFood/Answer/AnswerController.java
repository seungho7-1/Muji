package com.example.MujiFood.Answer;


import com.example.MujiFood.User.SiteUser;
import com.example.MujiFood.User.UserService;
import com.example.MujiFood.Question.Question;
import com.example.MujiFood.Question.QuestionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createAnswer(Model model , @PathVariable("id") Integer id, @Valid AnswerFormDTO answerForm, BindingResult bindingResult
			,Principal principal) {
		Question question = this.questionService.getQuestion(id);
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("question",question);
			return "question_detail";
		}
		// 답변을 저장한다.
		Answer answer = this.answerService.create(question, answerForm.getContent(),siteUser);
		
        return String.format("redirect:/question/detail/%s#answer_%s",answer.getQuestion().getId(), answer.getId());
	}

	@PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
	public String answerModify(AnswerFormDTO answerForm,
            @PathVariable("id") Integer id, Principal principal)
	{
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "answer_form";
	}
	
	
	
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerFormDTO answerForm, BindingResult bindingResult,
            @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
	public String answerDelelte(Principal principal,@PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
		}else {
			this.answerService.delete(answer);
		}
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}
	

}
