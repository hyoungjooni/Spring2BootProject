package kr.or.ddit.controller.chapt11;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import kr.or.ddit.controller.chapt06.Chapter06RedirectAttributeController;
import kr.or.ddit.security.CustomAccessDeniedHandler;
import kr.or.ddit.security.CustomLoginFailureHandler;
import kr.or.ddit.security.CustomLoginSuccessHandler;
import kr.or.ddit.security.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;


// @Configuration 어노테이션 스프링 설정 클래스 , 해당 클래스를 싱글톤을 적용하여 하나의 인스턴스로 관리하면서 bean 등록을 위해 설정 
// @EnableWebSecurity 어노테이션은 스프링 시큐리티를 활성화하고 보안 설정을 구성하는데 사용하며, 시큐리티 필터 체인을 위해 설정된다. 
// @EnableMethodSecurity 어노테이션은 요청을 처리하기 위한 타켓(메소드)에 권한을 서정하기 위해 설정한다. (@PreAuthorize, @PostAuthorize 사용) 

@Configuration
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final Chapter06RedirectAttributeController chapter06RedirectAttributeController;

    SecurityConfig(Chapter06RedirectAttributeController chapter06RedirectAttributeController) {
        this.chapter06RedirectAttributeController = chapter06RedirectAttributeController;
    }

		/*
		 	Spring Security 6.1.0 이후부터 람다식의 형태로 바뀌면서 deprecated된 메서드가 상당히 많아짐 
		 	하여, 대부분 람다식의 표현으로 사용하지 않으면 메소드의 밑줄이 거지는 deprecated의 형태로 warning을 알려준다. 
		 	그렇기 때문에 , 상위 버전에서는 deprecated된 메서드를 업데이트 된 메서드의 형태로 사용한다. 
		 	
		*/
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private DataSource dataSource;
	
    
    
    // 스프링 시큐리티 기능 비활성화 
    /*
       스프링 시큐리티의 모든 기능을 사용하지 않게 설정하는 코드. 
       즉, 인증 인가 서비스를 모든 곳에 적용하지 않는다. 
       일반적으로 정적 리소스(이미지, HTML 파일 등)에 설정한다. 정적 리소스만 스프링 시큐리티 사용을 비활성화하는데 
       static 하위 경로에 있는 리소스를 대상으로 ignoring() 메소드를 사용한다. 
    */
    @Bean
    public WebSecurityCustomizer configure() {
    	return (web) -> web.ignoring().requestMatchers("/resources/**");
    }
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// # CSRF 설정 
		// CSRF 공격에 방어하기 위한 방안으로 csrf를 설정하는데 REST에서는 csrf를 disable 설정이 기본 설정 
		// http.csrf((csrf) -> csrf.disable());
	
