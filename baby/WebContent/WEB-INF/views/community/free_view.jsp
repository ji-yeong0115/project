<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.community.model.vo.Attachment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kh.baby.community.model.vo.Board"%>
<% 
	Board board = (Board)request.getAttribute("board");
	ArrayList<Attachment> fList = (ArrayList<Attachment>)request.getAttribute("fList");
	String cp = request.getParameter("cp");
	String type = request.getParameter("type");
	int boardNo = board.getBoardNo();
	String memberId = board.getMemberId();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 상세 조회</title>
<style>
	#content-area{ margin-bottom:200px;}
	#board-content{ padding-bottom:150px;}
	
	html{width:100%;height:100%;}
	

	#board-imgArea img { display:block;  }
	
	  /* 팝업 레이어 */
   .idSelect {
      cursor: pointer;
   }
   
   .popupLayer {
      position: absolute;
      display: none;
      background-color: #ffffff;
      border: solid 2px #d0d0d0;
      width: 250px;
      height: 250px;
      padding: 10px;
   }
   .popupLayer div:nth-of-type(1) {
      position: absolute;
      top: 5px;
      right: 5px
   }
   
	#board-content {
			margin-top: 30px;
		
		}
	
		#updateBtn{
		text-decoration: "none";
		color: black }

	.boardImgArea img{
		width : 100%;
		height: 100%;
		
		max-width : 300px;
		max-height: 300px;
		
		margin : auto;
	}

	  
	
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

	#deleteBtn{
    	cursor : pointer;
    }
    


</style>
  
  <!-- Bootstrap core CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

	<style>
	     body {
	       padding-top: 56px;
	     }
	     
	     #content-area{
	     height: 100%;
	     
	     }
	     
	 </style>
	 
	   <!-- sweetalert : alert창을 꾸밀 수 있게 해주는 라이브러리 https://sweetalert.js.org/ -->
  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	 
</head>



