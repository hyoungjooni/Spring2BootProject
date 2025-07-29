package kr.or.ddit.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.controller.chapt12.service.IRestService;
import kr.or.ddit.vo.ServiceResult;
import kr.or.ddit.vo.rest.RestBoardVO;
import lombok.extern.slf4j.Slf4j;


// @SpringBootTest 어노테이션은 기본적으로 스프링 통합 테스트를 제공하는 테스트 어노테이션이다. 
@Slf4j
@SpringBootTest
@Transactional

class RestServiceImplTest {

	
	@Autowired
	private IRestService service;

	// 각 테스트 케이스 별, 케이스가 실행 되기 전 실행 
	// 테스트 케이스를 진행하기 전, 성행되어야 할 기능이 있다면 @BeforeEach 어노테이션으로 설정할 수 있다. 
	@BeforeEach
	public void before() {
		log.info("Test Before");
	}
	
	
	
	// 각 테스트 케이스 별, 케이스가 실행 된 후 실행 
	// 테스트 케이스를 실행 후, 이후 이행되어야 할 기능이 있다면 @AfterEach 어노테이션으로 설정할 수 있다.
	@AfterEach
	public void after() {
		log.info("Test After");
	}
	
	// 첫번째 테스트 케이스 
	@Test
	void testCase01() {
		int a = 10; 
		int b = 10;
		// 10과 10은 같기 때문에 테스트 성공 
		assertEquals(a,b);
	}
	
	// 두번째 테스트 케이스 
	@Test
	void testCase02() {
		int a = 10; 
		int b = 20;
		// 10과 20은 다르기 때문에 테스트 실패 
		assertEquals(a, b);
	}
	
	@Test
	void testCase03() {
		String text = "ddit";
		assertThat("ddit").isEqualTo(text);
				
	}
	
	@Test
	void testCase04() {
		String text = "Hello World!";
		assertThat(text).isNotNull()
						.startsWith("Hello")
						.endsWith("!");
	}
	
	
	// 서비스 객체를 활용해 Test를 진행 하기 전, Service 객체 주입이 정상적으로 진행된 상태에서 테스트를 하고 있는지 확인하기 위해서 
	// service 객체 not null 체크 
	@Test
	void notNullServiceTest() {
		assertNotNull(service);
	}
	
	
	// 조회 테스트 
	@Test
	void selectBoardTest() {
		
		// 1. 예상 데이터 
		RestBoardVO rbVO = new RestBoardVO();
		rbVO.setBoNo(6);
		rbVO.setBoTitle("1234");
		rbVO.setBoContent("1234");
		rbVO.setBoWriter("a001");
		rbVO.setBoHit(0);
		rbVO.setBoDate("2025-07-28 16:08:39");
		rbVO.setBoardFileList(new ArrayList<>());
		
		
		// 2. 실제 데이터 
		RestBoardVO realRbVO = service.selectBoard(5);
		
		// 3. 비교
		assertEquals(rbVO, realRbVO );
	}
	
	
	// 수정 테스트 
	@Test
	void updateBoardTest() {
		// 1. 수정 할 데이터 
		RestBoardVO rbVO = new RestBoardVO();
		rbVO.setBoNo(5);
		rbVO.setBoTitle("01234");
		rbVO.setBoContent("01234");
		
		// 2. 수정 진행 
		ServiceResult result = null;
		try {
			result = service.updateBoard(rbVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result.equals(ServiceResult.OK)) {
			RestBoardVO rbVO1 = new RestBoardVO();
			rbVO1.setBoNo(5);
			rbVO1.setBoTitle("01234");
			rbVO1.setBoContent("01234");
			rbVO1.setBoWriter("a001"); 
			rbVO1.setBoHit(0);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String nowTime1 = sdf1.format(now);
			
			rbVO1.setBoDate(nowTime1);
			rbVO1.setBoardFileList(new ArrayList<>());
			
			RestBoardVO realRbVO = service.selectBoard(5);
			assertEquals(rbVO1, realRbVO);
			
		}
	}
	
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
