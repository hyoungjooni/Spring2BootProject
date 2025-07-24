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
									<h4 class="card-title">Chapt09</h4>
								</div>									
								<div class="card-body">
								<p class="card-description">REGISTER</p>
								
								<form action="/item2/register" method="post" enctype="multipart/form-data"
								id="item">
									<table class="table table-bordered">
										<tr>
											<td>상품명</td>
											<td>
												<input class="form-control" type="text" name="itemName" id="itemName" />
											</td>
										</tr>
										<tr>
											<td>가격</td>
											<td>
												<input class="form-control" type="text" name="price" id="price" />
											</td>
										</tr>
										<tr>
											<td>파일</td>
											<td>
												<input class="form-control" type="file"  id="inputFile" />
												<div class ="uploadedList"></div>
											</td>
										</tr>
										<tr>
											<td>개요</td>
											<td>
												<textarea class="form-control" rows="5" cols="20" name="description"></textarea>
											</td>
										</tr>
									</table>
									<br/>
									<button class="btn btn-warning" type="submit">Register</button>
									<button class="btn btn-primary" type="button" onclick="javascript:location.href='/item2/list'">List</button>
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
	
	// 1st
	let item = $("#item");
	let inputFile = $("#inputFile");		// open 파일을 위한 Element
	
	
	
//===============================================================
// 1. 파일 선택 시 이벤트 처리 
//===============================================================

	inputFile.on("change", function(event){
		console.log("change event.....");
		
		
		// 사용자가 선택한 파일들 (파일리스트) 객체 
		let files = event.target.files; // 파일 input 태그 자체를 가리키게 되고, .files를 통해 사용자가 선택한 파일들의 목록을 얻을 수 있음 
		let file  = files[0];			// 선택한 파일 1개의 객체
		
		// event : 이벤트에 대한 정보를 담은 객체 
		// event.target : 이벤트가 발생한 실제 DOM 요소 (#inputFile 요소를 가리킴 )
		// event.target.files : <input type="file">에서 선택된 파일 리스트 
		
		console.log(file);
		
		
		// FormData 생성 => 비동기 전송을 위해 사용 
		let formData = new FormData(); 
		formData.append("file", file);	// file이라는 key로 파일을 추가 
	
		
		
//===============================================================
// 2. Ajax로 서버에 업로드 요청 
//===============================================================
		
		$.ajax({
			url : "/item2/uploadFile" ,		// 파일 업로드 처리할 컨드롤러 
			type : "post",
			contentType : false,			// multipart/form-data 전송을 위해 false 
			processData : false,			// jQuery가 데이터를 문자열로 바꾸지 않도록 false
			data : formData, 
			dataType : "text", 				// 서버에 문자열로 응답 
			success : function(result){		// 업로드 성공 시 
				console.log(result);		// 서버가 보내준 저장 경로(문자열)
				
			
				
				
				let html = ""; 
//===============================================================
// 3. 서버가 응답한 파일경로로 UI에 업로드목록 추가 
//===============================================================
				if(checkImageType(result)){	// 이미지라면 이미지태그를 이용하여 출력 
					
					html = "<div>";
					html += " 		<a href='/item2/displayFile?fileName=" + result +"'>"; // 클릭하면 원본파일 보기 
					// 썸네일 이미지 표시 (getThumnailName으로 썸네일 경로 생성)
					html += "			<img src='/item2/displayFile?fileName="+ getThumbnailName(result)+"'style='border-radius: 0% !important; width: 210px; height: 240px;'/>";
					html += " 		</a>";
					html += "		<span>X</span>";
					html += "</div>";
				} else{	// 파일이면 파일명에 대한 링크로만 출력 
					html = "<div>";
					html += " <a href='/item2/displayFile?fileName=" + result + "'>";
					html += " 	" + getOriginalName(result);
					html += " </a>";
					html += " <span>X</span>";	// xqjxms 
					html += "</div>";
					
				}
				$(".uploadedList").append(html);
			},
			error : function(){
				console.log(error);
				console.log(status);
				console.log(thrown);
			}
		});
		
	});
	
//===============================================================
// 업로드 목록에서 X 버튼 클릭 시 삭제 
//===============================================================
	
	// 업로드 한 이미지 또는 파일의 X 버튼 
	$(".uploadedList").on("click", "span", function(){
		$(this).parent("div").remove();
	});
	
	
//===============================================================
// 폼 전송 시 , 업로드된 파일 목록을 hidden input으로 추가 
//===============================================================
			
	item.submit(function(){
		let html= "";
		// .uploadedList 안의 <a> 태그들을 돌면서 파일경로 추출 
		$(".uploadedList a").each(function(index){
			 let value = $(this).attr("href");		// "/item2/displayFile?fileName=/2025/07/16/abc.jpg"
			 value = value.substr(28); 				// '?fileName=' 이후 실제 경로만 추출 (28은 직접 세팅한 값)
				
			 
			 console.log(value);	 
			
			 // hidden input 생성 (files[0], files[1], ... 이런식으로 서버로 전송 )
			 html += "<input type='hidden' name='files["+index+"]' value='"+value+"'/>";
		});
		
		// 폼 안에 hidden input들 추가 
		$(this).append(html);
	});
	
	
	
	// 이미지 파일인지 확인 
	function checkImageType(fileName ){
		console.log("야 이미지 체크하냐?");
		let pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern);		// 패턴과 일치하면 true (너 이미지구나?)
	}
	
	
	
	// 임시 파일로 썸네일 이미지 만들기 
	function getThumbnailName(fileName){
		let front = fileName.substr(0,12);	// 앞부분 폴더 경로 예: /2025/07/16
		let end = fileName.substr(12);		// 파일명 부분
		return front + "s_" + end;			// s_ 접두어 붙여 썸네일 경로 생성 
		
	}
	
	
	
	// 원본 파일명 추출
	function getOriginalName(fileName){
		// 이미지 파일이면 원본파일명 필요없으므로 리턴 	
		if(checkImageType(fileName)){
			return;
		}
		
		// 파일명에서 UUID와 구분자인 "_" 이후의 실제 파일명만 반환 
		let idx = fileName.indexOf("_") + 1; 
		return fileName.substr(idx);
	}
});





</script>

</html>