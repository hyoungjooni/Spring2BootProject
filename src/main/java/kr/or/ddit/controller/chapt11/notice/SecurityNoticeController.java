package kr.or.ddit.controller.chapt11.notice;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/security/notice")
public class SecurityNoticeController {

	
	// notice 게시판의 목록 화면 
	@PreAuthorize("permitAll")
	@GetMapping("/list")
	public String noticeList() {
		log.info("noticeList() 실행");
		return "chapt11/notice/list";
	}
	
	// notice 게시판의 등록 화면 
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/register")
	public String noticeRegister() {
		log.info("noticeRegister() 실행");
		return "chapt11/notice/register";
	}
}
