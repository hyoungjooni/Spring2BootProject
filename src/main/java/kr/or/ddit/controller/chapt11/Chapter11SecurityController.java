package kr.or.ddit.controller.chapt11;

public class Chapter11SecurityController {

	
	
	
	/*
	
	 	[ 11장 : 스프링 시큐리티 ] 
	 	
	 	1. 스프링 시큐리티 소개 
	 	
	 		- 애플리케이션에서 보안 기능을 구현하는데 사용되는 프레임워크 
	 		- 스프링 시큐리티는 필터 기반으로 동작하기 때문에 스프링 MVC와 분리되어 동작함
	 		- 스프링 기반의 애플리케이션 보안 (인증, 인가)을 담당하는 스프링 하위 프레임워크
	 		
	 		
	 		# 인증과 인가 
	 		
	 		인증(Authentication)은 사용자의 신원을 입증하는 과정. 
	 		예를 들어 사용자가 사이트에 로그인을 할 떄 누구인지 확인하는 과정을 인증이라고 함. 
	 		인가 (Authorization)는 인증과는 다다름. 
	 		인가는 사이트의 특정 부분에 접근할 수 있는지의 권한을 확인하는 작업.
	 		예를 들어, 관리자는 관리자 페이지에 들어갈 수 있지만 일반 사용자는 관리자 페이지에 들어갈 수 없음 
	 		이런 권한을 확인하는 과정을 인가라고 함. 
	 		
	 		
	 		# 스프링 시큐리티 
	 		
	 		스프링 시큐리티는 스프링 기반 애플리케이션의 보안을 담당하는 스프링 하위 프레임워크이다. 
	 		다양한 보안 옵션을 제공하고 어노테이션을 이용한 설정도 간편하게 설정할 수 있음 . 
	 		CSRF 공셕, 세션 고정(Session Fixation) 공격을 방어해주고 요청 헤더도 보안 처리를 해주므로 개발자가 보안 관련 개발을 해야하는 
	 		부담을 크게 줄일 수 있음. 
	 		
	 			*** CSRF 공격 
	 				- 크로스 사이트 요청 위조는 웹 사이트 취약점 공격의 하나로, 사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위 (수정, 삭제, 등록 등)를 
	 				  특정 웹 사이트에 요청하게 하는 공격을 말함 
	 				  
	 			*** 세션 고정 공격
	 				- 사용자의 인증 정보를 탈취하거나 변조하는 공격을 말함 
	 				
	 		
	 		# 시큐리티 동작 방식 
	 		
	 		스프링 시큐리티는 필터 기반으로 동작함. 
	 		시큐리티는 SecurittyContextPersistenceFilter부터 시작해서 FilterSecurityInterceptor에 이르기까지 순서대로 많은 필터를 거침 
	 		많은 필터들 중, USernamePasswordAuthenticationFilter와 FilterSecuritryInterceptor는 시큐리티에 있어서 중요한 역할을 담당하는 
	 		필터입니다. UsernamePasswordAuthenticationFilter는 아이디와 비밀번호가 넘어오면 인증 요청을 위임하는 인증 관리자 역할이고, 
	 		FileterSecurityInterceptor는 권한 부여 처리를 위임해 접근 제어 결정을 쉽게 하는 접근 결정 관리자 역할을 함 
	 		
	 		
	 		# 각 필터 설명 
	 		
	 		SecurityContextPersistenceFilter 
	 		- SecurityContextRepository에서  SecurityContext (접근 주체와 인증에 대한 정보를 담고 있는 객체)를 가져오거나 저장하는 역할을 함 
	 		
	 		LogoutFilter
	 		- 설정된 로그아웃 URL로 오는 요청을 확인해 해당 사용자를 로그아웃 처리함 
	 		
	 		UsernamePasswordAuthenticationFilter 
	 		- 인증 관리자이다.
	 		  폼 기반 로그인을 할 때 사용되는 필터로 아이디, 비밀번호 데이터를 파싱해 인증 요청을 위임함.
	 		  인증이 성공하면 AuthenticationSuccessHandler를, 인증에 실패하면 AuthenticationFailureHandler를 실행한다. 
	 		  
	 		  
	 		DefaultLoginPageGenerationFilter 
	 		- 사용자가 로그인 페이지를 따로 지정하지 않았을 때 기본적으로 설정하는 로그인 페이지 관련 필터 
	 		
	 		BasicAuthenticationFilter
	 		- 요청 해더에 있는 아이디와 비밀번호를 파싱해서 인증 요청을 위임. 
	 	      인증이 성공하면 AuthenticationSuccessHandler를, 인증에 실패하면 AuthenticationFailureHandler를 실행함. 
	 	      
	 	    RequestCacheAwareFilter 
	 	    - 로그인 성공 후, 관련 있는 캐시 요청이 있는지 확인하고 캐시 요청을 처리함 
	 	      예를 들어 로그인하지 않은 상태로 방문했던 페이지를 기억해두었다가 로그인 이후에 그 페이지로 이동 시켜줌 
	 	      
	 	    SecurityContextHolderAwareRequestFilter 
	 	    - HttpServletRequest 정보를 감쌈. 
	 	      필터 체인 상의 다음 필터들에게 부가정보를 제공하기 위해 사용 
	 	      
	 	    AnnymousAuthenticationFilter 
	 	    - 필터가 호출되는 시점까지 인증되지 않았다면 익명 사용자 전용 객체인 AnnoymousAuthentication을 만들어 SecurityContext에 넣어줌
	 	    
	 	    
	 	    SessionManagementFilter 
	 	    - 인증된 사용자와 관련된 세션 관편 작업을 진행. 
	 	      세션 변조 방지 전략을 설정하고, 유효하지 않은 세션에 대한 처리를 하고, 세션 생성 전략을 세우는 등의 작업을 처리 
	 	      
	 	    ExceptionTranslationFilter 
	 	    - 요청을 처리하는 중에 발생할 수 있는 예외를 위임하거나 전달한다.
	 	    
	 	    FilterSecurityInterceptor
	 	    - 접근 결정 관리자 . 
	 	      AccessDecisionManager로 권한 부여 추리를 위임함으로써 접근 제어 결정을 쉽게 해줌. 
	 	      이 과정에서는 이미 사용자가 인증되어 있으므로 유효한 사용자인지도 알 수 있음 .
	 	      즉 인가 관련 설정을 할 수 있음 
	 	      
	 	      
	 	    --------------------------------------------------------------------------------------------------------------
	 	    
	 	    
	 	    가장 많이 사용되어지는 시큐리티 인증 방식은 로그인 폼을 이용한 폼 기반 방식이다.
	 	    사용자가 폼에 아이디와 패스워드를 입력하면, HttpServletRequest에 아이디와 비번 정보가 전달된다. 
	 	    이때, AuthenticationFilter가 넘어온 아이디와 비번의 유효성 검사를 한다. 
	 	    유효성 검사가 끝나면 실제 구현체인 UsernamePasswordAuthenticationManager에게 보냅니다. 
	 	    그리고 전달받은 해당 UsernamePasswordAuthenticationToken을 AuthenticationProvider에 보낸다. 
	 	    사용자가 아이디를 UserDetailService에 보내고 USerDetailService는 사용자 아읻로 조회한 사용자의 정보를 
	 	    UserDetials 객체로 만들어 AuthenticationProvider에게 전달한다. 
	 	    이때, DB에 있는 사용자 정보를 가져와 입력 정보와 USerDetail의 정보를 비교해 실제 인증 처리를 한다. 
	 	    인증이 완료되면 SecuirtyContextHolder에 Authencasiotn을 저장한다. 
	 	    인증 성공 여부에 따라 성공하면 AuthenticationSucccessHandler, 인증 실패하면 AuthenticationFailureHandler를 실행합니다. 
	 	    
	 	    
	 	    2. 스프링 시큐리티 설정 
	 	    
	 	    # 환경 설정 
	 	    
	 	    	- 의존 라이브러리 설정 (pom.xml) 
	 	    	> spring-boot-starter-security 
	 	    	> spring-security-taglibs 
	 	    	
	 	    	- applicatio.properties 설정 
	 	    	> logging.level.org.springframework.security=debug
	 	    	
	 	    	- 시큐리티 Config 설정 
	 	    	> config 패키지 내, SecurityConfig 클래스 생성 
	 	    	
	 	   # 웹 화면 접근 정책 
	 	   
	 	   		- 웹 화면 접근 정책을 정한다. (테스트를 위한 각 화면당 접근 정책을 설정한다) 
	 	   		> [일반 게시판] 목록 화면  - 모두가 접근 가능 
	 	   					  등록 화면 - 로그인한 회원, 관리자만 접근 가능 
	 	   					  
	 	   		  [공지사항] 목록 화면 - 모두가 접근 가능
	 	   		   		   등록 화면 - 로그인한 관리자만 접근이 가능 
	 	   		   		   
	 	   
	 	   # 화면 설정 
	 	   
	 	   		- 컨트롤러 
	 	   		> board.SecurityBoardController 
	 	   		> notice.SecurityBoardController
	 	    
	 	    	- 화면 
	 	    	> board/list.jsp 
	 	    	> board/register.jsp 
	 	    	> notice/list.jsp 
	 	    	> notice/register.jsp 
	 	    	
	 	    
	 	  
	 	  3. 접근 제한 설정 
	 	  
	 	  		- 시큐리티 설정을 토앻서 특정 URI에 대한 접근을 제한 할 수 있다. 
	 	  		
	 	  		
	 	  		# 환경 설정 
	 	  		
	 	  			- 시큐리티 Config 설정 
	 	  			> URI 패턴으로 접근 제한을 설정 
	 	  			> protected SecurityFilterChain filterChain(HttpSecurity http) 
	 	  			
	 	  		# 화면 설명 
	 	  		
	 	  			- 일반 게시판 목록 화면 (모두 접근 가능하도록 되어 있다 : permitAll) 
	 	  			- 일반 게시판 등록 화면 (회원권한을 가진 사용자, 관리자만 접근 가능 : hasAnyRole('ROLE__MEMBER', 'ROLE_ADMIN') 
	 	  			
	 	  					> 접근 제한에 걸려 스프링 시큐리티가 기본적으로 제공하는 로그인 페이지로 이동함
	 	  			- 공지사항 게시판 목록 화면 (모두 접근 가능하도록 되어 있다 : permitAll) 
	 	  			- 공지사항 게시판 등록 화면 (관리자 권한을 가진 사용자만 접근 가능 : hasRole ('ROLE_ADMIN') 
	 						> 접근 제한에 걸려 스프링 시큐리티가 기본적으로 제공하는 로그인 페이지로 이동함
	 						
	 						
	 			
	 	  4.  로그인 처리 
	 	  
	 	  		- 메모리상에 아이디와 패스워드를 지정하여 로그인을 처리한다
	 	  		- 스프링 시큐리티 5버전부터는 패스워드 암호화 처리기를 반드시 이용하도록 변경이 되었다. 
	 	  		- 암호화 처리기를 사용하지 않도록 "{noop}" 문자열을 비번 앞에 사용한다. 
	 	  		
	 	  		# 환경 설정
	 	  		
	 	  		 	- 시큐리티 Config 설정 
	 	  		 		> protected InMemoryUserDetailsManager inMemoryUserDetailsManager()
	 	  		 		
	 	  		 	- 비밀번호 암호화 설정 
	 	  		 		> 암호화 처리기를 사용하지 않도록 설정하기 위해서는 비밀번호 문자열 앞에 '{noop}'을 붙힌다. 
	 	  		 		
	 	  		# 화면 설정 
	 	  		
	 	  			- 일반 게시판 등록 화면 
	 	  				> 접근 제한에 걸려 스프링 시큐리티가 기본적으로 제공하는 로그인 페이지가 연결되고, 
	 	  				  일반 회원 등급인 ROLE_MEMBER 권한을 가진 member 계정으로 로그인 후 해당 페이지로 접근 가능 
	 	  				> 관리자 등급인 admin 계정은 ROLE_MEMBER 권한도 가지고 있는 계정이므로 해당 페이지로 접근 가능 
	 	  				
	 	  			- 공지사항 게시판 등록 화면 
	 	  				> 접근 제한에 걸려 스프링 시큐리티가 기본적으로 제공하는 로그인 페이지와 연결되고 , 
	 	  				  관리자 등급인 ROLE_ADMIN 권한을 가진 admin 계정으로 로그인 후 해당 페이지로 접근 가능 
	 
	 	 
	 	 5. 접근 거부 처리 
	 	 
	 	 		- 접근 거부가 발생한 상황을 처리하는 접근 거부 처리자의 URI를 지정할 수 있다. 
	 	 		
	 	 		# 환경 설정 
	 	 		
	 	 			- 시큐리티 Config 설정 
	 	 				> protected SecurityFilterChain filterChain(HttpSecurity http) 
	 	 				> filterChain 메소드 안에서 accessDenied시에 처리할 내용을 추가 
	 	 				> http.exceptionHandling((exception) -> exception.accessDeniedPage());
	 	 				
	 	 		# 접근 거부 처리 
	 	 		
	 	 			- 접근 거부 처리 컨트롤러 작성 
	 	 				> security.CommonController
	 	 				
	 	 			- 접근 거부 페이지 
	 	 				> chapt11/accessError.jsp
	 	 				
	 	 		
	 	 		# 화면 설정 
	 	 		
	 	 			- 일반 게시판 등록 페이지 
	 	 				> 접근 제한에 걸려 스프링 시큐리티가 제공하는 로그인 페이지가 나타나고, 회원 권한을 가진 계정으로 
	 	 				  접근 시 접근 사능 
	 	 			
	 	 			- 공지사항 게시판 등록 페이지 
	 	 				> 접근 제한에 걸려 스프링 시큐리티가 제공하는 로그인 페이지가 나타나고, 회원 권한을 가진 계정으로 접근 시에 
	 	 				  공지사항 게시판 등록 페이지는 관리자가 권한으로만 접근 가능하므로 접근이 거부된다. 
	 	 				  이떄, access-denied-handler로 설정되어 있는 URI로 이동하고 해당 페이지에서 접근이 거부 되었을 때 보여질 페이지의 정보가 나타난다. 
	 	 			
	 	 			
	 	 			
	 	 6. 사용자 정의 접근 거부 처리자 
	 	 
	 	 	- 접근 거부가 발생한 상황에 단순 메세지 처리 이상의 다양한 처리를 하고 싶다면 AccessDeniedHandler를 직접 구현하여야 한다. 
	 	 	
	 	 	# 환경 설정 
	 	 	
	 	 		- 시큐리티 Config 설정 
	 	 			> protected SecurityFilterChain filterChain(HttpSecurity http) 
	 	 			> filterChain 메소드안에서 accessDenied시에 처리할 내용을 추가 
	 	 			> http.exceptionHandling((exception) -> exception.accessDeniedhandler()); 
	 	 			
	 	 	# 접근 거부 처리 
	 	 	
	 	 		- 접근 거부 처리 컨트롤러 작성 
	 	 			> security.CustomAccessDeniedHandler
	 	 			
	 	 	# 접근 거부 처리자 클래스 정의 
	 	 	
	 	 		- 사용자가 정의한 접근 거부 처리자 
	 	 			> CustomAccessDeniedhandler 클래스 정의 
	 	 			> AccessDeniedHandler 인터페이스를 참조 받아 handle() 메소드를 재정의한다. 
	 	 			> 해당 요청에 의해 접근이 거부되었을 때, CustomAccessDeniedHandler 클래스가 exception
	 	 			  Handling에 의해 동작하여 response 내장객체를 활용하여 '/accessError' URL로 이동하여 
	 	 			  접근 거부시 보여질 페이지로 이동 
	 	 			  
	 	 	# 화면 설명 
	 	 		
	 	 		- 일반 게시판 등록 페이지
	 	 			> 접근 제한에 걸려 스프링 시큐리티가 제공하는 로그인페이지가 나타나고, 회원 권한을 가진 계정으로 접근 시 접근 가능 
	 	 		- 공지사항 게시판 등록 페이지 
	 	 			> 접근 제한에 걸려 스프링 시큐리티가 제공하는 로그인 페이지가 나타나고, 회원 권한을 가진 계정으로 
	 	 			  접근 시에 공지사항 게시판 등록 페이지는 관리자 권한만 접근 가능하므로 접근이 거부된다. 
	 	 			  이떄, access-denied-handler로 설정되어 있는 사용자 정의 클래스의 요청으로 이동하고 해당 페이지에서 
	 	 			  접근이 거부되었을 때 보여질 페이지로 이동한다.ㄴ
	 	 			  
	 
	*/
}
