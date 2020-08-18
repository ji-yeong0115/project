<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String time = (String)(request.getAttribute("time")); %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>관리자 글쓰기</title>	
</head>
<body>
<div class="container-fluid">
<%@ include file="../common/header.jsp" %>

<div class="row" id="content">
<%@ include file="../common/aside.jsp" %>  
  
<div class="container">
<div class="my-5">
<div><h3>병원게시글 작성</h3></div>
<form action="<%=request.getContextPath()%>/adminPage/insertHospital.do?" method="post" onsubmit="return validate();">
<table>
		<tr>
			<th class="input_txt">카테고리</th>
			<td align="left">병원게시판</td>
		</tr>
		<tr>
			<th>제목 </th>
			<td style="width: 94%;"><input type="text" id="title" name="title" class="form-control"></td>
		</tr>
	           	
	    <tr>
	       <th>영업시간 </th>
		    <td>
		       <input type="text" id="hosTime" name="hosTime" class="form-control" value="<%=time%>">
		    </td>
	    </tr>
	    <tr>
	       <th>병원주소 </th>
		    <td>
		       <input type="text" id="hosAddress" name="hosAddress" class="form-control">
		    </td>
	    </tr>
	    <tr>
	       <th>야간진료 여부 </th>
	    <td>
	      <div class="form-check form-check-inline">
		  <input class="form-check-input" type="radio" name="nitghYN" id="nightY" value="Y">
		  <label class="form-check-label" for="nightY">야간진료 가능</label>
		  </div>
		  <div class="form-check form-check-inline">
		  <input class="form-check-input" type="radio" name="nitghYN" id="nightN" value="N" checked>
		  <label class="form-check-label" for="nightN">야간진료 불가능</label>
		  </div>
		</td>
		</tr>
	    <tr>
		    <th>주말진료 여부 </th>
		    <td>
	
			<div class="form-check form-check-inline">
			  <input class="form-check-input" type="radio" name="weekYN" id="weekendY" value="Y">
			  <label class="form-check-label" for="weekendY">주말진료 가능</label>
			</div>
			<div class="form-check form-check-inline">
			  <input class="form-check-input" type="radio" name="weekYN" id="weekendN" value="N" checked>
			  <label class="form-check-label" for="weekendN">주말진료 불가능</label>
			</div>									
	        </td>
		</tr>
		</table>
		
		<table>
		<tr>
			<td align=right style="padding-top:5px; border:none;">
			<input type="submit" class="bhs_button yb" value="등록" style="float:none;">
			</td>
		</tr>
		</table>
	</form>
	</div>
</div>
</div>
<%@ include file="../common/footer.jsp" %>
</div>

<script>

// 유효성 검사 
function validate() {
	if ($("#title").val().trim().length == 0) {
		alert("제목을 입력해 주세요.");
		$("#title").focus();
		return false;
	}
	
	if($("#hosAddress").val().trim().length==0){
		alert("주소를 입력해 주세요.");
		$("#hosAddress").focus();
		return false;
	}
}
</script>
</body>
</html>