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
							<h2>일반게시판 상세보기</h2>
						</div>
					</div>
					<div class="card-body">
						<table class="table table-bordered">
							<tr>
								<td>번호</td>
								<td id="no"></td>
							</tr>	
							<tr>
								<td>제목</td>
								<td id="title"></td>
							</tr>	
							<tr>
								<td id="etc" colspan="2"></td>
							</tr>
							<tr>
								<td id="content" colspan="2"></td>
							</tr>
							<tr>
								<td>파일</td>
								<td id="files"></td>
							</tr>
						</table>
					</div>
					<div class="card-footer">
						<button type="button" id="delBtn" class="btn btn-danger">삭제</button>
						<button type="button" id="udtBtn" class="btn btn-warning">수정</button>
						<button type="button" class="btn btn-primary" onclick="javascript:location.href='/rest/list'">목록</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

// 게시판 상세보기 메소드
function makeDetail(){
	
	
	$.ajax({
		url: "/api/detail/" + boardNo, 		// api controllerd에 메서드 생성하기 
		type : "get",
		success : function(result){
			$("#no").html(result.boNo);
			$("#title").html(result.boTitle);
			$("#etc").html(result.boWriter + " " + result.boDate + " " + result.boHit);
			$("#content").html(result.boContent);
			
			let fileNames = ``;
			result.boardFileList.map(function(v, i){
				fileNames += `<a href="/api/download/\${v.fileNo}"><i class="far fa-file"></i> \${v.fileName}</a><br/>`;
				
			});
			
			$("#files").html(fileNames);
		},
		error : function(error, status, thrown){
			console.log(error);
			console.log(status);
			console.log(thrown);
		}
	});
	
	
	
}
	
	
function deleteBoard(){
	$.ajax({
		url: "/api/delete/" + boardNo, 		// api controllerd에 메서드 생성하기 
		type : "post",
		beforeSend : function(xhr){
			xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
		},
		success : function(result){
			if(result == "OK"){
				alert(boardNo + "번 게시글 삭제가 완료되었습니다!");
				location.href="/rest/list";
			}else{
				alert("삭제 실패입니다!")
			}
		
		},
		error : function(error, status, thrown){
			console.log(error);
			console.log(status);
			console.log(thrown);
		}
	});
}	


makeDetail();		// 상세보기 데이터 호출 

$(function(){
	let udtBtn = $("#udtBtn");
	let delBtn = $("#delBtn");


	udtBtn.on("click",function(){
		location.href = "/rest/update/" + boardNo;
	});

	delBtn.on("click",function(){
		if(confirm("정말로 삭제하시겠습니까?")){
			deleteBoard();
		}
	});





});


</script>

</html>