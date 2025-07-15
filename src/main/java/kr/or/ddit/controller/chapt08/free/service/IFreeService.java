package kr.or.ddit.controller.chapt08.free.service;

import java.util.List;

import kr.or.ddit.vo.crud.Free;

public interface IFreeService {

	
	// 1. 자유 게시판 등록하기 
	void register(Free free);

	
	// 2. 상세정보
	Free detail(int freeNo);

	// 3. 목록 출력
	List<Free> list();

	// 4. 수정용
	int modify(Free free);

	// 5. 삭제
	void delete(int freeNo);

	// 6. 검색
	List<Free> search(Free free);


	
}
