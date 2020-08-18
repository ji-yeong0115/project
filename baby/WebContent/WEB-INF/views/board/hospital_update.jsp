<%@page import="java.util.Arrays"%>
<%@page import="com.kh.baby.board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	Board board = (Board)request.getAttribute("board");

	String cp = request.getParameter("cp");
	String type = request.getParameter("type");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지역별 병원 정보 게시글 수정화면</title>
	<style>
	
			@font-face { font-family: 'InfinitySans-BoldA1'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-BoldA1.woff') 
	format('woff'); font-weight: normal; font-style: normal; }
	
		          /* content_main 스타일 */
          #content_main {
            width: 80%;
            padding-right: 15px;
            margin-top: 48px;
          }

          /* padding 제거 css */
          .padding-none{
            padding-left: 0;
            padding-right: 0;
          }

          /* margin 제거 css */
          .margin-none{
            margin-left: 0;
            margin-right: 0;
          }

          /* 목록, 다음글, 이전글, 정보 수정/삭제 요청, 신고 버튼 오른쪽 정렬 */
          .btn-right{
            float: right;
          }

          /* margin-top 20px */
          .top-margin{
            margin-top: 20px;
          }

          .top-btn-area{
            margin-left: 10px;
          }

          #title{
            font-family: 'InfinitySans-BoldA1';
          }
          
          .text-loca, .like-area, , .content-area{
            text-align: left;
          }

          .solid{
            border: 1px solid black;
          }

          .board-area{
            margin-bottom: 20px;
          }

          #heart-img{
            width: 45px;
            height: 50px;
          }

          /* ---------------------------------------------------------------------- */
          /* 모달 */
          #label-title{
              color: rgb(56, 190, 243);
          }

          .modal-left{
            float: left;
            margin-bottom: 0;
          }


          /* ---------------------------------------------------------------------- */
          /* 댓글 작성 textarea */
          #replyContent{
            resize: none;
          }

          /* 댓글 목록 */
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

          .rContent, .rBtnArea{
            display: inline-block;
            box-sizing: border-box;
          }

          .rContent {
            height: 100%;
            width : 84.5%;
          }

          .rBtnArea {
            height: 100%;
            width : 15%;
            text-align: right;
          }

          .rTextCenter{
            text-align: left;
          }
          
          .writeDay{
          	text-align: right;
          	margin-bottom: 0px;
          }
          
          .listBtn{
          	float: left;
          }
          
	</style>

</head>
<body>

	<%
		String [] ynRadio = new String[4];
		Arrays.fill(ynRadio, "");
		
		if(board.getHosNightYN().equals("Y")){
			ynRadio[0] = "checked";
		}else{
			ynRadio[1] = "checked";
		}
		
		if(board.getHosWeekenYN().equals("Y")){
			ynRadio[2] = "checked";
		}else{
			ynRadio[3] = "checked";
		}
	
	
	%>

	<div class="container-fluid">
		<%@ include file="../common/header.jsp" %>
		
		<div class="row" id="content">
			<%@ include file="../common/aside.jsp" %>
			
				<div id="content_main"  style="padding-left: 15px">
				
				<form action="updateHos.do?" method="get">
				<input type="hidden" name="cp" value="<%=cp%>">
				<input type="hidden" name="type" value="<%=type%>">
				<input type="hidden" name="no" value="<%=board.getBoardNo() %>">
				
					<!-- 게시글 제목 -->
                  <div class="row margin-none">
                    <div class="col-md-12 padding-none">
                    	<h3 id="title">[게시글 수정 화면]</h3> <br>
						<table>
							<tr>
								<th class="h3" style="line-height:10px;">제목 : &nbsp;</th>
								<td style="width: 94%;"><input type="text" name="titleInfo" class="form-control" value="<%=board.getBoardTitle() %>"></td>
							</tr>
						</table>

                     </div>
                  </div>
          

                  <hr>
          
                  <!-- 게시글이 작성된 공간 -->
                  <div class="row board-area" style="width: 100%; margin: 0px;">
	                  <div class="col-md-4 top-margin board-area margin-none" style="margin: auto;">
	                  	<table>
	                  		<tr>
	                  			<th>영업시간 : </th>
	                  			<td>
	                  				<input type="text" name="hosTime" class="form-control" value="<%=board.getHosTime() %>">
	                  			</td>
	                  		</tr>
	                  		<tr>
	                  			<th>병원주소 : </th>
	                  			<td>
	                  				<input type="text" name="hosAddress" class="form-control" value="<%=board.getHosAddress()%>">
	                  			</td>
	                  		</tr>
	                  		<tr>
	                  			<th>야간진료 여부 : </th>
	                  			<td>
	                  				<div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="nitghYN" id="nightY" value="Y" <%=ynRadio[0]%>>
									  <label class="form-check-label" for="nightY">야간진료 가능</label>
									</div>
									<div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="nitghYN" id="nightN" value="N" <%=ynRadio[1]%>>
									  <label class="form-check-label" for="nightN">야간진료 불가능</label>
									</div>
								</td>
	                  		</tr>
	                  		<tr>
	  	                  		<th>주말진료 여부 : &nbsp;</th>
	                  			<td>

								<div class="form-check form-check-inline">
								  <input class="form-check-input" type="radio" name="weekYN" id="weekendY" value="Y" <%=ynRadio[2]%>>
								  <label class="form-check-label" for="weekendY">주말진료 가능</label>
								</div>
								<div class="form-check form-check-inline">
								  <input class="form-check-input" type="radio" name="weekYN" id="weekendN" value="N" <%=ynRadio[3]%>>
								  <label class="form-check-label" for="weekendN">주말진료 불가능</label>
								</div>									
	                  			</td>
	                  		</tr>
	                  	</table>
	                    
	                  </div>
                  </div>
          
                  <!-- 좋아요 이미지와 버튼 -->
                  <div class="row margin-none" style="margin-top: 20px;">
                    <div div class="col-md-12">
                      <button class="btn btn-primary btn-right" type="submit">수정</button>
                    </div>            
                  </div>
				</form>
				

                  <hr>     

                <!-- content_main 끝 -->
                </div>
		
		</div>
	
	
		<%@ include file="../common/footer.jsp" %>
	</div>
	
	
	<!-- 정보 수정/삭제 요청 Modal -->
	<div class="modal fade" id="en-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div class="modal-dialog">
              <div class="modal-content" style="width: 500px;">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">정보 수정/삭제 요청</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                  <form action="#" id="information-en">
  
                      <div class="custom-control custom-radio">
                          <input type="radio" id="customRadio1" name="customRadio" class="custom-control-input modal-left">
                          <label class="custom-control-label modal-left" for="customRadio1">정보 수정 요청</label>
                        </div>
  
                        <div class="custom-control custom-radio">
                          <input type="radio" id="customRadio2" name="customRadio" class="custom-control-input modal-left">
                          <label class="custom-control-label modal-left" for="customRadio2">정보 삭제 요청</label> <br><br>
                        </div>
  
                      <label id="label-title" class="modal-left">요청 내용 입력</label> <br>
                      <textarea name="text-area" cols="45" rows="5" style="resize: none; margin-bottom: 20px;" class="modal-left"></textarea> <br>
                      
                      <div class="modal-footer" style="clear: both; margin-bottom: 20px;">
                          <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                          <button type="button" class="btn btn-primary" id=requestBtn>요청</button>
                      </div>
                  </form>
                </div>
              </div>
          </div>
        </div>	 	

</body>
</html>