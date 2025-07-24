package kr.or.ddit.controller.chapt09.item02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.controller.chapt09.item02.service.IItemService2;
import kr.or.ddit.vo.Item2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/item2")
public class Chapter09FileUploadController2 {

	
	/*
	 	2. 비동기 방식 업로드 
	 	
	 	- 비동기 방식으로 여러 개의 이미지를 업로드 하는 파일 업로드 기능을 구현합니다. 
	 	
	 		# 환경 설정 
	 		
	 		- 의존 관계 정의 (pom.xml) 
	 			> commons 라이브러리 (io, fileupload) : 파일을 처리하기 위한 의존 라이브러리 
	 			> imgscalr-lib :  이미지 변환을 처리하기 위한 의존 라이브러리 
	 			
	 		# 파일 업로드 구현 설명 
	 			
	 			- 파일 업로드 등록 화면 컨트롤러 만들기 (Chapter09FileUploadController02) 
	 			- 파일 업로드 등록 화면 컨트롤러 메서드 만들기 (item2RegisterForm:get) 
	 			- 파일 업로드 등록 화면 만들기 (item2/register.jsp) 
	 			- 여기까지 확인 
	 			
	 		
	*/
	
	
	@Value("${kr.or.ddit.upload.path}")
	private String uploadPath;
	
	@Autowired
	private IItemService2 itemService;
	
	
	// 1. 등록화면 요청하기 
	@GetMapping("/register")
	public String item2RegisterForm() {
		return "chapt09/item2/register";
	}
	
	@PostMapping("/register")
	public String item2Register(Item2 item, Model model) {
		String[] files = item.getFiles();
		
		if(files != null && files.length > 0) {
			for(int i=0; i < files.length; i++) {
				log.info("files["+i+" ] : " + files[i] );
			}
		}
		
		itemService.register(item);
		model.addAttribute("msg", "등록 완료");
		return "chapt09/item2/success";
	}
	
	@GetMapping("/list")
	public String item2List(Model model) {
		List<Item2> itemList = itemService.list();
		model.addAttribute("itemList",itemList);
		return "chapt09/item2/list";
	}
	
	@GetMapping("/modify")
	public String item2ModifyForm(int itemId, Model model) {
		 Item2 item = itemService.read(itemId);
		 model.addAttribute("item", item);
		 return "chapt09/item2/modify";
	}
	
	@ResponseBody
	@GetMapping("/getAttach/{itemId}")
	public List<String> getAttach(@PathVariable int itemId){
		return itemService.getAttach(itemId);
	}
	
	@PostMapping("/modify")
	public String item2Modify(Item2 item, Model model) {
		String[] files = item.getFiles();
		
		if(files != null && files.length > 0) {
			for(String fileName : files) {
				log.info("file(modify) : " + fileName);
			}
		}
			
		itemService.modify(item);
		model.addAttribute("msg", "수정이 완료되었습니다.");
		return "chapt09/item2/success";
	}
	
	@GetMapping("/remove")
	public String itemRemoveForm (int itemId, Model model) {
		Item2 item = itemService.read(itemId); 
		model.addAttribute("item", item);
		return "chapt09/item2/remove";
	}
	
	@PostMapping("/remove")
	public String itemRemove(int itemId, Model model){
		itemService.remove(itemId);
		model.addAttribute("msg", "삭제 완료");
		
		return "chapt09/item2/success";
	}
	
	// uploadFile 메서드는 브라우저에서 넘겨받은 파일을 업로드하는 기능을 담당한다. 
	// produces 속성은 클라이언트에서 보내오는 비동기 요청에 대한 응답 타입을 설정할 때 사용한다. 
	// 헤더 정보 중, Accept에 설정된 MimeType들 중 하나가 응답으로 나가고, 클라이언트 요청에 속성들 중, dataType 속성에 
	// 명시한 타입과 produces 속성에 명시한 타입을 일치시켜야 해당 요청과 목적지가 연동될 수 있다. 
	// 물론, 해당 메서드의 응답 타입에 명시된 제네릭타입으로도 예측은 할 수 있지만, 무수히 많은 목적지 (메서드)들 중 하나를 명확하기 
	// 설정하기 위해서는 여러 옵션에 해당하는 속성정보를 맞출 필요는 있다.
	@PostMapping(value= "/uploadFile", produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> uploadFile(MultipartFile file) throws  Exception{		
		// register.jsp의 inputFile에서 보낸 파일 데이터가 MulitpartFile로 자동으로 매핑된다 
		
		log.info("file.originalFileName : " + file.getOriginalFilename());
		
		// 1. 클라이언트에서 보낸 파일 정보를 MultipartFile로 자동 매핑 
		String savedName = UploadFileUtils.uploadFile(
				uploadPath, 					// @Value로 주입받은 실제 경로 
				file.getOriginalFilename(), 	// 클라이언트에서 전송한 원본 파일명 
				file.getBytes()					// 실제 파일 데이터 
		);
		
		// 2. 실제 파일 저장을 유틸리티에 위임 
		return new ResponseEntity<String>(savedName, HttpStatus.OK);
		
	}
	
   @ResponseBody
   @GetMapping("/displayFile")
   public ResponseEntity<byte[]> displayFile(String fileName){
       InputStream in = null;
       ResponseEntity<byte[]> entity = null;

       try {
           String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
           // 너 사진이야!
           MediaType mType = MediaUtils.getMediaType(formatName);
           HttpHeaders headers = new HttpHeaders();
           in = new FileInputStream(uploadPath + "/" + fileName);

           if (mType != null) { //이미지 파일
              headers.setContentType(mType);
           }else {
        	  fileName = fileName.substring(fileName.indexOf("_") + 1);
              headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
              headers.add("Content-Disposition", "attachment;filename=\""+
              new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\"");
           }
           // 여기까지
           // => 어떤 파일인지
           // => 파일에 대한 실제 자료(정보)
           // 파일의 contentType이 뭔지
           
           entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
       } catch(Exception e) {
           e.printStackTrace();
           entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
       } finally {
          if(in != null) {
             try {
                in.close();
             }catch (IOException e) {
                e.printStackTrace();
             }
          }
       }
       
   return entity;
   }



	
	
	
	
	
	
	
	
	
	
	
	
	
}
