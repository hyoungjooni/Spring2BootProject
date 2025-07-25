<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags"  prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>LOGOUT</h1>
	
	<form action="/login" method="post">

		<input type="submit" value="로그아웃" />
		<sec:csrfInput/>
	</form>
</body>
</html>