package kr.or.ddit.controller.chapt12.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.rest.RestBoardFileVO;
import kr.or.ddit.vo.rest.RestBoardVO;

@Mapper
public interface IRestMapper {

	public int insertBoard(RestBoardVO boardVO);

	public void insertBoardFile(RestBoardFileVO boardFileVO);

	public RestBoardVO selectBoard(int no);

	public RestBoardFileVO getFileInfo(int fileNo);

	public int updateBoard(RestBoardVO boardVO);

	public RestBoardFileVO selectBoardFile(Integer integer);

	public void deleteBoardFile(Integer integer);

	public void deleteBoardFileByBoNo(int no);

	public int deleteBoard(int no);

	public List<RestBoardVO> list();


}
