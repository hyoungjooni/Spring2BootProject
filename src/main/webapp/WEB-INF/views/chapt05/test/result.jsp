<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 




	[아래 결과처럼 출력해주세요.]
	
	유저ID			a001
	패스워드			1234
	이름				조현준
	E-MAIL			wh-guswns123@hanmail.net
	생년월일			2023년 08월 25일
	성별				남자
	개발자여부			개발자
	외국인여부			외국인
	국적				대한민국
	소유차량			소유차량 없음
	취미				운동 영화시청
	우편번호			45617
	주소				대전광역시 서구  오류동
	카드1(번호)		1451-1234-1234-1122
	카드1(유효년월)	2040년 09월 09일
	카드2(번호)		1234-1234-1234-1234
	카드2(유효년월)	2040년 09월 09일
	소개				반갑습니다
	
	** 조건
	- 선택한 성별에 따라 남자/여자로 나타내주세요.
	- 개발자 여부 체크에 따라 개발자/일반으로 나타내주세요.
	- 외국인 여부 체크에 따라 외국인/내국인으로 나타내주세요.
	- 국적에 따라 대한민국/독일/호주/캐나다로 나타내주세요.
	- 소유차량 선택에 따라 소유차량 없음/JEEP/VOLVO/BMW/AUDI로 나타내주세요.
		선택 갯수에 따라 아래처럼 출력해주세요.
		> 소유차량 없음
		> JEEP
		> JEEP VOLVO AUDI
	- 취미 선택에 따라 운동/영화시청/음악감상로 나타내주세요.
		선택 갯수에 따라 아래처럼 출력해주세요.
		> 취미 없음
		> 음악감상
		> 운동 영화시청
	- 날짜는 꼭 포멧을 지정해서 출력하지 않고 날짜 데이터만 출력해도 됩니다
	

 -->
 
 <c:set value="${member }" var="mem" />
 <form action="/chapt05/test/result" method="post">>
 	<table border="1">
 		
 		<tr>
 			<td>유저 ID</td>
 			<td>${mem.userId }</td>
 		</tr>
		<tr> 			
 			<td>패스워드</td>
 			<td>${mem.password }</td>
 		</tr>	
 			
		<tr> 			
 			<td>이름</td>
 			<td>${mem.userName }</td>
 		</tr>	
 			
		<tr> 			
 			<td>E-Mail</td>
 			<td>${mem.email }</td>
 		</tr>	
 			
		<tr> 			
 			<td>생년월일</td>
 			<td>${mem.dateOfBirth }</td>
 		</tr>	
 			
		<tr> 			
 			<td>성별</td>
 			<td>
 			<c:choose >
 				<c:when test="${mem.gender == 'male'}">남자</c:when>
 				<c:when test="${mem.gender == 'female'}">여자</c:when>
 				<c:when test="${mem.gender == 'other'}">트젠</c:when>
 			</c:choose>
 			</td>
 		</tr>	
 			
		<tr> 			
 			<td>개발자 여부</td>
 			<td>${mem.developer == 'Y' ? "개발자" : "개발자 아님" }</td>
 		</tr>	
		
		<tr> 			
 			<td>외국인 여부</td>
 			<td>${mem.foreigner == true ? "외국인" : "외국인 아님"}</td>
 		</tr>	
 			
		<tr> 			
 			<td>국적</td>
 			<td>
 			<c:set value="${mem.nationality }" var="nation"/>
 			<c:choose>
 				<c:when test="${nation == 'korea' }">대한민국</c:when>
 				<c:when test="${nation == 'germany' }">독일</c:when>
 				<c:when test="${nation == 'canada' }">캐나다</c:when>
 				<c:when test="${nation == 'usa' }">미국</c:when>
 			</c:choose>
 			</td>
 		</tr>	
 			
 		
		<tr> 			
 			<c:set value="${mem.carList }" var="carList" />
 			<td>소유차량</td>
 			<td>
 				<c:choose>
 					<c:when test="${empty carList }">
 					소유차량 없음 
 					</c:when>
 				<c:otherwise>
 					<c:forEach items="${carList }" var="car" varStatus="vs" >
 					${car }
 					<c:if test="${!vs.last }"></c:if>
 					</c:forEach>
 				</c:otherwise>
 				</c:choose>
 			
 			
 			</td>
 		</tr>	
 			
 		
		<tr> 			
 			<c:set value="${member.hobbyList }" var="hobbyList" />
 			<td>취미</td>
 			<td>
 			<c:choose>
 				<c:when test="${empty hobbyList }">
 				취미 없음 
 				</c:when>
 				<c:otherwise>
 					<c:forEach items="${hobbyList }" var="hobby" varStatus="vs">
 						<c:choose>
 							<c:when test="${hobby == 'sports' }">운동</c:when>
 							<c:when test="${hobby == 'book' }">독서</c:when>
 							<c:when test="${hobby == 'movie' }">영화</c:when>
 							<c:when test="${hobby == 'music' }">음악</c:when>
 						</c:choose>
 						<c:if test="${!vs.last }">,</c:if>
 					</c:forEach>
 				</c:otherwise>
 			</c:choose>
 			</td>
 			<td>
 			</td>
 		</tr>	
 		
		<tr> 			
 			<td>우편번호</td>
 			<td>${mem.address.postCode }</td>
 				
 		</tr>	
		<tr> 			
 			<td>주소</td>
 			<td>${mem.address.location }</td>
 				
 		</tr>	
 		
		<tr> 			
 			<td>카드1-번호</td>
 			<td>${mem.cardList.get(0).no }</td>
 				
 		</tr>	
		<tr> 			
 			<td>카드1-유효년월</td>
 			<td>${mem.cardList.get(0).validMonth }</td>
 				
 		</tr>	
		<tr> 			
 			<td>카드2-번호</td>
 			<td>${mem.cardList.get(1).no }</td>
 				
 		</tr>	
		<tr> 			
 			<td>카드1-유효년월</td>
 			<td>${mem.cardList.get(1).validMonth }</td>
 				
 		</tr>	
		
		<tr> 			
 			<td>소개</td>
 			<td>${mem.introduction }</td>
 		</tr>	
 		
 	
 		
 		
 	</table>
 
 
 </form>
 
 
 
 
 
</body>
</html>