package kr.or.ddit.controller.chapt08.free.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.controller.chapt08.free.mapper.IFreeMapper;
import kr.or.ddit.vo.crud.Free;


@Service
public class FreeServiceImpl implements IFreeService {

	@Autowired
	private IFreeMapper mapper;
	
	
	
	
	// 1. 등록 
	@Override
	public void register(Free free) {
		mapper.register(free);
		// return을 안해도 되는 이유는 DB에 데이터를 insert만 하면 되기 떄문이다 . 
		// 등록 성공 여부나 결과 ID등을 등록 후 VIEW로 리턴을 안해줘도 되기 떄문이다.
		
	}



	// 2. 등록한 상세 페이지
	@Override
	public Free detail(int freeNo) {
		return mapper.detail(freeNo);
	}


	// 목록 출력 
	@Override
	public List<Free> list() {
		return mapper.list();
	}


	// 수정
	@Override
	public int modify(Free free) {
		return mapper.modify(free);
	}



	@Override
	public void delete(int freeNo) {
			mapper.delete(freeNo);
	}



	@Override
	public List<Free> search(Free free) {
		return mapper.search(free);
	}



	
	

}
