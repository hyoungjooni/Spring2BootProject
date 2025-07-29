package kr.or.ddit.controller.chapt13;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.or.ddit.jwt.JwtProperties;
import kr.or.ddit.vo.jwt.JwtMemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/jwt")
public class JwtLoginController {

	@Autowired
	private JwtProperties jwtProperties;
	
	
	// 로그인 
	// 로그인은 사용자의 세션을 얻는 행위와도 같습니다. 
	// 세션과 같은 사용자가 로그인을 시도하고 인증이 완료 시, 얻어지는 세션과 같은 기능을 담당할 token을 발행합니다.
	// 해당 토큰은 시크릿키를 기반으로 헤더(header)와 내용(payload)이 포함된 JWT Token을 발행합니다.
	@PostMapping("/login")
	public ResponseEntity<?> jwtLogin(@RequestBody JwtMemberVO memberVO){
		log.info("username :" + memberVO.getUsername());
		log.info("password :" + memberVO.getPassword());
	
		// application.properties 내, secret_key 가져오기 
		// 시크릿키를 바이트로 전환한다. 
		// 이후 SHA-256 방식의 암호화를 설정한 서명의 값으로 사용하기 위해 바이트로 전환 
		byte[] keyByte = jwtProperties.getSecretKey().getBytes();
		// 제공자 정보를 가져온다. 
		String issuer = jwtProperties.getIssuer();		// 제공자 : ddit
		
		// 토큰 생성 
		String jwt = Jwts.builder()
				/*
				  	.setHeaderParam()는 header를 설정할 떄 사용한다. 
				  	{
				  		"typ" : "JWT",
				  		"alg" : "HS256" 
				  	
				  	}
				*/
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)		// 헤더 typ:JWT
				.setIssuer(issuer) 				// payload의 'iss' (토큰 발급자)를 지정할 때 사용한다. 
				.setIssuedAt(new Date())		// payload의 'iat' (토큰 발급시간)을 지정할 때 사용한다. 
				// 내용 exp : expiry 맴버 변수값 
				// payload의 'exp'(토큰 만료시간)을 지정할 때 사용한다. 
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))		// 기간은 1일 
				// payload의 'sub'(토큰 제목)을 지정할 떄 사용한다. 
				.setSubject(memberVO.getUsername()) 		// 내용 sub 
				// payload의 'uid' (공개 클레임 id)를 지정할 댸 사용한다. 
				.claim("uid", memberVO.getUsername())		// 클레임 uid : 유저 ID
				.claim("upw", memberVO.getPassword())		// 클레임 upw : 유저 PW
				.claim("unm", "hongkildong")				// 클레임 unm : 유저 NAME
				.claim("auth", "ROLE_MEMBER")				// 클레임 auth : 유저 권한 
				// .signWith (알고리즘, 시크릿키) 
				// 시크릿키는 application.properties에 설정된 secret_key
				// 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화 
				// H256이란 HMAC의 SHA-256 방식으로 암호화를 진행한다는 뜻이다. 
				.signWith(SignatureAlgorithm.HS256, keyByte)
				.compact();
		
		log.info("## jwt : " + jwt);
		return new ResponseEntity<String>(jwt,HttpStatus.OK);
				
				
	}
	
	@GetMapping("/userInfo")
	public ResponseEntity<?> userInfo(
			@RequestHeader(name="Authorization") String header
			){
		String userInfo = "";		// 복호화 된 결과 
		if(StringUtils.isNotBlank(header)) {
			log.info("# header : " + header);
			
			String jwt = header.replace("Bearer", "");	// 'BeClaims 제거 
			
			Jwt<JwsHeader, Claims> parsedToken = Jwts.parser()
					.setSigningKey(jwtProperties.getSecretKey().getBytes())  // 비밀키 복호화
					.parseClaimsJws(jwt);		// 기존에 만들어뒀던 token을 decode
			
			// 토큰을 파싱 후 (복호화), payload(내용)에 들어있는 내용들을 꺼낸다. 
			String uid = parsedToken.getBody().get("uid").toString();
			String upw = parsedToken.getBody().get("upw").toString();
			String unm = parsedToken.getBody().get("unm").toString();
			String auth = parsedToken.getBody().get("auth").toString();
			
			
			log.info("# uid : " + uid);
			log.info("# upw : " + upw);
			log.info("# unm : " + unm);
			log.info("# auth : " + auth);
			
			userInfo = parsedToken.toString();
			
		}
		
		return new ResponseEntity<String>(userInfo, HttpStatus.OK);
		
	}
	
	
	
	
}
