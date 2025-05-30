package com.example.MujiFood.Answer;
import com.example.MujiFood.User.SiteUser;

import java.util.Optional;
import com.example.MujiFood.DataNotFoundException;

import java.time.LocalDateTime;

import com.example.MujiFood.Question.Question;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service

public class AnswerService {
	private final AnswerRepository answerRepository;
	
	//답변 생성 메서드
	public Answer create(Question question, String content,SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		this.answerRepository.save(answer);
		return answer;
	}
	//답변 조회 메서드
	public Answer getAnswer(Integer id) {
		Optional<Answer> answer = this.answerRepository.findById(id);
		if(answer.isPresent()) {
			return answer.get();
		}else {
			throw new DataNotFoundException("answer not found");
			}
		}

	
	//답변 수정 메서드
	public void modify(Answer answer,String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}
	
	public void delete(Answer answer) {
		this.answerRepository.delete(answer);
	}

}
