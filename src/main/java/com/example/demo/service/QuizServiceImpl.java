package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Question;
import com.example.demo.repository.QuizRepository;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
	
	private final QuizRepository quizRepository;
	
    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public Question getQuestion() {
        return quizRepository.getQuestion();
    }
    
    @Override
    public Question getQuestion(String id) {
        return quizRepository.getQuestion(id);
    }
    
    @Override
    public int reset() {
    	return quizRepository.reset();
    }
    
    @Override
    public int getCorrectCount() {
    	return quizRepository.getCorrectCount();
    }
    
    @Override
    public String update(String id, String choice, String answer_id) {
    	return quizRepository.update(id, choice, answer_id);
    }
    
}
