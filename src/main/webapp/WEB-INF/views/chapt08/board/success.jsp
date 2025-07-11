<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>DDIT BOARD</title>
<%@ include file="../../skydash/headPart.jsp" %>
</head>
<body>
	<div class="container-scroller">
		<%@ include file="../../skydash/header.jsp" %>
		<div class="container-fluid page-body-wrapper">
			<%@ include file="../../skydash/aside.jsp" %>
			<div class="main-panel">
				<div class="content-wrapper">
					<div class="row">
						<div class="col-lg-12 grid-margin stretch-card">
							<div class="card">
								<div class="card-header">
									<h4 class="card-title">Chapt08</h4>
								</div>									
								<div class="card-body">
								<p class="card-description">SUCCESS</p>
								<h2>${msg }</h2>
								<a href="/crud/board/list"> 목록</a>
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<%@ include file="../../skydash/footer.jsp" %>
			</div>
		</div>
	</div>

	<%@ include file="../../skydash/footerPart.jsp" %>
</body>

<script type="text/javascript">
$(function (){
	let btnRegister = $("#btnRegister"); 
	let btnList = $("#btnList");
	let board = $("#board");
	
	// 등록 버튼 이벤트 
	btnRegister.on("click", function(){
		let title = $("#title").val();		// 제목 값
		let writer = $("#writer").val();	// 작성자 값
		let content = $("#content").val();	// 내용 값 
		
		if(title == null || title= ""){
			alert("제목 입력 플리즈앤땡큐");
			return false;
		}
		
		if(writer == null || writer= ""){
			alert("작성자 입력 플리즈앤땡큐");
			return false;
		}
		
		if(content == null || content= ""){
			alert("내용 입력 플리즈앤땡큐");
			return false;
		}
		
		board.submit();
	});
	
	
	
	// 목록 버튼 이벤트 
	btnList.on("click", function(){
		location.href ="/crud/board/list";
	});
	
	
	
	
	
	
});




</script>
</html>