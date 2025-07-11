<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>DDIT BOARD</title>
<%@ include file="../skydash/headPart.jsp" %>
</head>
<body>
	<div class="container-scroller">
		<%@ include file="../skydash/header.jsp" %>
		<div class="container-fluid page-body-wrapper">
			<%@ include file="../skydash/aside.jsp" %>
			<div class="main-panel">
				<div class="content-wrapper">
					<div class="row">
						<div class="col-lg-12 grid-margin stretch-card">
							<div class="card">
								<div class="card-header">
									<h4 class="card-title">1. Model</h4>
								</div>									
								<div class="card-body">
								<p class="card-description">
								1) 매개변수에 선언된 Model 객체의 addAttribute() 메서드를 
								   호출하여 데이터를 뷰(view)에 전달한다.
								</p>
								member.userId : ${member.userId }<br/>
								member.password : ${member.password }<br/>
								member.userName : ${member.userName }<br/>
								member.email : ${member.email }<br/>
								member.birthDay : ${member.birthDay }<br/>
								member.dateOfBirth : ${member.dateOfBirth }<br/>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%@ include file="../skydash/footer.jsp" %>
			</div>
		</div>
	</div>

	<%@ include file="../skydash/footerPart.jsp" %>
</body>
</html>