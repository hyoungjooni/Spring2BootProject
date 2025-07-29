package kr.or.ddit.controller.chapt12;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.controller.chapt12.service.IRestService;
import kr.or.ddit.vo.ServiceResult;
import kr.or.ddit.vo.rest.RestBoardFileVO;
import kr.or.ddit.vo.rest.RestBoardVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/api")
public class RESTAPIController {

	
	/*
	 	[ 12장 : REST API ]
	 	
	 	1. REST API 소개 
	 	
		 	웹 서비스를 사용하는 클라이언트에는 웹 브라우저만 있는 것이 아니다. 
		 	스마트폰, 스마트워치, 테블릿, CCTV 등 각종 센서 등이 모두 클라이언트이다. 
		 	IT 기기의 발전에 따라 지금도 수 많은 클라이언트가 만들어지고 있다. 
		 	서버는 이러한 모든 클라잉너트 요청에 응답해야 한다. 웹 브라우저뿐만 아니라 어떤 기기와도 기기에 맞는 뷰 페이지 (VIEW1...))를 
		  	응답해야한다. 그런데 이런 기기들은 앞으로 끝없이 나올텐데 그떄마다 서버가 일일이 대응하기란 쉽지 않다. 
		  	API는 서버의 자원을 클라이언트에 구애 받지 않고 사용할 수 있게 하는 설계 방식이다. 
		  	REST API 방식에서는 HTTP 요청에 대한 응답으로 서버의 자원을 반환한다. 서버에서 보내는 응답이 특정 기기에 종속되지 않도록 
		  	모든 기기에서 통용될 수 있는 데잍터를 반환한다. 
		  	REST API에서 서버는 클라이언트의 요청에 대한 응답으로 화면(view)이 아닌 데이터(data)를 전송한다. 
		  	이때 사용하는 응답 데이터는 JSON(JavaScript Objecct Notation)이다. 과거에는 응답 데이터로 XML을 많이 사용했지만, 최근에는 JSONdmf 
		  	보편적으로 많이 쓰고 있다. 
		  	
		  	
		  	REST API를 통해 요청을 보내고 처리 여부에 따라 성공 또는 실패의 응답결화를 받는다.
		  	이때, 응답으로 내보낼 수 있는 여러 상태코드가 존재한다. 
		  	
		  	# HTTP 상태 코드 
		  	- 1XX (정보) : 요청이 수신돼 처리 중이다. 
		  	- 2XX (성공) : 요청이 정상적으로 처리됨
		  	- 3XX (리다이렉션 메세지) : 요청을 완료하려면 추가 행동이 필요 
		  	- 4XX (클라이언트 요청 오류) : 클라이언트의 요청이 잘못돼 서버가 요청을 수행할 수 없음
		  	- 5XX (서버 응답 오류) : 서버 내부에 에러가 발생해 클라이언트 요청에 대해 적절히 수행하지 못함. 
		  	
	  	
	  	2. REST API 설정 
	  	
		  	# 컨트롤러 설정
		  	
		  	 - @RestController 또는 @Controller, @ResponseBody 설정 
			  	 > @RestController 어노테이션은 @Contrller와 @ResponseBody 어노테이션을 포함하고 있는 어노테이션이다. 
			  	   요청을 받기 위해 해당 클래스는 컨트롤러임을 명시하고 받은 요청을 응답으로 내보낼 시, 페이지 경로가 아닌 데이터가 응답으로 나갈 수 있도록 설정해준다.
			 
			 - @CrossOrigin("*") 설정 
			 	 > CORS(Cross-Origin resource sharing) 에러를 방지하기 위한 설정으로 서로 다른 도메인이 서로 간의 리소스를 공유 할 수 있도록 설정할 때 사용.
			 	   기존에 사용되었던 sameOrigin(동일 출처 정책)과는 반대의 개념이라고 생각하면 된다. 
			 	   
		
		3. REST API 만들기 
		
			# REST CRUD 작성 
			
				1) 게시판 등록 페이지 만들기 
				- 게시판 등록 화면 컨트롤러 메소드 만들리 (form:get) 
				- 게시판 등록 화면 만들기 (rest/form.jsp) 
				- 여기까지 확인 
					
					
	  	   		2) 게시판 등록 기능 만들기 
	  	   		- 게시판 등록 기능 컨트롤러 메소드 만들기 (insert:post) 
	  	   		- 게시판 등록 기능 서비스 인터페이스 메소드 만들기 
	  	   		- 게시판 등록 기능 서비스 클래스 메소드 만들기 
	  	   		- 게시판 등록 기능 Mapper 인터페이스 메소드 만들기 
	  	   		- 게시판 등록 기능 Mapper XML 쿼리 만들기 
	  	   		- 여기까지 확인 
	  	   		
	  	   		3) 게시판 상세정보 만들기 
	  	   		- 게시판 상세정보 컨트롤러 메소드 만들기 (detail:get) 
	  	   		- 게시판 상세정보 서비스 인터페이스 메소드 만들기 
	  	   		- 게시판 상세정보 서비스 클래스 메소드 만들기 
	  	   		- 게시판 상세정보 Mapper 인터페이스 메소드 만들기 
	  	   		- 게시판 상세정보 Mapper XML 쿼리 만들기 
	  	   		- 게시판 상세정보 페이지 만들기 (rest/detail.jsp)
	  	   		- 여기까지 확인 
	  	   		
	  	   
	  	   		
	  
	 	
	 	
	*/
	@Autowired
	private IRestService restService;
	
