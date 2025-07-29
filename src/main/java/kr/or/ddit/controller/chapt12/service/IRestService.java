package kr.or.ddit.controller.chapt12.service;

import java.util.List;

import kr.or.ddit.vo.ServiceResult;
import kr.or.ddit.vo.rest.RestBoardFileVO;
import kr.or.ddit.vo.rest.RestBoardVO;

public interface IRestService {

	 public ServiceResult insertBoard(RestBoardVO boardVO) throws Exception;

	public RestBoardVO selectBoard(int no);

	public RestBoardFileVO getFileInfo(int fileNo);

	public ServiceResult updateBoard(RestBoardVO boardVO) throws Exception ;

	public ServiceResult deleteBoard(int no);

	public List<RestBoardVO> list();

}
