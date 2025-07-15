package kr.or.ddit.controller.chapt08.free.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.crud.Free;

@Mapper
public interface IFreeMapper {

	// 1. 등록 
	void register(Free free);

	
	// 2. 상세정보
	Free detail(int freeNo);

	// 3. 목록출력
	List<Free> list();

	// 4. 수정
	int modify(Free free);

	// 5. 삭제
	void delete(int freeNo);

	// 6. 검색
	List<Free> search(Free free);

	
	
	


}
