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
								<p class="card-description">DETAIL</p>
								<table class="table table-bordered">
								<tr>
									<td>제목</td>
									<td>${free.title }</td>
								</tr>
							
								<tr>
									<td>작성자</td>
									<td>${free.writer }</td>
								</tr>
							
								<tr>
									<td>내용</td>
									<td>${free.content }</td>
								</tr>
							
								<tr>
									<td>등록일</td>
									<td>${free.regDate }</td>
								</tr>
								</table>
								<br>
								<input type="button" class="btn btn-primary" id="modifyBtn" value="수정"  />
								<input type="button" class="btn btn-warning" id="deleteBtn" value="삭제"  />
								<input type="button" class="btn btn-primary" id="listBtn" value="목록"  
								onclick="javascript:location.href='/crud/free/list'"/>
								
								<form action="/crud/free/delete" method="post" id="delForm">
									<input type="hidden" name="freeNo" value="${free.freeNo }">
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
	let modifyBtn = $("#modifyBtn");
	let deleteBtn = $("#deleteBtn");
	let listBtn = $("#listBtn");
	let delForm = $("#delForm");
	
	modifyBtn.on("click", function(){
		delForm.attr("action", "/crud/free/modify");
		delForm.attr("method", "get");
		delForm.submit();
	});
	
	deleteBtn.on("click", function(){
		if(confirm("진짜 삭제? 진짜??")){
			delForm.submit();
		}
	});
	
	
	
	
});


</script>

</html>