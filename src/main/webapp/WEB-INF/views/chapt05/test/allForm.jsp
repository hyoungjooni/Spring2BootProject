<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 

		문제01) 회원가입 양식을 만들고 서버로 전송해주세요.
			항 목		|	name							|		value
		───────────────────────────────────────────────────────────────────────────
			아이디	|	userId 							|
			비밀번호	|	password  						|
			이름		|	userName 						|
			이메일	|	email 							|
			생년월일	|	dateOfBirth 					|
			성별		|	gender							|	남자(male), 여자(female)   
			개발자 여부	|	developer						|	개발자(Y), 비개발자(null)
			외국인 여부	|	foreigner						|	외국인(true), 내국인(false)
			국적		|	nationality						|	대한민국(korea),독일(germany),캐나다(canada),미국(usa)
			소유차량	|	cars, carArray, carList			|	BMW,AUDI,VOLVO,JEEP
			취미		|	hobby, hobbyArray, hobbyList	|	운동(sports),독서(book),영화감상(movie),음악감상(music)
			우편번호	|	postCode						|
			주소		|	location						|
		카드1-번호		|	no								|
		카드1-유효년월	|	validMonth						|	날짜 데이터
		카드2-번호		|	no								|
		카드2-유효년월	|	validMonth						|	날짜 데이터
			소개		|	introduction					|
		───────────────────────────────────────────────────────────────────────────
		** 사용 변수 및 타입은 자유
		문제02) 입력한 데이터를 '/chapt05/test/result'로 전송해주세요. (result.jsp는 'chapt05/test/result.jsp')



 -->
 
 <form action="/chapt05/test/result"  method="post">
 	<table border="1">
 		
 		<tr>
 			<td>유저 ID</td>
 			<td><input type="text" name="userId" /></td>
 		</tr>
		<tr> 			
 			<td>패스워드</td>
 			<td><input type="password" name="password" /></td>
 		</tr>	
 			
		<tr> 			
 			<td>이름</td>
 			<td><input type="text" name="name" /></td>
 		</tr>	
 			
		<tr> 			
 			<td>E-Mail</td>
 			<td><input type="text" name="email" /></td>
 		</tr>	
 		
		<tr> 			
 			<td>생년월일 </td>
 			<td><input type="text" name="dateOfBirth" /></td>
 		</tr>	
 			
		<tr> 			
 			<td>성별</td>
 			<td>
 			<input type="radio" name="gender" value="male" checked />Male
 			<input type="radio" name="gender" value="female"  />Female
 			<input type="radio" name="gender" value="other"  />Other
 			</td>
 		</tr>	
 			
		<tr> 			
 			<td>개발자 여부</td>
 			<td><input type="checkbox" name="developer" value="Y" /></td>
 		</tr>	
		<tr> 			
 			<td>외국인 여부</td>
 			<td><input type="checkbox" name="foreigner" value="true" /></td>
 		</tr>	
 			
		<tr> 			
 			<td>국적</td>
 			<td>
 				<select name="nationality">
 					<option value="korea">대한민국</option>
 					<option value="germany">독일</option>
 					<option value="canada">캐나다</option>
 					<option value="usa">미국</option>
 				</select>
 			</td>
 		</tr>	
 			
 		
		<tr> 			
 			<td>소유차량</td>
 			<td>
 				<select name="carList" multiple ="multiple">
 					<option value="JEEP">JEEP</option>
 					<option value="BMW">BMW</option>
 					<option value="AUDI">AUDI</option>
 					<option value="VOLVO">VOLVO</option>
 				</select>
 			</td>
 		</tr>	
 			
 		
		<tr> 			
 			<td>취미</td>
 			<td>
 				<input type="checkbox" name="hobbyList" value="sports" />운동
 				<input type="checkbox" name="hobbyList" value="book" />독서
 				<input type="checkbox" name="hobbyList" value="movie" />영화감상
 				<input type="checkbox" name="hobbyList" value="music" />음악감상
 			</td>
 		</tr>	
 		
		<tr> 			
 			<td>우편번호</td>
 			<td>
 				<input type="text" name="address.postCode" /></td>
 		</tr>	
 		
		<tr> 			
 			<td>주소</td>
 			<td>
 				<input type="text" name="address.location" /></td>
 		</tr>	
 		
		<tr> 			
 			<td>카드1-번호</td>
 			<td>
 				<input type="text" name="cardList[0].no" /></td>
 		</tr>	
		<tr> 			
 			<td>카드1-유효년월</td>
 			<td>
 				<input type="text" name="cardList[0].validMonth" /></td>
 		</tr>	
		<tr> 			
 			<td>카드2-번호</td>
 			<td>
 				<input type="text" name="cardList[1].no" /></td>
 		</tr>	
		<tr> 			
 			<td>카드2-유효년월</td>
 			<td>
 				<input type="text" name="cardList[1].validMonth" /></td>
 		</tr>	
		
		<tr> 			
 			<td>소개</td>
 			<td>
 				<input type="text" name="Introduction" /></td>
 		</tr>	
 		
 		<tr>
 		<td colspan="2">
 		<input type ="submit" value="등록">
 		<input type ="reset" value="리셋">
 		</td>
 		</tr>
 		
 		
 	</table>
 
 
 </form>
 
 
 
</body>
</html>