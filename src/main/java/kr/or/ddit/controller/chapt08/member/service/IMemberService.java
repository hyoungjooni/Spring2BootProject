package kr.or.ddit.controller.chapt08.member.service;

import java.util.List;

import kr.or.ddit.vo.crud.CrudMember;

public interface IMemberService {

	// 등록
	public void register(CrudMember member);

	// 목록
	public List<CrudMember> list();
	
	// 상세
	public CrudMember read(int userNo);
	
	// 수정
	public void modify(CrudMember member);
	
	// 삭제
	public void remove(int userNo);

}
