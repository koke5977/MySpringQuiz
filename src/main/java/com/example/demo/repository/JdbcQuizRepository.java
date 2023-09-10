package com.example.demo.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Question;

@Repository
public class JdbcQuizRepository implements QuizRepository {
	
	private final JdbcTemplate jdbcTemplate;

    public JdbcQuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Question getQuestion() {
    	try {
    		//未回答の問題をランダムで1問取得
    		return jdbcTemplate.queryForObject("SELECT * FROM question WHERE answered = FALSE ORDER BY RAND() LIMIT 1",
        		new DataClassRowMapper<>(Question.class));
    	} catch(EmptyResultDataAccessException ex) {
    		return null;
    	}
    }
    
    @Override
    public Question getQuestion(String id) {
    	try {
    		//問題IDに対する解答と解説文を取得
    		return jdbcTemplate.queryForObject("SELECT answer, description FROM question WHERE id = ?",
        		new DataClassRowMapper<>(Question.class), id);
    	} catch(EmptyResultDataAccessException ex) {
    		return null;
    	}
    }
    
    @Override
    public int reset() {
    	//回答履歴と正解履歴を全てリセット（FALSEに更新）
    	return jdbcTemplate.update("UPDATE question SET answered = FALSE, correct = FALSE");
    }
    
    @Override
    public String update(String id, String choice, String answer_id) {
    	String message;
    	if(choice.equals(answer_id)) {
    		//選択肢と解答が一致する場合、回答履歴と正解履歴をTRUEに更新
    		jdbcTemplate.update("UPDATE question SET answered = TRUE, correct = TRUE WHERE id = ? ", id);
    		message = "正解！";
    	}else {
    		//選択肢と解答が一致しない場合、回答履歴のみTRUEに更新
    		jdbcTemplate.update("UPDATE question SET answered = TRUE WHERE id = ? ", id);
    		message = "不正解…";
    	}
    	return message;
    }    
    
    @Override
    public int getCorrectCount() {
    	//正解した問題の個数（正解数）を取得
    	int correct_count = jdbcTemplate.queryForObject("SELECT count(*) FROM question WHERE correct = TRUE", int.class);
    	return correct_count;
    }

}
