<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String type = request.getParameter("type"); 
	String cp = request.getParameter("cp");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 글쓰기</title>
<style>
    .insert-label {
      display: inline-block;
      width: 80px;
      line-height: 40px
    }
    
    .boardImg{
    	cursor : pointer;
    }
</style>
</head>
<body>
			<div class="container-fluid">
			<%@ include file="../common/header.jsp" %>
		
			<div class="row" id="content">
		  <%@ include file="../common/aside.jsp" %>

		<div class="container my-5">

			<h3>게시글 등록</h3>
			<hr>
		
			<form action="<%=request.getContextPath()%>/community/freeInsert.do?type=<%=type%>&cp=<%=cp%>" method="post" 
				  enctype="multipart/form-data" role="form" onsubmit="return validate();">

			<div class="form-inline mb-2">

					<label class="input-group-addon mr-3 insert-label">카테고리</label> 
					<select	class="custom-select" id="category" name="category" style="width: 150px;">
						<option value="수다">수다</option>
						<option value="나눔">나눔</option>
						<option value="질문">질문</option>
		
					</select>
				</div>
				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">제목</label> 
					<input type="text" class="form-control" id="texttitle" name="texttitle" size="70">
				</div>


				<hr>

				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">썸네일</label>
					<div class="boardImg" id="titleImgArea">
						<img id="titleImg" width="200" height="200">
					</div>
				</div>

				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">업로드<br>이미지</label>
					<div class="mr-2 boardImg" id="contentImgArea1">
						<img id="contentImg1" width="150" height="150">
					</div>

					<div class="mr-2 boardImg" id="contentImgArea2">
						<img id="contentImg2" width="150" height="150">
					</div>

					<div class="mr-2 boardImg" id="contentImgArea3">
						<img id="contentImg3" width="150" height="150">
					</div>
				</div>


				<!-- 파일 업로드 하는 부분 -->
				<div id="fileArea">
					<!--  multiple 속성
						- input 요소 하나에 둘 이상의 값을 입력할 수 있음을 명시 (파일 여러개 선택 가능)
						
																							onchange : input태그 value값이 변했을때
					 -->
					<input type="file" id="img1" name="img1" onchange="LoadImg(this,1)"> 
					<input type="file" id="img2" name="img2" onchange="LoadImg(this,2)"> 
					<input type="file" id="img3" name="img3" onchange="LoadImg(this,3)"> 
					<input type="file" id="img4" name="img4" onchange="LoadImg(this,4)">
				</div>

				<div class="form-group">
					<div class="form-inline mb-2">
				
						<label for="content">내용</label>
					</div>
					<textarea class="form-control" id="textcontent" name="textcontent"
						rows="10" style="resize: none;"></textarea>
				</div>


				<hr class="mb-4">

				<div class="text-center">
					<button type="submit" class="btn btn-primary">등록</button>
					<button type="button" class="btn btn-primary"  onclick="location.href ='freeView.do'">목록으로</button>
				</div>

			</form>
		</div>
		</div>
			<%@ include file="../common/footer.jsp" %>
			</div>



	<script>
		// 오늘 날짜 출력 
		var today = new Date();
		var month = (today.getMonth()+1);

		var str = today.getFullYear() + "-"
				+ (month < 10 ? "0"+month : month) + "-"
				+ today.getDate();
		$("#today").html(str);

		// 유효성 검사 
	function validate() {
			if ($("#texttitle").val().trim().length == 0) {
				alert("제목을 입력해 주세요.");
				$("#texttitle").focus();
				return false;
			}

			if ($("#textcontent").val().trim().length == 0) {
				alert("내용을 입력해 주세요.");
				$("#textcontent").focus();
				return false;
			}
		}
		
		 // 이미지 공간을 클릭할 때 파일 첨부 창이 뜨도록 설정하는 함수
	    $(function () {
	       $("#fileArea").hide();

	      $("#titleImgArea").click(function () {
	        $("#img1").click();
	      });
	      $("#contentImgArea1").click(function () {
	        $("#img2").click();
	      });
	      $("#contentImgArea2").click(function () {
	        $("#img3").click();
	      });
	      $("#contentImgArea3").click(function () {
	        $("#img4").click();
	      });

	      $(".boardImg").on("click",function(){
	    	  var index =  $(this).children("img").prop("id") + 1;
	        console.log(index);
	        $("#img" + index).click();
	      });

	        

	    });

	    // 각각의 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
	    function LoadImg(value, num) {
	      if (value.files && value.files[0]) {
	    	  // value(현재요소)
	    	  //value.files : 현재 요소에 파일이 업로드되어 있는가?
	    		//value.files[0] : multiple 속성 사용으로 인해 여러 파일이 업로드되었을 때 첫 번째 파일이 존재하는가?
	    				
		       	 var reader = new FileReader();
						
		        reader.onload = function (e) {
		        	
		          switch (num) {
		            case 1:
		              $("#titleImg").attr("src", e.target.result);
		              break;
		            case 2:
		              $("#contentImg1").attr("src", e.target.result);
		              break;
		            case 3:
		              $("#contentImg2").attr("src", e.target.result);
		              break;
		            case 4:
		              $("#contentImg3").attr("src", e.target.result);
		              break;
		          }
		        }
	
		        reader.readAsDataURL(value.files[0]);
	      }
	    }
		
	</script>
</body>
</html>
