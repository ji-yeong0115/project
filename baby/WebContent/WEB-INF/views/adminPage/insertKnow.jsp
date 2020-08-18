<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>WebServer Project</title>
  
  <!-- Bootstrap core CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

<style>

	@font-face { font-family: 'InfinitySans-BoldA1'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-BoldA1.woff') 
	format('woff'); font-weight: normal; font-style: normal; }
	
	
	#title{
		font-family: 'InfinitySans-BoldA1';
	}

	
	#content-main{
		width: 80%;
	}
   
	#notice-content {
			margin-top: 30px;
	}
	
	.textleft{
		text-align: left;
	}
	
	.margin-none{
		margin-top: 0px;
	}
	
	.btn-margin{
		margin-right: 15px;
	}
	
	#wrtieDay{
		margin-bottom: 0px;
	}
	
	.paddingTitle{
		padding-left: 15px;
	}
	
	.marginBotNone{
		margin-bottom: 0px;
	}
	.listBtn{
		float: left;
		margin-right: 4px;
	}
	
	.modBtn{
		float: right;
		margin-right: 15px;
	}
	
	.padding-none{
		padding-left: 0px;
		padding-right: 0px;
	}
</style>
</head>
<body>


	<div class="container-fluid">
		<%@ include file="../common/header.jsp" %> 
	
		<div class="row" id="content">
	  	<%@ include file="../common/aside.jsp" %>
	  
		  	<div id="content-main" style="padding-right: 15px; margin-top: 48px; " >
	
			<form method="POST" action="insertKnow.do" onsubmit="return validate();">
                   <div id="notice-area" style="width: 100%;">	
	                    <div class="col-md-12 padding-none">
	                    	<h3>상식게시판 글 작성</h3> <br>
							<table>
							<tr>
							<th class="input-group-addon mr-3 insert-label">카테고리</th> 
							<td align="left">
							<select id="select" name="select">
								<option value="3">0~3세</option>
								<option value="4">4세</option>
								<option value="5">5세</option>
								<option value="6">6세</option>
								<option value="7">7세</option>
							</select>
							</td>
							</tr>
							<tr>
								<th class="input-group-addon mr-3 insert-label">제목</th> 
								<td style="width: 94%;"><input type="text" id="title" name="title" class="form-control"></td>
							</tr>
							<tr>	
      							<th class="input-group-addon mr-3 insert-label">내용</th> 
								<td><textarea class="form-control" id="boardContent" name="content" rows="10" style="resize: none;"></textarea></td>
							</tr>
							</table>			
		                    
		                    <button class="btn btn-primary modBtn ml-1 mr-1">등록</button>                  
							</div>
            		</div>
                </form>
				
		</div>
	</div>
	<%@include file="../common/footer.jsp" %>
	</div>
<script>

// 유효성 검사 
function validate() {
	if ($("#title").val().trim().length == 0) {
		alert("제목을 입력해 주세요.");
		$("#title").focus();
		return false;
	}

	if ($("#boardContent").val().trim().length == 0) {
		alert("내용을 입력해 주세요.");
		$("#boardContent").focus();
		return false;
	}
}
</script>
</body>
</html>


    