package com.example.demo.service;

import com.example.demo.entity.Question;

public interface QuizService {
	Question getQuestion();
	Question getQuestion(String id);
	int reset();
	int getCorrectCount();
	String update(String id, String choice, String answer_id);
}
