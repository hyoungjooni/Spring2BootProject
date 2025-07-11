<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

</head>
<body>
	<!-- 문제)
		 
		 파일을 업로드 합니다. 
		 비동기 요청을 이용해 파일을 서버로 업로드 하고 
		 서버로부터 전달받은 업로드 한 파일의 파일명, 크기, ContentType을 출력해주세요 
		 이떄, 이미지 파일인 경우라면 썸네일을 함께 출력하고 일반적인 파일이면 파일명만 출력해주세요 
		 
		 
		 C ------------------------------------- Server 
		 												전송한 파일명, 크기, ContentType 출력
		 												3개의 출력 데이터 다시 Client callback으로 전달 
		 												
		 전달받은 데이터 결과 출력
		 이떄, 이미지 파일이면 썸네일 까지 출력 
		 일반 파일이면 파일명 출력 
	 -->
	<input type="file" id="inputFile" />

	<div id="resultArea"></div>
</body>
<script type="text/javascript">
function isImageFile(file){
	let ext = file.name.split(".").pop().toLowerCase(); 
	return ($.inArray(ext, ["jpg", "jpeg","gif", "png" ]) === -1) ? false : true;
}

$(function(){
	
	let inputFile = $("#inputFile");
	
	inputFile.on("change", function (event){
		console.log("change event");
		
		let files = event.target.files; 
		let file = files[0]; 
		
		console.log(file);
		
		if(isImageFile(file)){
			let formData = new FormData();
			formData.append("file", file); 
			
			$.ajax({
				url : "/chapt05/test02/upload", 
				type : "post", 
				contentType: false, 
				processData : false, 
				data : formData, 
				success : function (data){
					console.log(data);
					
					
			let html = ""; 
			
			if(isImageFile(file)){
				let reader = new FileReader();
				reader.onload= function(e){
					html += `<p>\${data.fileName}</p>`;
					html += `<img src="\${e.target.result}" width="200" />`;
					html += `<p>\${data.fileSize}</p>`;
					$("#resultArea").append(html);
				}
				reader.readAsDataURL(file);
				
				}else{
					html += `<p>\${data.fileName}</p>`;
					html += `<p>\${data.fileSize}</p>`;
					html += `<p>\${data.contentType}</p>`;
					
					$("#resultArea").append(html);
				}
			
			}
			
				
			})
		}
	})
	
	
	
	
});




</script>
</html>