package kr.or.ddit.controller.chapt11;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;
import kr.or.ddit.controller.chapt06.Chapter06RedirectAttributeController;
import kr.or.ddit.security.CustomAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;


// @Configuration 어노테이션 스프링 설정 클래스 , 해당 클래스를 싱글톤을 적용하여 하나의 인스턴스로 관리하면서 bean 등록을 위해 설정 


@Configuration
@Slf4j
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
	
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// # CSRF 설정 
		// CSRF 공격에 방어하기 위한 방안으로 csrf를 설정하는데 REST에서는 csrf를 disable 설정이 기본 설정 
		http.csrf((csrf) -> csrf.disable());
	
		http.authorizeHttpRequests(
				(authorize) -> 
						// forward는 모두 접근 가능 
						authorize.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ASYNC).permitAll()
						// 서버쪽에서 정적 자원을 관리한다면 static 하위 정적 파일들을 permitAll로 설정한다.
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
						.requestMatchers("/resources/**").permitAll()
						// 시큐리티 일반게시판 목록, 시큐리티 공지사항 게시판 목록 모두 접근 가능 
						.requestMatchers("/security/board/list", "/security/notice/list").permitAll()
						// 시큐리티 일반 게시판 등록은 회원과 관리자만 접근 가능 
						// 'ROLE_'를 제외한 권한명을 설정한다. 
						.requestMatchers("/security/board/register").hasAnyRole("MEMBER", "ADMIN")
						// 시큐리티 공지사항 게시판 등록은 관리자만 접근 가능
						.requestMatchers("/security/notice/register").hasRole("ADMIN")
						.requestMatchers("/").permitAll()
						// 다른 요청은 인증된 사용자가 아닌 모든 사용자 접근 가능 
						.anyRequest().permitAll()
		);
		
		
		// 로그인 인증 방식은 "BASIC"에 해당하는 기본 인증 방식으로 요청 
		http.httpBasic(Customizer.withDefaults());
		
		// 5. 접근 거부 처리 
		// 해당 타겟에 접근 가능한 권한을 가진 사용자가 아닌 경우 접근 거부 처리 발동! 
		// http.exceptionHandling((exception) -> exception.accessDeniedPage("/accessError"));
		
		
		// 6. 사용자 정의 접근 거부 처리자 
		http.exceptionHandling((exception) -> exception.accessDeniedHandler(new CustomAccessDeniedHandler())); 
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
	
	
}
