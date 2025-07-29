<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
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
							<h2>일반게시판 <font class="text-add">등록</font></h2>
						</div>
					</div>
					<div class="card-body">
						<form id="frm">
							<table class="table table-bordered">
								<tr>
									<td>제목</td>
									<td>
										<input type="text" class="form-control" name="title" id="title"/>
									</td>
								</tr>	
								<tr>
									<td>내용</td>
									<td>
										<textarea rows="5" cols="10" class="form-control" name="content" id="content"></textarea>
									</td>
								</tr>
								<tr>
									<td>파일</td>
									<td>
										<input type="file" id="inputFile" multiple="multiple"/>
									</td>
								</tr>
								<tr>
									<td>파일 내용</td>
									<td id="fileList">
										
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div class="card-footer">
						<button type="button" id="addBtn" class="btn btn-primary"><font class="text-add">등록</font></button>
						<button type="button" id="cancelBtn" class="btn btn-warning" onclick="javascript:location.href='/rest/detail/${no}'">취소</button>
						<button type="button" id="listBtn" class="btn btn-primary" onclick="javascript:location.href='/rest/list'">목록</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
function makeUpdateForm(){
	$.ajax({
		url : "/api/detail/" + boardNo,
		type : "get",
		success : function(result){
			$("#title").val(result.boTitle);
			$("#content").val(result.boContent);
			
			if(result.boardFileList != null && result.boardFileList.length > 0){
				let fileNames = ``;
				result.boardFileList.map(function(v,i){
					fileNames += `
						<div>
							<i class="far fa-file"></i> \${v.fileName}
							<span class="badge text-bg-light">\${v.fileFancysize}</span>
							<i class="far fa-times-circle fileDel" data-no="\${v.fileNo}" style="color: #fe5858;"></i>
							<br/>
						</div>
					`;
				});
				$("#fileList").html(fileNames);
			}
		},
		error : function(error, status, thrown){
			console.log(error);
			console.log(status);
			console.log(thrown);
		}
	});
}



// URL에 매핑된 정보에 따른 등록, 수정 페이지 처리 
// isNaN : 숫자가 아니면 true
if(boardNo != null && boardNo != "" && !isNaN(boardNo)){
	$(".text-add").text("수정");
	$("#cancelBtn").show();
	$("#listBtn").hide();
	makeUpdateForm();			// 수정 데이터 설정 
}else{
	$("#cancelBtn").hide();
	$("#listBtn").show();
	
}


$(function(){
	
	let addBtn = $("#addBtn");
	let frm = $("#frm");
	
	
	
	// 게시판 등록/수정 이벤트
	addBtn.on("click", function(){
		// 일반데이터 + 파일데이터
		// 파일이 추가되면 무조건 formdata 구성해주기!!!
		
		let data = new FormData();
		data.append("boTitle", $("#title").val());			// 제목 데이터 설정
		data.append("boContent", $("#content").val());		// 내용 데이터 설정 
		
		
		
		// 파일 데이터 설정 
		let files = $("#inputFile")[0].files;
		if(files != null && files.length > 0){
			console.log("file data...!, length : " + files.length);
			for(let i=0; i<files.length; i++){
				data.append("inputFiles", files[i]);
			}
		}
		
		//  파일 수정 시, x를 눌렀을 떄 (수정할 파일이 존재할 때)
		let delBoardEle = $(".del-board");
		if(delBoardEle != null && delBoardEle.length > 0){
			for(let i =0; i< delBoardEle.length; i++){
				data.append("delBoardNo", delBoardEle[i].value);
			}
		}
		
		if($(this).text() == "수정"){
			update(boardNo, data);
		}else{
			insert(data);
		}

	});
	
	// x 표시 눌러서 파일 취소 이벤트
	$("#frm").on("click", ".fileDel", function(){
		let fileNo = $(this).data("no");
		let ptrn = `<input type="hidden" class="del-board" name="delBoardNo" value="\${fileNo}"/>`;
		$("#frm").append(ptrn); 
		$(this).parent("div:first").hide();
	});
	
	//  게시판 등록 
	function insert(data){
		$.ajax({
			url : "/api/insert",
			type : "post",
			data : data,
			contentType : false,		// multipart 일때 기본 setting
			processData : false,
			beforeSend : function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},
			success : function(result){
				if(result.status == "OK"){
					let no = result.value;
					alert(no + "번 게시글 등록이 완료되었습니다!");
					location.href = "/rest/detail/" + no;
				}else{
					alert("등록 실패, 다시 시도해주세요!");
				}
			},
			error:function(error, status, thrown){
				console.log(error); 
				console.log(status); 
				console.log(thrown); 
				
			}
		});
	}
	
	function update(no,data){
		data.append("boNo", no);
	
		$.ajax({
			url : "/api/update/" + no,
			type : "post",
			data : data,
			contentType : false,
			processData : false, 
			beforeSend : function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},
			success : function(result){
				if(result == "OK"){
					alert(boardNo + "번 게시글 수정이 완료되었습니다!");
					location.href = "/rest/detail/" + boardNo;
				}else{
					alert("수정 실패!")
				}
			},
			error:function(error, status, thrown){
				console.log(error); 
				console.log(status); 
				console.log(thrown); 
				
			}
		
		})
		
		
	}
	
	
});



</script>
</html>