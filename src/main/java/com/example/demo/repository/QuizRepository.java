package com.example.demo.repository;

import com.example.demo.entity.Question;

public interface QuizRepository {
	Question getQuestion();
	Question getQuestion(String id);
	int reset();		
	int getCorrectCount();
	String update(String id, String choice, String answer_id);
}
