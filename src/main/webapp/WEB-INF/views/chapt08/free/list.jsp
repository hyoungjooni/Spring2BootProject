<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
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
								<p class="card-description">LIST</p>
								
								<!-- 검색창 -->
								<form action="/crud/free/search" method="post">
									<input type="text" name="title" value="" placeholder="제목 입력" />
									<input type="submit" value="검색" />
								</form>
								
								<table class="table table-bordered">
									<tr>
										<td align="center" width="90">번호</td>
										<td align="center" width="320">제목</td>
										<td align="center" width="100">작성자</td>
										<td align="center" width="180">작성일</td>
									</tr>
									<c:choose>
										<c:when test="${empty freeList }">
											<tr>
												<td colspan="4">조회하실 게시물이 존재하지 않는다.</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${freeList }" var="free">
												<tr>
													<td>${free.freeNo }</td>
													<td>
														<a href="/crud/free/detail?freeNo=${free.freeNo }">${free.title }</a>
													</td>
													<td>${free.writer }</td>
													<td>
														<fmt:formatDate value="${free.regDate }" pattern="yyyy-MM-dd hh:mm"/>
													</td>
												</tr>
											
											</c:forEach>
										</c:otherwise>
									</c:choose>
								
								</table>
								<br/>
								
								<input type="button" class="btn btn-primary" id="registerBtn" value="등록" />
								
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
	
	// 목록 버튼 이벤트 
	registerBtn.on("click", function(){
		location.href ="/crud/free/register";
	});
	
	
	
	
	
	
});




</script>
</html>