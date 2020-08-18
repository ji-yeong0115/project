<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.kh.baby.board.model.vo.RequestBoard"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.board.model.vo.Board"%>
<% 
	Board board = (Board)request.getAttribute("board");
	RequestBoard rBoard = (RequestBoard)request.getAttribute("rBoard");	
	
	String cp = request.getParameter("cp");
	String type = request.getParameter("type");

	int boardNo = board.getBoardNo();
	int boardType =board.getBoardType();
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지역별 병원 정보 상세 조회</title>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=25251707361e354ba42001a01201471c&libraries=services"></script>

	<style>
		
			@font-face { font-family: 'InfinitySans-BoldA1'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-BoldA1.woff') 
			format('woff'); font-weight: normal; font-style: normal; }
			
			#title{
				font-family: 'InfinitySans-BoldA1';
			}
			
	
		  /* content_main 스타일 */
          #content_main {
            width: 80%;
            padding-right: 15px;
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
            margin-left: 4px;
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


 
          
          .listBtn{
          	float: left;
          }
          
	</style>

</head>
<body>

	<div class="container-fluid">
		<%@ include file="../common/header.jsp" %>
		
		<div class="row" id="content">
			<%@ include file="../common/aside.jsp" %>
			
				<div id="content_main"  style="padding-left: 15px; margin-top: 48px;">
				
				<!-- 게시글 제목 -->
                  <div class="row width-100 margin-none top-margin">
                    <div class="col-md-12 padding-none ">
                      <p class="h2 width-100" style="text-align: left;" id="title"><%=board.getBoardTitle() %></p>
                      </div>
                  </div>
          
                    <!-- 신고 및 정보 수정/삭제 요청 버튼 -->
                  <div class="row width-100 margin-none">
                    <div class="col-md-12 margin-none padding-none width-100">
                    	<% SimpleDateFormat sdf = new SimpleDateFormat("yyyy'년' MM'월' dd'일' HH':'mm':'ss"); %>
                    
                      <P style="text-align: left; margin-bottom: 0px;">작성일 : <%=sdf.format(board.getBoardCreateDate()) %></P>
                      <P style="text-align: left;">수정일 : <%=sdf.format(board.getBoardModifyDate()) %></P> <br>
                      
                      <%if(loginMember != null && loginMember.getMemberGrade().equals("G")) {%>
	                      <button class="btn btn-primary btn-right top-btn-area" type="button" data-toggle="modal" data-target="#en-modal">정보 수정/삭제 요청</button>                                         
                      <%} else if(loginMember != null && loginMember.getMemberGrade().equals("A")) {%>
	                      <button class="btn btn-primary btn-right top-btn-area" type="submit" id="deleteBtn">삭제</button>
	                      <a href="updateForm.do?type=<%=type%>&no=<%=boardNo%>&cp=<%=cp %>" class="btn btn-primary float-right ml-1 mr-1">수정</a>                      
                      <%} %>
                      
                    </div>
                  </div>

                  <hr>
          
                  <!-- 게시글이 작성된 공간 -->
                  <div class="row board-area" style="width: 100%; margin: 0px;" >
	                  <div class="col-md-4 top-margin board-area margin-none" style="margin: auto; text-align: left; line-height: 40px;">

						<span style="text-align: left;">영업시간 : <%=board.getHosTime() %></span> <br>
	                    	병원주소 : <span style="text-align: left;" id="hosAd"><%=board.getHosAddress() %></span> <br>
	                    <span style="text-align: left;">야간진료 여부 : 
		                    <%if(board.getHosNightYN().equals("Y")){ %>
		                    	<span style="color: green;">야간진료 가능</span> <br>
		                    <%} else {%>
		                    	<span style="color: red;">야간진료 불가능</span> <br>
		                    <%} %>
	                    </span>
	                    <span style="text-align: left;">주말진료 여부 :
	                    	<%if(board.getHosWeekenYN().equals("Y")){ %>
		                    	<span style="color: green;">주말진료 가능</span> <br>
		                    <%} else {%>
		                    	<span style="color: red;">주말진료 불가능</span> <br>
		                    <%} %>
	                    </span>
	                    
	                  </div>
	                  <div class="col-md-8 top-margin board-area width-100 margin-none">
						<div style="width: 900px; height: 500px;" id="map">
							
						</div>
	                    
	                  </div>
                  </div>
          
                  <!-- 좋아요 이미지와 버튼 -->
                  <div class="row top-margin board-area">
                    <div class="col-md-12">
                    <%if(loginMember != null && loginMember.getMemberGrade().equals("G")){ %>
                      <button class="btn btn-primary btn-right" id="likeBtn" type="button">좋아요</button>                    
                    <%} %>
                      <a href="hospitalForm.do?type=<%=type%>&cp=<%=cp%>" class="btn btn-primary listBtn">목록</a>
                    </div>            
                  </div>
                  <hr>
          
				<%@include file="../board/reply.jsp" %>

                <!-- content_main 끝 -->
                </div>
		
		</div>
	
	
		<%@ include file="../common/footer.jsp" %>
	</div>
	
		<!-- 정보 수정/삭제 요청 Modal -->
	<div class="modal fade" id="en-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div class="modal-dialog">
              <div class="modal-content" style="width: 400px;">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">정보 수정/삭제 요청</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                  <form action="UpdateInfoForm.do" id="information-en" method="GET">
                  	<input type="hidden" name="type" value="<%=type%>">
  	               	<input type="hidden" name="no" value="<%=boardNo%>">
                      <div class="custom-control custom-radio">
                          <input type="radio" id="customRadio1" name="updateRadio" class="custom-control-input modal-left" value="U">
                          <label class="custom-control-label modal-left" for="customRadio1">정보 수정 요청</label>
                        </div>
  
                        <div class="custom-control custom-radio">
                          <input type="radio" id="customRadio2" name="updateRadio" class="custom-control-input modal-left"  value="D">
                          <label class="custom-control-label modal-left" for="customRadio2">정보 삭제 요청</label> <br><br>
                        </div>
  
                      <label id="label-title" class="modal-left">요청 내용 입력</label> <br>
                      <textarea name="modalInfo" cols="40" name="updateInfo" rows="5" style="resize: none; margin-bottom: 20px;" class="modal-left" maxlength="150"></textarea> 
                      <p><span id="counter">0</span>/150<p>
                      <br>
                      
                      <div class="modal-footer" style="clear: both; margin-bottom: 20px;">
                          <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                          <button type="submit" class="btn btn-primary" id=requestBtn>요청</button>
                      </div>
                  </form>
                </div>
              </div>
          </div>
        </div>
	
	<script>
		var hosAddress = $("#hosAd").text();
		var hosName = $("#title").text();
		
		$("#deleteBtn").on("click", function(){
			if(confirm("정말 삭제 하시겠습니까?")) location.href = "deleteHos.do?no=<%=boardNo %>&type=<%=type%>&cp=<%=cp%>";
		});
		
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
    var no = "<%=boardNo%>";
    var type = "<%=type%>";
    
    $("#likeBtn").on("click", function() {
    	console.log("click");
    	$.ajax({
    		url : "updateLike.do",
    		data : {"no" : no, "type" : type},
    		success : function(result) {
    			if(result == 1) {
    				swal("해당 게시글에 좋아요 클릭 !");
    			} else if(result == 2) {
    				swal("해당 게시글에 좋아요 해제 !");
    			} else {
    				swal("좋아요 클릭 과정 중 오류 발생");
    			}
    		}, error : function() {
    			console.log("ajax 통신 실패");
    		}
    	});
    });
    
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

// 주소로 좌표를 검색합니다
geocoder.addressSearch(hosAddress, function(result, status) {

    // 정상적으로 검색이 완료됐으면 
     if (status === kakao.maps.services.Status.OK) {

        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });

        // 인포윈도우로 장소에 대한 설명을 표시합니다
        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">'+hosName+'</div>'
        });
        infowindow.open(map, marker);

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
    } 
});    
    

	</script>
	 	

</body>
</html>