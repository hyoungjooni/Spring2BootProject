package kr.or.ddit.vo.rest;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RestBoardFileVO {
	private MultipartFile item; 
	private int boNo; 
	private int fileNo;
	private String fileName;
	private long fileSize; 
	private String fileFancysize; 
	private String fileMime; 
	private String fileSavepath;
	private int fileDowncount; 
	
	public RestBoardFileVO() {
		
	}
	
	public RestBoardFileVO(MultipartFile item) {
		this.item = item; 
		this.fileName = item.getOriginalFilename();
		this.fileSize = item.getSize();
		this.fileMime = item.getContentType();
		this.fileFancysize = FileUtils.byteCountToDisplaySize(fileSize);
	}
}
