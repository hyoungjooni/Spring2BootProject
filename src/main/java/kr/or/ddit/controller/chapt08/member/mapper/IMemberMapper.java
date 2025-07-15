package kr.or.ddit.controller.chapt08.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.crud.CrudMember;
import kr.or.ddit.vo.crud.CrudMemberAuth;

@Mapper
public interface IMemberMapper {

	
	// 회원 등록 
	public void create(CrudMember member);

	public void createAuth(CrudMemberAuth memberAuth);

	public List<CrudMember> list();

	
	// 상세페이지
	public CrudMember read(int userNo);

	// 업데이트
	public void modify(CrudMember member);

	
	// 삭제
	public void deleteAuth(int userNo);

	public void delete(int userNo);

}