//		http.authorizeHttpRequests(
//				(authorize) -> 
//						// forward는 모두 접근 가능 
//						authorize.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ASYNC).permitAll()
//						// 서버쪽에서 정적 자원을 관리한다면 static 하위 정적 파일들을 permitAll로 설정한다.
//						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//						.requestMatchers("/resources/**").permitAll()
//						// 시큐리티 일반게시판 목록, 시큐리티 공지사항 게시판 목록 모두 접근 가능 
//						.requestMatchers("/security/board/list", "/security/notice/list").permitAll()
//						// 시큐리티 일반 게시판 등록은 회원과 관리자만 접근 가능 
//						// 'ROLE_'를 제외한 권한명을 설정한다. 
//						.requestMatchers("/security/board/register").hasAnyRole("MEMBER", "ADMIN")
//						// 시큐리티 공지사항 게시판 등록은 관리자만 접근 가능
//						.requestMatchers("/security/notice/register").hasRole("ADMIN")
//						.requestMatchers("/").permitAll()
//						// 다른 요청은 인증된 사용자가 아닌 모든 사용자 접근 가능 
//						.anyRequest().authenticated()
//		);
		
		
		// 로그인 인증 방식은 "BASIC"에 해당하는 기본 인증 방식으로 요청 
		http.httpBasic(Customizer.withDefaults());
		
		// 5. 접근 거부 처리 
		// 해당 타겟에 접근 가능한 권한을 가진 사용자가 아닌 경우 접근 거부 처리 발동! 
		// http.exceptionHandling((exception) -> exception.accessDeniedPage("/accessError"));
		
		
		// 6. 사용자 정의 접근 거부 처리자 
		http.exceptionHandling((exception) -> exception.accessDeniedHandler(new CustomAccessDeniedHandler())); 
	
		
		
		// 7. 사용자 정의 로그인 페이지 
		http.httpBasic((basic) -> basic.disable());
		http.formLogin((login) -> login.loginPage("/login")
										.successHandler(new CustomLoginSuccessHandler())	// 로그인 성공 처리자 
										.failureHandler(new CustomLoginFailureHandler())
		
		);
		
		// 9. 로그아웃 처리 
		http.logout(
				(logout) -> logout.logoutUrl("/logout")			// 로그아웃 처리 URL
								.invalidateHttpSession(true)	// 로그아웃 처리 시, session 삭제
								.deleteCookies("JSESSION_ID", "remember-me")	// 로그아웃 시, 쿠키 삭제
		);
		
		// 12. 자동 로그인 처리 
		http.rememberMe((config) -> config.tokenValiditySeconds(86400) // 쿠키 유효 시간 설정 
									.tokenRepository(persistentTokenRepository())
					);
		
		
		return http.build();
	}
	
	
	@Bean
	@ConditionalOnMissingBean(UserDetailsService.class)
	protected InMemoryUserDetailsManager inMemDetailsManager() {
		// 'ROLE_MEMBER'권한을 가진 회원 정보와 'ROLE_ADMIN', 'ROLE_MEMBER' 권한을 가진 관리자 정보를 등록한다. 
		// 회원 권한을 가진 계정 생성 
		UserDetails member = User.withUsername("m001")					// 사용자 id 설정 
								.password("{noop}1234") 				// 사용자 pw 설정 ({noop}) 암호화 비활성화) 
								.authorities("ROLE_MEMBER")				// 권한 설정(회원)
								.build();
		
		// 관리자 권한을 가진 계정 생성 
		UserDetails admin = User.withUsername("a001")					// 사용자 id 설정
							.password("{noop}1234")						// 사용자 pw 설정 ({noop} 암호화 비활성화)
							.authorities("ROLE_MEMBER", "ROLE_ADMIN")	// 권한 설정(회원, 관리자 ) 
							.build();
		
		// UserDetails로 만든 두가지 계정 정보를 InMemory에 저장 
		return new InMemoryUserDetailsManager(member, admin);
	
	
	
	}
	
	// 10. UserDetailsService 재정의 
	// 인증 관리자 관련 설정 
	// 사용자 정보를 가져올 서비스를 재정의하거나, 인증방법, LDAP/JDBC 기반 인증 등을 설정할 때 사용한다. 
	@Bean
	protected AuthenticationManager authenticationManager(
		HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, 
		UserDetailsService userDetailsService){
			// 인증 제공자 인증 처리
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			// # userDeatilsService()
			// - 사용자 정보를 가져올 서비스를 설정한다. 이때, 설정하는 클래스는 UserDetailsService를 상속받는 클래스여야 한다. 
			// # passwordEncoder()
			// - 비밀번호 암호화하기 위한 인코더를 설정한다.
			
			authProvider.setUserDetailsService(customUserDetailsService);
			authProvider.setPasswordEncoder(bCryptPasswordEncoder);
			return new ProviderManager(authProvider);
		}
	
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 12. 자동로그인 설정 
	// remember-me 데이터베이스 연결 
	@Bean
	protected PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		
		try {
			tokenRepository.getJdbcTemplate().execute(JdbcTokenRepositoryImpl.CREATE_TABLE_SQL);
		} catch (BadSqlGrammarException e) {
			log.error("persistent_logins 테이블이 이미 존재합니다!");
		}catch (Exception e) {
			log.error("자동 로그인 테이블 생성 중, 에러 발생 ");
		}
		
		return tokenRepository;
	}
	
}
	
	

