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
									<h4 class="card-title">Chapt08</h4>
								</div>									
								<div class="card-body">
								<p class="card-description">LIST</p>
								
								<table class="table table-bordered">
									<tr>
										<td align="center" width="60">번호</td>
										<td align="center" width="80">아이디</td>
										<td align="center" width="50">비밀번호</td>
										<td align="center" width="50">사용자명</td>
										<td align="center" width="180">작성일</td>
									</tr>
									<c:choose>
										<c:when test="${empty memberList }">
											<tr>
												<td colspan="4">조회하실 회원이 존재하지 않습니다.</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${memberList }" var="member">
												<tr>
													<td>${member.userNo }</td>
													<td>
														<a href="/crud/member/read?userNo=${member.userNo }">
															${member.userId }
														</a>
													</td>
													<td>${member.userPw }</td>
													<td>${member.userName }</td>
													<td>
														<fmt:formatDate value="${member.regDate }" pattern="yyyy-MM-dd hh:mm"/>
													</td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
									<tr>
									</tr>
								</table>
								<br/>
								<a href="/crud/member/register" class="btn btn-warning">등록</a>
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
	
	// 목록 버튼 이벤트 
	btnList.on("click", function(){
		location.href ="/crud/board/list";
	});
	
	
	
	
	
	
});




</script>
</html>