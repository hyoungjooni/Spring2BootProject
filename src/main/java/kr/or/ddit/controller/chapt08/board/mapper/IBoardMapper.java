package kr.or.ddit.controller.chapt08.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.crud.Board;

@Mapper
public interface IBoardMapper {

	// 새로 등록 
	void create(Board board);

	
	// 목록
	List<Board> list();

	// 상세 정보
	Board read(int boardNo);

	// 수정
	void modify(Board board);

	// 삭제
	void remove(int boardNo);

	// 검색
	List<Board> search(Board board);

}
