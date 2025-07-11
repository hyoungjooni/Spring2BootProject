package kr.or.ddit.controller.chapt05;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/chapt05/test02")
public class Chapt05TestController {
	
	
	// 비동기 이벤트로 파일 데이터 전송 후, 결과 출력을 위한 테스트 페이지 
	@GetMapping("/ajaxForm")
	public String testAjaxForm() {
		
		return "chapt05/test02/ajaxForm";
		
	}
	
	// 위 테스트 페이지에서 비동기 요청 시, 목적지로 사용할 메서드 
	// 리턴 반환타입, 요청을 받을 메서드 방식은 여러분들이 자유롭게 설정 
	// 요청 URL : /chapt05/test02/upload 로 목적지 설정 
	@ResponseBody
	@PostMapping("/upload")
	public ResponseEntity<Map<String, Object>> testUpload(MultipartFile file) {
		log.info("업로드 시작");
		
		String fileName = file.getOriginalFilename();
		long fileSize = file.getSize();
		String contentType = file.getContentType();
		log.info("fileName : " + fileName);
		log.info("fileSize : " + fileSize);
		log.info("contentType : " + contentType);
		
		
		Map<String, Object> result = new HashMap<>();
		result.put("fileName",	fileName);
		result.put("fileSize", fileSize);
		result.put("contentType",contentType);
		
		return ResponseEntity.ok(result);
		
	}
}
