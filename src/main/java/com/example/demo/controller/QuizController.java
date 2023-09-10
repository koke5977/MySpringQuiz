package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Question;
import com.example.demo.service.QuizService;

@Controller
public class QuizController {
	
	@Autowired
	private HttpSession session;
	
	private final QuizService quizService;
	
	public QuizController(QuizService quizService) {
        this.quizService = quizService ;
    }
	
	//問題数
	int question_number;
	
	//ホーム画面
	@RequestMapping("/")
	public String home() {
		
		//問題DBの解答履歴と正解履歴をリセット
		int count = quizService.reset();
		if(count == 0) {
			//正常にリセットできない場合、エラー画面を表示
			return "error";
		}
		
		//ホーム画面を表示
		return "home";
	}
	
	//クイズ画面（1問目）
	@GetMapping("/quiz")
	public String first_quiz(Model model) {
		
		//問題数のセッションを破棄
		session.removeAttribute("question_number");
		//1問目
		question_number = 1;
		
		//問題を取得
		Question question = quizService.getQuestion();
		if(question == null) {
			//取得できない場合、エラー画面を表示
			return "error";
		} 
		
		//問題数と問題データを保存
		model.addAttribute("question", question);
		model.addAttribute("question_number", question_number);
		session.setAttribute("question_number", question_number);
		
		//クイズ画面（1問目）を表示
		return "quiz";
	}
	
	//クイズ画面（2~10問目）
	@GetMapping("/quiz/next")
	public String quiz(Model model) {
		
		//セッションから問題数を取得
		question_number = (int)session.getAttribute("question_number");
		
		if(question_number >= 10) {
			//問題数が10の場合、合計正解数を取得し最終結果画面に遷移する
			int correct_count = quizService.getCorrectCount();
			model.addAttribute("correct_count", correct_count);
			return "all_result";
		}
		
		//問題を取得
		Question question = quizService.getQuestion();
		if(question == null) {
			return "error";
		} else if((int)session.getAttribute("question_number") == 0) {
			return "home";
		}

		//問題数を1増加
		question_number++;
		
		//問題数と問題データを保存
		model.addAttribute("question", question);
		model.addAttribute("question_number", question_number);
		session.setAttribute("question_number", question_number);
		
		//クイズ画面（2~10問目）を表示
		return "quiz";
	}
	
	//解説画面
	@PostMapping("/result")
	public String result(@RequestParam String id, String choice, 
			String answer_id, Model model) {
		
		//正解かどうかをチェックし、「正解」または「不正解」を取得
		String message = quizService.update(id, choice, answer_id);
		
		//問題IDに対する解答と解説文を取得
		Question question = quizService.getQuestion(id);
		
		//問題数と問題データを保存
		model.addAttribute("question", question);
		model.addAttribute("question_number", session.getAttribute("question_number"));
		model.addAttribute("message", message);

		//解説画面を表示
		return "result";	
	}
}
