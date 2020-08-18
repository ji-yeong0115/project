<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 작성</title>
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
</head>
<div class="container-fluid">
<%@ include file="../common/header.jsp" %>

<div class="row">
<%@ include file="../common/aside.jsp" %>  
  
<div class="container">
<div class="my-5">		
<h3>공지사항 등록</h3>
			<hr>
			<form action="<%=request.getContextPath()%>/adminPage/insertNotice.do" method="post" role="form" onsubmit="return validate();">
				<div class="mb-2">
					<label class="input-group-addon mr-3 insert-label" for="select">카테고리</label> 
					<select	class="custom-select" id="type" name="type" style="width: 150px;">
						<option value="1">병원</option>
						<option value="3">자유</option>
						<option value="4">홍보</option>
					</select>
				</div>
				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">제목</label> 
					<input type="text" class="form-control" id="title" name="title" size="100">
				</div>

				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">작성자</label>
					<h5 class="my-0" id="writer"><%=loginMember.getMemberId() %></h5>
				</div>

				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">작성일</label>
					<h5 class="my-0" id="today"></h5>
				</div>

				<div class="form-inline mb-2">
					<label for="content" class="input-group-addon mr-3 insert-label">내용</label>
					<textarea class="form-control" id="content" name="content" rows="10" cols="100" style="resize: none;"></textarea>
				</div>
				
				<hr class="mb-4">

				<div class="text-center">
					<button type="submit" class="btn btn-primary">등록</button>
					<button type="button" class="btn btn-primary" onclick="location.href='noticeBoard.do'">목록으로</button>
				</div>

			</form>
		</div>
		</div>
		</div>

		<%@ include file="../common/footer.jsp"%>
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
			if ($("#title").val().trim().length == 0) {
				alert("제목을 입력해 주세요.");
				$("#title").focus();
				return false;
			}

			if ($("#content").val().trim().length == 0) {
				alert("내용을 입력해 주세요.");
				$("#content").focus();
				return false;
			}
		}
		
		
	</script>
</body>
</html>