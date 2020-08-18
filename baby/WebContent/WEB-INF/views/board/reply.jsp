<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.board.model.vo.Board"%>
<%@page import="com.kh.baby.member.model.vo.Member"%>
    
<%
	Member tempMember = (Member)session.getAttribute("loginMember");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>

		/*댓글*/	
.replyWrite>table {
	width: 90%;
	margin-top : 100px;
}

#replyContentArea {
	width: 90%;
}

#replyContentArea>textarea {
	resize: none;
	width: 100%;
}

#replyBtnArea {
	width: 100px;
}

.rWriter {
	display : inline-block;
	margin-right: 30px;
	vertical-align: top;
}

.rDate {
	display : inline-block;
	font-size: 0.5em;
	color: gray;
}

#replyListArea {
	list-style-type: none;
	margin-top: 20px;
	margin-bottom: 0px;
	padding-left: 0px;
}

.rContent, .btnArea{
	display: inline-block;
	box-sizing: border-box;
}

.rContent {
	height: 100%;
	width : 84.5%;
}

.btnArea {
	height: 100%;
	width : 15%;
	text-align: right;
}

.dayText{
	text-align: left;
}

.textSize{
	font-size: 13px;
}

	
</style>

</head>
<body>

				<div id="reply-area ">	
					<!-- 댓글 작성 부분 -->
					<%if(tempMember != null) { %>
						<div class="replyWrite">
							<table align="center">
								<tr>
									<td id="replyContentArea">
										<textArea rows="3" id="replyContent" maxlength="100"></textArea>
									</td>
									<td id="replyBtnArea">
										<button class="btn btn-primary" id="addReply">등록</button>
									</td>
								</tr>
							</table>
						</div>
					<%} %>
	
	
					<!-- 댓글 출력 부분 -->
					<div class="replyList pt-2">
						<ul id="replyListArea">
						  <li class="reply-row" id="1">
									<p class="rWriter"></p> 
									<p class="rDate">
										<br>
										
									</p>
								
								<p class="rContent">
								</p>
								<div class="btnArea">
									<button class="btn btn-primary" id="updateReply">수정</button>
									<button class="btn btn-primary" id="deleteReply">삭제</button>
								</div>
							</li>
							<hr> 
						</ul>
					</div>
					
				</div>
				
<script>

<%
	Member tempMember2 = (Member)session.getAttribute("loginMember");
	Board tempBoard = (Board)request.getAttribute("board");
%>
	var loginMemberId;
	
	<%
		if(tempMember2 != null) {%>
		loginMemberId = "<%=tempMember2.getMemberId()%>";
	<%}%>
	
	$(function(){
		selectReplyList(<%=tempBoard.getBoardNo()%>);
	});

	
	
	function selectReplyList(boardNo){
		var url = "<%=request.getContextPath()%>/reply/selectList.do";
		console.log(boardNo);
		
		$.ajax({
			url : url,
			data : {"boardNo" : boardNo},
			dataType : "JSON",
			success : function(rList){
				console.log(rList);
				
				$("#replyListArea").html("");
				
				$.each(rList, function(i){
					
					var $li = $("<li>").addClass("reply-row");
					
					var $div = $("<div>").addClass("dayText");
						
					var $rWriter = $("<a>")
						.addClass("rWriter").html(rList[i].memberNickName);
				
					var $rDate = $("<p>").addClass("rDate textSize")
						.html("작성일 : " + rList[i].replyWriteDay + "<br>" + 
						"마지막 수정일 : " + rList[i].replyModifyDay);
				
					$div.append($rWriter, $rDate);
								
					var $rContent = $("<p>").addClass("rContent dayText")
									.html(rList[i].replyContent);
					
					var $btnArea = $("<div>").addClass("btnArea");
					
					
					if(rList[i].memberId == loginMemberId){
						
						var $updateReply = $("<button>").addClass(
								"btn btn-primary btn-sm ml")
									.text("수정").attr("onclick", 
										"updateFormReply(this, "+rList[i].replyNo+")");
						
						var $deleteReply = $("<button>").addClass(
						"btn btn-primary btn-sm ml")
							.text("삭제").attr("onclick", 
								"deleteReply("+rList[i].replyNo+")");
						
						$btnArea.append($updateReply, $deleteReply);
					}
						
					var $hr = $("<hr>");
					
					$li.append($div, $rContent, $btnArea);
					
					$("#replyListArea").append($li, $hr);
				});
				
			}, error : function(){
				console.log("ajax 통신 실패");
			}
		});
	}
	

	
	$("#addReply").on("click", function(){
		
			
			// 작성된 댓글 내용을 얻어옴
			var replyContent = $("#replyContent").val();
			
			// 댓글이 작성되어 있는지 유효성 검사 진행
			if(replyContent.trim().length == 0){
				alert("댓글 작성 후 클릭해주세요.");
				$("#replyContent").focus();
			}else{
				// 로그인도 되어있고, 댓글도 작성되어 있는 경우
				var url = "<%=request.getContextPath()%>/reply/insertReply.do";
				
				// 작성자 : 전역변수 loginMemberId 사용
				// 글번호
				var boardNo = <%=tempBoard.getBoardNo()%>
				$.ajax({
					url : url,
					type : "POST",
					data : {"memberId" : loginMemberId, 
							"boardNo" : boardNo,
							"replyContent" : replyContent},
					success : function(result){
						alert(result);
						$("#replyContent").val("");
						// 삽입된 내용을 화면에서 지움
						
						// 갱신된 DB 내용을 다시 조회하여 화면 댓글 목록을 갱신함
						selectReplyList(boardNo);
						
					}, error : function(){
						console.log("ajax 통신 실패");
					}
				});
				
			}

	});
	
	function deleteReply(replyNo){
		
		if(confirm("해당 댓글을 삭제하시겠습니까?")){
			
			var url = "<%=request.getContextPath()%>/reply/deleteReply.do";
			var boardNo = <%=tempBoard.getBoardNo()%>
			
			$.ajax({
				url : url,
				type : "POST",
				data : {"replyNo" : replyNo},
				success : function(result){
					swal(result);
					
					// 삭제 성공 시
					// 댓글 목록을 다시 작성
					selectReplyList(boardNo);
					
				}, error : function(){
					console.log("ajax 통신 실패");
				}
			});
		}
	}
	
	function updateFormReply(e,replyNo){

		   var content = $(e).parent().prev().html();

		   $(e).parent().prev().html('');

		   $(e).parent().prev().append("<textarea style='width:100%; resize:none;'>"+content+"</textarea>");
		   
		   var parent = $(e).parent();

		   $(e).parent().html('');  
		   MBtn = $('<button style="float: left; margin-left: 10px; margin-bottom: 10px;">').addClass('btn btn-primary').text('수정').attr('onclick', 'updateReply(this, '+replyNo+')');
		   
		   parent.append(MBtn);
		   
		}
	   
	function updateReply(e,replyNo) {
		   var url = '<%=request.getContextPath()%>/reply/updateReply.do';
			var boardNo = <%=tempBoard.getBoardNo()%>
		   
		   var content = $(e).parent().prev().children('textarea').val();
		   console.log(content);
		   
		   $.ajax({
		      url : url,
		      data : {'replyNo':replyNo,
		            'replyContent':content},
		      success : function(result){
		         swal(result);
		         
		         selectReplyList(boardNo);
		         
		      }, error : function(){
		         console.log('ajax통신 실패');
		      }
		      
		   });
		}


		


</script>				

</body>
</html>