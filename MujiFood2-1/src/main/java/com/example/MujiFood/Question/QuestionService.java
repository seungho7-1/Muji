package com.example.MujiFood.Question;
import com.example.MujiFood.User.SiteUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.example.MujiFood.DataNotFoundException;


import com.example.MujiFood.Answer.Answer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	private final QuestionRepository questionRepostiory;
	
	public Page<Question> getList(int page,String kw){
		List<Sort.Order> sorts = new ArrayList();
		sorts.add(Sort.Order.desc("createDate"));
		//최신순으로 데이터 추가하기
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		Specification<Question> spec = search(kw);
		return this.questionRepostiory.findAll(spec,pageable);
	}
	
	public List<Question> getList(){
		return this.questionRepostiory.findAll();
		
	}
	
	public void modify(Question question, String subject,String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepostiory.save(question);
	}
	
	
	
	
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepostiory.findById(id);
		if(question.isPresent()) {
			return question.get();
		}else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	public void create(String subject,String content,SiteUser user) {
		Question q = new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setAuthor(user);
		q.setCreateDate(LocalDateTime.now());
		this.questionRepostiory.save(q);
	}
	
	public void delete(Question question) {
		this.questionRepostiory.delete(question);
	}
	
	private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거 
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목 
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용 
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자 
            }
        };
    }


	

}
