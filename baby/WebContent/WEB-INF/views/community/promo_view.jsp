<%@page import="com.kh.baby.community.model.vo.Attachment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kh.baby.community.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>홍보게시판 상세조회</title>
<style>
#content-area{ margin-bottom:200px;}
	#board-content{ padding-bottom:150px;}
	
	html{width:100%;height:100%;}
	

	#board-imgArea img { display:block; }
	
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



	.boardImgArea img{
		width : 100%;
		height: 100%;
		
		max-width : 300px;
		max-height: 300px;
		
		margin : auto;
	}

  

		.promo_area{
                width:100%;
                height: 300px;
                float:left;
                margin-top : 30px;
            }
            
		#updateBtn{
		text-decoration: "none";
		color: black }
		
		#promo_thum{
			width: 50%;
			height: 100%;
			float:left;
			text-align: center;
			
		}
		
		#promo_detail{
			width: 50%;
			height: 100%;
			float:left;
	
		}

		#promo_detail td {
			height: 40px;
		
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
	 
	   <!-- sweetalert : alert창을 꾸밀 수 있게 해주는 라이브러리 https://sweetalert.js.org/ -->
  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	 
  <!-- Bootstrap core CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	 
	 
	</script>
	 
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



	<div class="container my-5">

		<div id="content-area">
			

            <!-- Title -->
      
			<p>
			<span class="float-left"> 	<h3>홍보게시판</h3>	</span>
			</p>

			<!-- Writer -->
			<p class="lead">
			<span class="float-left"> 	작성자 : <a class="idSelect"><%=board.getMemberName() %></a>
                &nbsp;| &nbsp;
           		<%=board.getBoardCreateDate() %> </span>
				 <span class="float-right">조회수 <pr><%=board.getReadCount() %></pr> </span>
			</p>

			<hr>
			<br>
			<p>
				<span class="float-left">  좋아요 <pr><%=board.getBoardLike() %></pr> </span>
				
  	<%if(loginMember != null  && loginMember.getMemberGrade().equals("S")  && loginMember.getMemberId().equals(memberId)) {%>				 
  		 <span class="float-right" id="deleteBtn">
				 삭제</span>
				 <span class="float-right">|&nbsp;&nbsp;</span>
				 
         <span class="float-right">
         <a href="promoUpdateForm.do?type=<%=type%>&cp=<%=cp%>&no=<%=board.getBoardNo()%>" id="updateBtn">수정 &nbsp;</a> 
         </span>
         
  	<%} else if(loginMember != null  && loginMember.getMemberGrade().equals("A") ) {%>				 
  		 <span class="float-right" id="deleteBtn">
				 삭제</span>
				 
				<%  if (loginMember.getMemberId().equals(memberId) ) { %>
				 <span class="float-right">|&nbsp;&nbsp;</span>
				 
         <span class="float-right">
         <a href="promoUpdateForm.do?type=<%=type%>&cp=<%=cp%>&no=<%=board.getBoardNo()%>" id="updateBtn">수정 &nbsp;</a> 
         </span>
         
         	<%} } %>
			</p>


			<div class="promo_area">
				<div id="promo_thum">
				
  			<% if(fList != null){ %>
                    <div class="boardImgArea">
                     <% 
	                   		String src = null;
                   			for(int i=0; i<1 ; i++) {
                   			 for(Attachment at : fList){
	                    	  if(at.getFileLevel() == i){
	                    		 src = request.getContextPath()+"/resources/uploadImages/"+at.getFileChangeName();
	                    		 
                 	   %> 	  
	                    <img src="<%=src%>" width="400px">
	                       
	                    <%  } } }  %>
	                    </div> 
                <% } %>				
				</div>				
				
				<div id="promo_detail">
					<table width=400>
						<tr>
							<td colspan="2" height=50><h3><%=board.getBoardTitle() %></h3> </td>
						</tr>
						
						<tr>
							<td><b>연령</b> </td>
							<td><%=board.getSellAge() %></td>
						</tr>
						<tr>
							<td><b>가격</b> </td>
							<td><%=board.getPrice()%>원</td>
						</tr>
						<tr>
							<td><b>안내사항</b></td>
							<td rowspan="2"> <%=board.getSellIntro()%>
							<br> 
						
							</td>
						</tr>
						<tr>
							<td><b> <!--빈영역--></b></td>

						</tr>
					</table>

				</div>
			</div>



			<!-- Content -->
			
			
						<div id="board-imgArea">
               <% if(fList != null){ %>
                    <div class="boardImgArea">
                     <% 
	                   		String src = null;
                   			for(int i=1; i<4 ; i++) {
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
			
			<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">태그</label> 
					<input type="text" class="productTagArea" id="hashtag1" name="hashtag1" size="12" placeholder="#"> &nbsp;
					<input type="text" class="productTagArea" id="hashtag2" name="hashtag2" size="12" placeholder="#"> &nbsp;
					<input type="text" class="productTagArea" id="hashtag3" name="hashtag3" size="12" placeholder="#"> &nbsp;
					<input type="text" class="productTagArea" id="hashtag4" name="hashtag4" size="12" placeholder="#"> &nbsp;
					<input type="text" class="productTagArea" id="hashtag5" name="hashtag5" size="12" placeholder="#"> &nbsp;
					
			</div>
			
			<hr>
				
			
			<div>
				
               
            </div>
            <div>
                <a class="btn btn-primary float-right" href="<%=request.getContextPath()%>/community/promoList.do?type=4&cp=<%=cp%>">목록으로</a> 
		 	<%if(loginMember != null && loginMember.getMemberGrade().equals("G")){ %>
               <span class="float-left"><a href="#" class="btn btn-primary"  id="reportBtn" data-toggle="modal" data-target="#en-modal">신고하기</a> &nbsp;&nbsp;</span>
             	<span class="float-left">   <a href="#" class="btn btn-primary" id="likeBtn">좋아요</a> &nbsp;&nbsp;</span>
             <%} %>
			<br>
			</div>
			

	<%@ include file="../community/community_reply.jsp" %>

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
                    
                         <input type="radio" name="report" value="1" class="input-wid" > 성적인 콘텐츠<br> <br>
                         <input type="radio" name="report" value="2" class="input-wid" > 폭력적 콘텐츠 <br><br>
                         <input type="radio" name="report" value="3" class="input-wid" > 광고 <br><br>
                         <input type="radio" name="report" value="4" class="input-wid" > 유해한 콘텐츠 <br><br>
                         <input type="radio" name="report" value="5" class="input-wid" > 아동 학대 <br><br>
                         <input type="radio" name="report" value="6" class="input-wid" > 테러 조장 <br><br>
                         <input type="radio" name="report" value="7" class="input-wid" > 스팸 콘텐츠 <br><br>
                         <input type="radio" name="report" value="8" class="input-wid" > 권리 침해 <br><br>
                        
                         <div class="modal-footer" style="clear: both;">
                             <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                             <button type="submit" class="btn btn-primary" id="inforBtn">요청</button>
                         </div>
                       
                     </form>
                 </div>
                 </div>
             </div>
         </div> 
	
		
	<script>
		$("#deleteBtn").on("click",function(){
			if(confirm("정말 삭제 하시겠습니까?")) location.href = "promoDelete.do?type=<%=type%>&cp=<%=cp%>&no=<%=board.getBoardNo()%>";
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
	    				swal("해당 게시글에 좋아요를 눌렀습니다.");
	    				$("#likeBtn").prop("disabled", true);
	    				
	    			}  else {
	    				swal("좋아요 클릭 과정 중 오류 발생");
	    			}
	    		}, error : function() {
	    			console.log("ajax 통신 실패");
	    		}
	    	});
	    });
		
		 // 태그 split으로 화면에 출력
	      var jbString = "<%=board.getHashtag()%>";
			  var jbSplit = jbString.split(',');
			
			  var hashtag = [];
			
			for (var i=1; i<6 ; i++ ){
				hashtag[i] = document.getElementById("hashtag"+i);
			  hashtag[i].value+="#"+jbSplit[i-1] ;
			
			}
			
		
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


</body>
</html>
