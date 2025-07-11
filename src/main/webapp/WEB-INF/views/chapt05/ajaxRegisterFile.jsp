<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h4>10. 파일업로드 Ajax 방식 요청 처리</h4>
	<hr/>
	
	<p>Ajax 방식으로 전달한 파일 요소값을 스프링 MVC가 지원하는 MultipartFile 매개변수로 처리한다.</p>
	<div>
		<input type ="file" id="inputFile" /><br/>
		<hr/>
		<img id="preview" />
	</div>
</body>
<script type="text/javascript">
$(function(){
	let inputFile = $("#inputFile");
	
	inputFile.on("change", function(event){
		console.log("change event....");
		
		 let files = event.target.files;	// 내가 선택한 파일 
		 let file = files[0];				// 내가 선택한 파일 (단일 파일 1개)
		 
		 console.log(file);
		 
		 if(isImageFile(file)){
			 let formData = new FormData(); 
			 formData.append("file", file);
			 // 비동기일때 formData 사용필수!
			 
			 
			 $.ajax({
				 url : "/chapt05/ajax/uploadFile",
				 type: "post",
				 contentType : false,
				 processData : false,
				 data : formData, 
				 success : function (data){
					 console.log(data);
					 
					 if(data === "UPLOAD SUCCESS"){
						 // 내가 선택한 파일을 이용한 썸네일 출력 
						 let file = event.target.files[0];
						 let reader = new FileReader();
						 reader.onload = function(e){
							 $("#preview").attr("src", e.target.result);
						 }
						 reader.readAsDataURL(file);
						 
					 }
				 }, 
				 error : function(error, status, thrown){
					 console.log(error);
					 console.log(status);
					 console.log(thrown);
				 }
			
			 });
		 }else{
			 alert("이미지를 넣어주세요!");
		 }
	});
	
});


// Change 이벤트가 발생했을 때 선택된 파일이 이미지인지 검증 
function isImageFile(file){
	let ext = file.name.split(".").pop().toLowerCase(); // 파일명에서 확장자를 가져온다. (소문자로 변환)
	return ($.inArray(ext, ["jpg","jpeg","gif","png"]) === -1) ? false : true;

}

</script>

</html>