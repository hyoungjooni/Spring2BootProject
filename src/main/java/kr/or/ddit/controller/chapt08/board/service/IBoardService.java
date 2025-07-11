package kr.or.ddit.controller.chapt08.board.service;

import java.util.List;

import kr.or.ddit.vo.crud.Board;

public interface IBoardService {

	
	// 등록이벤트
	void register(Board board);
	
	// 목록 리스트
	List<Board> list();
	
	// 상세정보
	Board read(int boardNo);
	
	// 수정
	void modify(Board board);
	
	// 삭제
	void remove(int boardNo);

	// 검색
	List<Board> search(Board board);

}