<body>

	<div class="container-fluid">
		<%@ include file="../common/header.jsp" %> 
	
		<div class="row" id="content">
	  <%@ include file="../common/aside.jsp" %>

	
	<!-- Bootstrap core JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

	<script>
		// 로그인 유효성 검사
		// -> 아이디, 비밀번호 중 하나라도 입력되지 않으면 form태그 기본 이벤트를 제거한다
		function loginValidate(){
			if ($("#memberId").val().trim() == ""){
				alert("아이디를 입력해 주세요.");
				$("#memberId").focus();
				return false; // 기본 이벤트 제거 
			} 
			
			if ($("#memberPwd").val().trim() == ""){
				alert("비밀번를 입력해 주세요.");
				$("#memberPwd").focus();
				return false; // 기본 이벤트 제거 
			} 
			
			return true; // if문에 안 걸렸으면 기본 이벤트 실행해라 라는 뜻
			
		}
	
	</script>


	<div class="container my-5">

		<div id="content-area">

            <!-- Title -->
                 
			<p>
			<span class="float-left">	커뮤니티 </span>
			</p>
			
			<br>
			<p>
			<span class="float-left">
				<h2 class="mt-4">[<%=board.getCategoryName() %>]<%=board.getBoardTitle()%></h2>
			</span>
			</p>

			<!-- Writer -->
			
			<div>
			<p class="lead">
				<span class="float-left"> 작성자 : <a class="idSelect"><%=board.getMemberName() %></a>
                &nbsp;| &nbsp;
           		<%=board.getBoardCreateDate() %></span>
				 <span class="float-right">조회수  <%=board.getReadCount() %></span>
			</p>
			</div>
			<hr>

			<br>
			<div>
				<span class="float-left">
					<p>좋아요 <%=board.getBoardLike() %> </p></span>
					
					<!-- 로그인한 일반 회원만 글을 수정/삭제할 수 있다 -->
					
  	<%if(loginMember != null  && loginMember.getMemberGrade().equals("G") && loginMember.getMemberId().equals(memberId)) {%>				 
  		<span class="float-right" id="deleteBtn">
				 삭제</span>
				 <span class="float-right">|&nbsp;&nbsp;</span>
         <span class="float-right">
         	<a href="freeUpdateForm.do?type=<%=type%>&cp=<%=cp%>&no=<%=board.getBoardNo()%>" id="updateBtn">수정 &nbsp; </a>
         
         </span>
         <!-- 관리자는 모든 커뮤니티 글 삭제가능, 글수정은 본인이 작성한 글만 -->
  	<%} else if (loginMember != null  && loginMember.getMemberGrade().equals("A"))  {%>				 
  		<span class="float-right" id="deleteBtn">
				 삭제</span>
				 	
				 	<% if(loginMember.getMemberId().equals(memberId)) { %>
				 <span class="float-right">|&nbsp;&nbsp;</span>
         <span class="float-right">
         	<a href="freeUpdateForm.do?type=<%=type%>&cp=<%=cp%>&no=<%=board.getBoardNo()%>" id="updateBtn">수정 &nbsp; </a>
         
         </span>
				 
				 <% } %>
         	<% } %>
         	
         	
         	
         	
         	
			</div>
			<br>
			


			<!-- Content -->
		<!-- 이미지 -->
	
		
		<!-- 게시판 내용 -->
			<div id="board-content">
			
						<div id="board-imgArea">
               <% if(fList != null){ %>
                    <div class="boardImgArea">
                     <% 
	                   		String src = null;
                   			for(int i=0; i<4 ; i++) {
                   			 for(Attachment at : fList){
	                    	  if(at.getFileLevel() == i){
	                    		 src = request.getContextPath()+"/resources/uploadImages/"+at.getFileChangeName();
                 	   %> 	  
	                    		
	                          <img src="<%=src%>">
	                       
	                    <%  } } } //여기 %>
	                    </div> 
            
                <% } %>
						</div>
		
						<div id="board-textArea">
						<%=board.getBoardContent() %><br>
						</div>
			</div>
			
			<hr>
			
			<div>
				
               
            </div>
            <div>
                <a class="btn btn-primary float-right" href="<%=request.getContextPath()%>/community/freeList.do?type=3&cp=<%=cp%>">목록으로</a> 
              <%if(loginMember != null && loginMember.getMemberGrade().equals("G")){ %>
             	<span class="float-left">   <a href="#" class="btn btn-primary"  id="reportBtn" data-toggle="modal" data-target="#en-modal">신고하기</a> &nbsp;&nbsp;</span>
             
           
             	<span class="float-left">   <a href="#" class="btn btn-primary" id="likeBtn">좋아요</a> &nbsp;&nbsp;</span>
             <%} %>
             <br>
			</div>
			

                                    
                 
			
	<%@ include file="../community/community_reply.jsp" %>


				</div>
		</div>
	</div>
	<%@include file="../common/footer.jsp" %>
	</div>
	
	
	   <!-- Modal -->
           <div class="modal fade" id="en-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
             <div class="modal-dialog">
                 <div class="modal-content" style="width: 400px;">
                 <div class="modal-header">
                     <h5 class="modal-title" id="exampleModalLabel">신고하기</h5>
                     <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                     <span aria-hidden="true">&times;</span>
                     </button>
                 </div>
                 <div class="modal-body">
                     <form action="<%=request.getContextPath()%>/adminPage/reportInsert.do" id="information-en" method="POST">
                        <input type="hidden" name="type" value="<%=type%>">
                        <input type="hidden" name="reportType" value="reportType">
                        <input type="hidden" name="cp" value="<%=cp %>">
                        <input type="hidden" name="boardNo" value="<%=board.getBoardNo()%>">                     
                    
                         <input type="radio" name="report" value="1" id="1" class="input-wid" ><label for="1">성적인 콘텐츠</label><br> <br>
                         <input type="radio" name="report" value="2" id="2" class="input-wid" ><label for="2">폭력적 콘텐츠</label> <br><br>
                         <input type="radio" name="report" value="3" id="3" class="input-wid" ><label for="3">광고</label> <br><br>
                         <input type="radio" name="report" value="4" id="4" class="input-wid" ><label for="4">유해한 콘텐츠</label> <br><br>
                         <input type="radio" name="report" value="5" id="5" class="input-wid" ><label for="5">아동 학대</label> <br><br>
                         <input type="radio" name="report" value="6" id="6" class="input-wid" ><label for="6">테러 조장</label> <br><br>
                         <input type="radio" name="report" value="7" id="7" class="input-wid" ><label for="7">스팸 콘텐츠</label> <br><br>
                         <input type="radio" name="report" value="8" id="8" class="input-wid" ><label for="8">권리 침해</label> <br><br>
                        
                         <div class="modal-footer" style="clear: both;">
                             <button type="submit" class="btn btn-primary" id="inforBtn">요청</button>
                             <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                         </div>
                       
                     </form>
                 </div>
                 </div>
             </div>
         </div> 
	
	  
	
	<script>
		$("#deleteBtn").on("click",function(){
			if(confirm("정말 삭제 하시겠습니까?")) location.href = "freeDelete.do?type=<%=type%>&cp=<%=cp%>&no=<%=board.getBoardNo()%>";
		});
		
		
		
		
		
		
	      
	     // 글자수 제한
	     
	        $("textarea").on("input", function() {
		        
		        var inputLength = $(this).val().length;
		  
		        $("#counter").text(inputLength);
		  
		        if(inputLength - 150 < 0) {
		          $("#counter").css("color", "black");
		        } else {
		          $("#counter").css("color", "red");
		        }
		        
		        if(inputLength > 150){
		     	   alert("최대 150자까지 입력 가능합니다.");	        	   
		        }
		      });
	     
		
	     // 좋아요기능
		    var no = "<%=boardNo%>";
		    var type = "<%=type%>";
		    
		    $("#likeBtn").on("click", function() {
		    	$.ajax({
		    		url : "updateLike.do",
		    		data : {"no" : no, "type" : type},
		    		success : function(result) {
		    			if(result) {
		    				$(this).attr("disabled","true");
		    				swal("해당 게시글에 좋아요를 눌렀습니다.");
		    			}  else {
		    				swal("좋아요 클릭 과정 중 오류 발생");
		    			}
		    		}, error : function() {
		    			console.log("ajax 통신 실패");
		    		}
		    	});
		    });
		
	</script>


</body>
</html>
    