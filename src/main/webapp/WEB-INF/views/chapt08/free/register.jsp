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
									<h4 class="card-title">FREE</h4>
								</div>									
								<div class="card-body">
								<p class="card-description">Register</p>
								<form action="/crud/free/register" method="post" id="free">
								<c:set value="등록" var="btnType"/>
									<c:if test="${status eq 'u' }">
										<input type="hidden" name="freeNo" value="${free.freeNo }">
										<c:set value="수정" var="btnType"/>
									</c:if>
									
									<table>
										<tr>
											<td>제목</td>
											<td>
												<input type="text" name="title" id="title" value="${free.title }" />
											</td>
										</tr>
										
										<tr>
											<td>작성자</td>
											<td>
												<input type="text" name="writer" id="writer" value="${free.writer }" />
											</td>
										</tr>
										
										<tr>
											<td>내용</td>
											<td>
												<textarea rows="8" cols="27" name="content" id="content" >${free.content }</textarea>
											</td>
										</tr>
									
									</table>
									<br/>
									<input type="button" id="registerBtn" value="${btnType}" class="btn btn-primary"/>
									<input type="button" id="listBtn" value="목록" class="btn btn-info"/>
								</form>
								
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

$(function(){
	
	let registerBtn = $("#registerBtn");
	let listBtn = $("#listBtn");
	let free = $("#free");
	
	// 등록 버튼 이벤트
	registerBtn.on("click", function(){
		
		// 등록버튼을 눌렀을때.....
		
		let title = $("#title").val();
		let writer = $("#writer").val();
		let content = $("#content").val();
		
		// 입력란을 빈칸으로 등록했을 때
		if (title == null || title == ""){
			alert("제목을 입력해주시오");
			return false;
		}
		
		if (writer == null || writer == ""){
			alert("작성자명을 입력해주시오");
			return false;
		}
		
		if (content == null || content == ""){
			alert("내용을 입력해주시오");
			return false;
		}
		
		if($(this).val() == "수정"){
			free.attr("action", "/crud/free/modify");
		}
		
		free.submit();
		
	});
	
	
	
	// 목록 버튼 이벤트
	listBtn.on("click", function(){
		location.href = "/crud/free/list";
	});
	
	
	
});


</script>



</html>