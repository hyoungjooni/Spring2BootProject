package kr.or.ddit.vo.rest;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.micrometer.common.util.StringUtils;
import lombok.Data;

@Data
public class RestBoardVO {
	private int boNo;
	private String boTitle;
	private String boContent; 
	private String boWriter; 
	private int boHit; 
	private String boDate; 
	private int cnt; 		// 업로드 된 파일 갯수 
	
	private List<MultipartFile> inputFiles; 
	private List<RestBoardFileVO> boardFileList; 
	private Integer[] delBoardNo;
	
	public void setInputFiles(List<MultipartFile> inputFiles) {
		this.inputFiles = inputFiles;
		
		if(inputFiles != null && inputFiles.size() > 0) {
			List<RestBoardFileVO> fileList = new ArrayList<>();
			for(MultipartFile item : inputFiles) {
				if(StringUtils.isBlank(item.getOriginalFilename())) {
					continue;
				}
				
				RestBoardFileVO boardFileVo = new RestBoardFileVO(item);
				fileList.add(boardFileVo);
			}
			
			this.boardFileList = fileList;
		}
	}
}
