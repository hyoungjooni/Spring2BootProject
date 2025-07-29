<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="./module/header.jsp" %>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="card">
					<div class="card-header">
						<div class="card-title">
							<h2>일반게시판 목록</h2>
						</div>
					</div>
					<div class="card-body">
						<table class="table table-bordered">	
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>작성자</th>
									<th>작성일</th>
									<th>조회수</th>
								</tr>
							</thead>
							<tbody id="tBody">
							
							</tbody>
						</table>
					</div>
					<div class="card-footer">
						<button type="button" class="btn btn-primary" onclick="javascript:location.href='/rest/form'">등록</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
function makeList(){
	$.ajax({
		url : "/api/list",
		type : "get",
		success : function(result){
			let html = ``;
			if(result != null && result.length > 0){
				for(let i=0; i<result.length; i++){
					let item = result[i];
					html += `
						<tr onclick="moveBoard('\${item.boNo}')">
							<td>\${item.boNo}</td>
							<td>
							\${item.boTitle} `;
							if(item.cnt > 0){
								html += `<span class ="badge text-bg-dark">O</span>`;
							}
							html += `</td>
							<td>\${item.boWriter}</td>
							<td>\${item.boDate}</td>
							<td>\${item.boHit}</td>
							
							<td>
						</tr>	
					`;
				}
				
			}else{
				html =`
					<tr>
						<td colspan="5">조회하신 게시글이 존재하진 않습니다.</td>
					</tr>
				`;
			}
			$("#tBody").html(html);
		}
		
	});
	
}

makeList();

function moveBoard(no){
	if(no == null){
		alert("요청하신 게시글 번호가 존재하지 않습니다!");
		return false;
	}
	
	location.href ="/rest/detail/" + no;
}

</script>

</html>