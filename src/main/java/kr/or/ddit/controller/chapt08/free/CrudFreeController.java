package kr.or.ddit.controller.chapt08.free;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.controller.chapt08.free.service.IFreeService;
import kr.or.ddit.vo.crud.Free;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/crud/free")
public class CrudFreeController {

	
	@Autowired
	private IFreeService service;
	
	
	// 1. 등록 페이지 띄우기 
	@GetMapping("/register")
	public String GetFreeRegister() {
		log.info("등록 페이지 띄우기");
		
		return "/chapt08/free/register";
		
	}
	
	// 2. 등록 기능 구현하기 
	@PostMapping("/register")
	public String PostFreeRegister(Free free, Model model) {
		log.info("등록기능 구현 실행");
		
		service.register(free);
		model.addAttribute("free", free);
		return "/chapt08/free/success";
	}
	
	
	// 3. 목록 불러오기 
	@GetMapping("/list")
	public String ListFree(Model model) {
		log.info("목록 출력");
		List<Free> freeList = service.list();
		model.addAttribute("freeList", freeList);
		return "chapt08/free/list";
	}
	
	
	// 4. 등록한 페이지 상세 정보 
	@GetMapping("/detail")
	public String GetFreeDetail(int freeNo, Model model) {
		log.info("등록 페이지 상세정보 가져오기");
		
		Free free = service.detail(freeNo);
		model.addAttribute("free", free);
		return "/chapt08/free/detail";
	}
	
	// 5. 수정 페이지 호출하기
	@GetMapping("/modify")
	public String ModifyFreeForm(int freeNo, Model model) {
		log.info("게시판 수정폼 가져오기");
		
		Free free = service.detail(freeNo);
		model.addAttribute("free", free);
		model.addAttribute("status", "u");
		return "/chapt08/free/register";
		 
	}
	
	@PostMapping("/modify")
	public String ModifyFree(Free free, Model model) {
		log.info("게시판 수정하기 시작");
		service.modify(free);
		model.addAttribute("msg", "수정완료");
		return "chapt08/free/success";
	}
	
	@PostMapping("/delete")
	public String DeleteForm(int freeNo, Model model) {
		log.info("삭제 처리");
		service.delete(freeNo);
		model.addAttribute("msg", "삭제완료");
		return "chapt08/free/success";
	}
	
	@PostMapping("/search")
	public String Search(Free free, Model model) {
		log.info("검색하기");
		List<Free> freeList = service.search(free);
		model.addAttribute("freeList" ,freeList);
		return "chapt08/free/list";
	}
}
