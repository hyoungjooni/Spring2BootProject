package kr.or.ddit.controller.chapt12;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/rest")
public class RestCrudController {

	// 게시판 등록 페이지
	@GetMapping("/form")
	public String form() {
		return "rest/form";
		
	}
	
	// 게시판 상세정보 페이지
	@GetMapping("/detail/{no}")
	public String form(@PathVariable int no) {
		log.info("#detail no -> " +  no);
		return "rest/detail";
	}
	
	// 게시판 수정폼 페이지
	@GetMapping("/update/{no}")
	public String updateForm(@PathVariable int no) {
		log.info("#updateForm no -> " +  no);
		return "rest/form";
	}
	
	// 게시판 목록 페이지
	@GetMapping("/list")
	public String list() {
		return "rest/list";
	}
}
