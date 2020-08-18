package com.kh.baby.board.model.vo;

public class Page {
	
	private int currentPage;
	private int listCount;
	private int limit = 10;
	private int pagingBarSize = 10;
	
	private int maxPage;
	private int startPage;
	private int endPage;
	
	private int boardType;
	private int commonAge;
	
	public int getCommonAge() {
		return commonAge;
	}

	public void setCommonAge(int commonAge) {
		this.commonAge = commonAge;
	}

	public Page(int currentPage, int listCount, int boardType) {
		super();
		this.currentPage = currentPage;
		this.listCount = listCount;
		this.boardType = boardType;
		makePageInfo();
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
	}

	public int getMaxPage() {
		return maxPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public int getBoardType() {
		return boardType;
	}

	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}

	@Override
	public String toString() {
		return "PageInfo [currentPage=" + currentPage + ", listCount=" + listCount + ", limit=" + limit
				+ ", pagingBarSize=" + pagingBarSize + ", maxPage=" + maxPage + ", startPate=" + startPage
				+ ", endPage=" + endPage + ", boardType=" + boardType + "]";
	}
	
	private void makePageInfo() {
		
		maxPage = (int)(Math.ceil((double)listCount / limit));
		startPage = (currentPage-1) / pagingBarSize * pagingBarSize + 1;
		
		endPage = startPage + pagingBarSize - 1;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
	}
	
	

}
