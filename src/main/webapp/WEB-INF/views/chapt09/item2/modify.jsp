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
								
								<form action="/item2/modify" method="post" enctype="multipart/form-data"
								id="item">
								<input type="hidden" name="itemId" value="${item.itemId }" />
									<table class="table table-bordered">
										<tr>
											<td>상품명</td>
											<td>
												<input class="form-control" type="text" name="itemName" id="itemName" 
												value="${item.itemName }"/>
											</td>
										</tr>
										<tr>
											<td>가격</td>
											<td>
												<input class="form-control" type="text" name="price" id="price" 
												value="${item.price }"/>
											</td>
										</tr>
										<tr>
											<td>파일</td>
											<td>
												<input class="form-control" type="file"  id="inputFile" />
												<div class ="uploadedList">
												
												</div>
											</td>
										</tr>
										<tr>
											<td>개요</td>
											<td>
												<textarea class="form-control" rows="5" cols="20" name="description">${item.description }</textarea>
											</td>
										</tr>
									</table>
									<br/>
									<button class="btn btn-warning" type="submit">Modify</button>
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
	
	// get방식의 비동기 요청 
	let itemId = "${item.itemId}";
	$.getJSON("/item2/getAttach/" + itemId, function(list){
		$(list).each(function(){
			console.log(list);
			
			console.log(this);
			let data = this;
			
			let html= "";
			
			if(checkImageType(data)){	// 이미지라면 이미지태그를 이용하여 출력 
				html = "<div>";
				html += " 		<a href='/item2/displayFile?fileName=" + data +"'>";
				html += "			<img src='/item2/displayFile?fileName="+ getThumbnailName(data)+"'style='border-radius: 0% !important; width: 210px; height: 240px;'/>";
				html += " 		</a>";
				html += "		<span>X</span>";
				html += "</div>";
			} else{	// 파일이면 파일명에 대한 링크로만 출력 
				html = "<div>";
				html += " <a href='/item2/displayFile?fileName=" + data + "'>";
				html += " 	" + getOriginalName(data);
				html += " </a>";
				html += " <span>X</span>";
				html += "</div>";
				
			}
			$(".uploadedList").append(html);
		});
		
	});
	
	
	inputFile.on("change", function(event){
		console.log("change event.....");
		
		let files = event.target.files; // 배열 형태의 파일목록 
		let file  = files[0];			// 선택한 파일 1개의 객체
		
		console.log(file);
		
		let formData = new FormData(); 
		formData.append("file", file);			
		
		$.ajax({
			url : "/item2/uploadFile" ,	// 업로드 처리 컨트롤러 
			type : "post",
			contentType : false,		// 기본 해더를 제거해서 multipart/form-data로 전성 
			processData : false,		// jQuery가 데이터를 문자열로 변환하지 않도록 함 
			data : formData, 
			dataType : "text", 
			success : function(result){
				console.log(result);
				
				let html = ""; 
				if(checkImageType(result)){	// 이미지라면 이미지태그를 이용하여 출력 
					html = "<div>";
					html += " 		<a href='/item2/displayFile?fileName=" + result +"'>";
					html += "			<img src='/item2/displayFile?fileName="+ getThumbnailName(result)+"'style='border-radius: 0% !important; width: 210px; height: 240px;'/>";
					html += " 		</a>";
					html += "		<span>X</span>";
					html += "</div>";
				} else{	// 파일이면 파일명에 대한 링크로만 출력 
					html = "<div>";
					html += " <a href='/item2/displayFile?fileName=" + result + "'>";
					html += " 	" + getOriginalName(result);
					html += " </a>";
					html += " <span>X</span>";
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
	
	
	// 업로드 한 이미지 또는 파일의 X 버튼 
	$(".uploadedList").on("click", "span", function(){
		$(this).parent("div").remove();
	});
	
	item.submit(function(){
		let html= " ";
		$(".uploadedList a").each(function(index){
			 let value = $(this).attr("href");
			 value = value.substr(28); 	// '?fileName=' 다음에 나오는 값 
					 
			html += "<input type='hidden' name='files["+index+"]' value='"+value+"'/>";
		});
		
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
		let front = fileName.substr(0,12);	// /2025/07/16 폴더 
		let end = fileName.substr(12);		// 뒤 파일명 
		return front + "s_" + end;
		
	}
	
	// 파일명 추출
	function getOriginalName(fileName){
		// 이미지 파일이면 return		
		if(checkImageType(fileName)){
			return;
		}
		
		let idx = fileName.indexOf("_") + 1; 
		return fileName.substr(idx);
	}
});





</script>

</html>