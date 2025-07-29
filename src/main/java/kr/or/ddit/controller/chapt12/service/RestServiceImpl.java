package kr.or.ddit.controller.chapt12.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.or.ddit.controller.chapt12.mapper.IRestMapper;
import kr.or.ddit.vo.ServiceResult;
import kr.or.ddit.vo.rest.RestBoardFileVO;
import kr.or.ddit.vo.rest.RestBoardVO;

@Service
public class RestServiceImpl implements IRestService {

	@Value("${kr.or.ddit.upload.path}") // C
	private String savePath;
	
	@Autowired
	private IRestMapper mapper; 
	
	@Override
	public ServiceResult insertBoard(RestBoardVO boardVO) throws Exception {
		ServiceResult result = null;
		
		boardVO.setBoWriter("a001");
		int status = mapper.insertBoard(boardVO);
		if(status > 0) {
			List<RestBoardFileVO> boardFileList = boardVO.getBoardFileList();
			uploadfiles(boardFileList, boardVO.getBoNo());
			
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	
	// 파일 업로드 메소드
	private void uploadfiles(List<RestBoardFileVO> boardFileList, int boNo) throws Exception {
		String saveLocate = savePath + "/rest/" + boNo; 
		
		File sFile = new File(saveLocate);
		if(!sFile.exists()) {
			sFile.mkdirs();
		}
		
		if(boardFileList != null && boardFileList.size() > 0) {
			for(RestBoardFileVO boardFileVO : boardFileList) {
				String saveName = UUID.randomUUID().toString();
				saveName += "_" + boardFileVO.getFileName().replaceAll("", "_");
				String path = saveLocate + "/" + saveName;
				
				boardFileVO.setBoNo(boNo);
				boardFileVO.setFileSavepath(path);
				mapper.insertBoardFile(boardFileVO);
				
				File destFile = new File(path);
				boardFileVO.getItem().transferTo(destFile);
			}
		}
		
		
	}


	@Override
	public RestBoardVO selectBoard(int no) {
		return mapper.selectBoard(no);
	}


	@Override
	public RestBoardFileVO getFileInfo(int fileNo) {
		return mapper.getFileInfo(fileNo);
	}


	@Override
	public ServiceResult updateBoard(RestBoardVO boardVO) throws Exception {
		ServiceResult result = null;
		
		int status = mapper.updateBoard(boardVO);
		if(status > 0) {
			// 1. 새롭게 추가될 파일 업로드 
			// 2. 기존에 업로드된 파일들 중 삭제할 파일을 삭제
			 List<RestBoardFileVO> boardFileList = boardVO.getBoardFileList();
			 uploadfiles(boardFileList, boardVO.getBoNo());
			 
			 
			 // 기존에 등록되어 있는 파일 목록들 중, 수정하기 위해서 x버튼을 눌러 삭제 처리로 넘겨준 파일 번호들 
			 Integer[] delBoardNos = boardVO.getDelBoardNo();
			 if(delBoardNos != null) {
				 for(int i = 0; i<delBoardNos.length; i++) {
					 RestBoardFileVO boardFileVO = mapper.selectBoardFile(delBoardNos[i]);
					 mapper.deleteBoardFile(delBoardNos[i]);
					 File file = new File(boardFileVO.getFileSavepath());
					 file.delete();
				 }
			 }
			 
			 
			 
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}


	@Override
	public ServiceResult deleteBoard(int no) {
		ServiceResult result = null;
		
		RestBoardVO boardVO = mapper.selectBoard(no);
		mapper.deleteBoardFileByBoNo(no);
		int status = mapper.deleteBoard(no);
		
		if(status > 0) {
			List<RestBoardFileVO> boardFileList = boardVO.getBoardFileList();
			if(boardFileList != null && boardFileList.size() > 0) {
				int lastIdx = boardFileList.get(0).getFileSavepath().lastIndexOf("/");
				String lastPath = boardFileList.get(0).getFileSavepath().substring(0,lastIdx);
				deleteFolder(lastPath);
			}
			result = ServiceResult.OK;
			
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}
	
	
	
	
	
	private void deleteFolder(String path) {
		// UUID 원본파일명 전 폴더경로를 folder 파일객체로 잡는다. 
		File folder = new File(path);
		
		try {
			if(folder.exists()) {
				// 폴더 안에 있는 파일들의 목록을 가져온다. 
				File[] fileList = folder.listFiles();
				
				for(int i = 0; i< fileList.length; i++) {
					if(fileList[i].isFile()) {		// 폴더안의 있는 파일이 파일일떄 
						fileList[i].delete();		// 폴더 안에 파일을 차례대로 삭제
						
					}else {	//파일이 아닌 폴더일 때
						// 폴더안의 있는 파일이 폴더일 떄 재귀함수 호출 (폴더안으로 들어갈라고 함)
						deleteFolder(fileList[i].getPath());
					}
				}
				folder.delete();	// 폴더 삭제
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}


	@Override
	public List<RestBoardVO> list() {
		return mapper.list();
	}
	
	
}
