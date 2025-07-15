package kr.or.ddit.vo.crud;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("fVO")
public class Free {

	private int freeNo;
	private String title; 
	private String content; 
	private String writer; 
	private Date regDate;

}
