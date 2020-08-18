package com.kh.baby.mypage.model.vo;

public class Page {
	private int currentPage; 			// 현재 페이지 번호
	private int listCount; 				// 전체 게시글의 수
	private int limit = 10; 			// 한 페이지에 보여질 게시글의 수
	private int pagingBarSize = 5;	// 화면에 표시될 페이징바의 페이지 개수
	
	private int maxPage;				// 전체 페이지 중 제일 마지막 페이지
	private int startPage;				// 페이징바 시작 페이지 번호
	private int endPage;				// 페이징바 끝 페이지 번호
	
	private int memberNo;				// 게시글 타입

	public Page(int currentPage, int listCount, int memberNo) {
		this.currentPage = currentPage;
		this.listCount = listCount;
		this.memberNo = memberNo;
		
		makePageInfo();
	}

	public Page(int currentPage, int listCount, int limit, int pagingBarSize, int memberNo) {
		super();
		this.currentPage = currentPage;
		this.listCount = listCount;
		this.limit = limit;
		this.pagingBarSize = pagingBarSize;
		this.memberNo = memberNo;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		makePageInfo();
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
		makePageInfo();
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
		makePageInfo();
	}

	public int getPagingBarSize() {
		return pagingBarSize;
	}

	public void setPagingBarSize(int pagingBarSize) {
		this.pagingBarSize = pagingBarSize;
		makePageInfo();
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	@Override
	public String toString() {
		return "PageInfo [currentPage=" + currentPage + ", listCount=" + listCount + ", limit=" + limit
				+ ", pagingBarSize=" + pagingBarSize + ", maxPage=" + maxPage + ", startPage=" + startPage
				+ ", endPage=" + endPage + ", memberNo=" + memberNo + "]";
	}
	
	// 페이징 처리에 필요한 값을 계산하는 메소드
	private void makePageInfo() {
		// maxPage : 가장 마지막 == 총 페이지 수
		// limit가 10일 때 게시글이 100개일 경우 필요한 페이지의 수 : 10p / 101개 : 11p
		// 전체 게시글 수 / 한 페이지에 보여질 게시글 수 -> 올림처리
		maxPage = (int)Math.ceil((double)listCount / limit);
		
		// startPage : 페이징바 시작 페이지 번호
		// 페이징바의 크기가 10인 경우 시작 페이지 번호는 1, 11, 21, 31, ...
		startPage = (currentPage - 1)/pagingBarSize * pagingBarSize + 1;
		
		// endPage : 페이징바 끝 페이지 번호
		// 페이징바의 크기가 10인 경우 끝 페이지 번호는 10, 20, 30, 40, ...
		
		endPage = startPage + pagingBarSize - 1;
		// endPage가 maxPage보다 클 경우
		if(endPage > maxPage) {
			endPage = maxPage;
		} /*else {
			endPage = startPage + pagingBarSize - 1;
		}*/
	}
	
}