	@PostMapping("/insert")
	public ResponseEntity<Map<String, Object>> insert(RestBoardVO boardVO) throws Exception{
		log.info("# insert 요청");
		ServiceResult result = restService.insertBoard(boardVO);
		
		Map<String, Object> param = new HashMap<>();
		param.put("status", result.toString());
		param.put("value", boardVO.getBoNo());
		
		return new ResponseEntity<Map<String,Object>>(param, HttpStatus.OK);
		
	}
	
	
	
	// 게시판 상세정보 데이터 요청 
	@GetMapping("/detail/{no}")
	public ResponseEntity<RestBoardVO> detail(@PathVariable int no){
		
		log.info("# detail 요청");
		return new ResponseEntity<RestBoardVO>(restService.selectBoard(no), HttpStatus.OK);
		
	}
	
	@GetMapping("download/{fileNo}")
	public ResponseEntity<byte[]> download(@PathVariable int fileNo) throws Exception{
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		RestBoardFileVO boardFileVO = restService.getFileInfo(fileNo);
		String fileName = boardFileVO.getFileName();
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);	// 확장자 추출 
		MediaType mType = getMediaType(formatName);
		HttpHeaders headers = new HttpHeaders();
		
		try {
			in = new FileInputStream(boardFileVO.getFileSavepath());
			if(mType != null) {
				headers.setContentType(mType);
			}else {
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
				
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
			
		} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally{
			if(in != null) {
				in.close();
			}
		}
		return entity;
		
	}



	private MediaType getMediaType(String formatName) {
		if(formatName != null) {
			if(formatName.toUpperCase().equals("JPG")) {
				return MediaType.IMAGE_JPEG;
			}
			if(formatName.toUpperCase().equals("GIF")) {
				return MediaType.IMAGE_GIF;
			}
			if(formatName.toUpperCase().equals("PNG")) {
				return MediaType.IMAGE_PNG;
			}
		}
		return null;
	} 
	
	@PostMapping("/update/{no}")
	public ResponseEntity<String> update(@PathVariable int no, RestBoardVO boardVO) throws Exception {
		log.info("# update 요청");
		log.info("no : " + no);
		ServiceResult result =  restService.updateBoard(boardVO);
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}
	
	@PostMapping("/delete/{no}")
	public ResponseEntity<String> update(@PathVariable int no) throws Exception {
		log.info("# delete 요청");
		log.info("no : " + no);
		ServiceResult result =  restService.deleteBoard(no);
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}
	
	
	@GetMapping("/list")
	public ResponseEntity<List<RestBoardVO>> list(){
		log.info("# list 요청");
		return new ResponseEntity<List<RestBoardVO>>(restService.list(), HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
