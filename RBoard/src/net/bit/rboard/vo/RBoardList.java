package net.bit.rboard.vo;

import java.util.*;

import net.bit.rboard.service.SelectCountService;

public class RBoardList {
	private int totalCount;
	private int totalPage;
	private int currentPage;
	private int pageSize;
	private int startNo;
	private int endNo;
	private int currentBlock;
	private int startPage;
	private int endPage;
	// getter & setter
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public int getStartNo() {
		return startNo;
	}
	public int getEndNo() {
		return endNo;
	}
	public int getCurrentBlock() {
		return currentBlock;
	}
	public int getStartPage() {
		return startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	private List<RBoardVO> list = new ArrayList<RBoardVO>();
	public List<RBoardVO> getList() {
		return list;
	}
	public void setList(List<RBoardVO> list, int pageNo) {
		this.list = list;
		// 페이징 처리하는 모든 변수의 계산을 처리한다.
		currentPage = pageNo;
		totalCount = SelectCountService.getInstance().selectCount();
		pageSize = 10;
		totalPage = (totalCount-1) / pageSize + 1;
		if(currentPage>totalPage) currentPage = totalPage;
		startNo = (currentPage-1) * pageSize + 1;
		endNo = startNo + pageSize - 1;
		if(endNo > totalCount) endNo = totalCount;
		currentBlock = (currentPage-1) / pageSize + 1;		// 현재블록
		startPage = (currentBlock-1) * pageSize+1;			// 페이지 시작번호
		endPage = startPage + pageSize -1;
		if(endPage > totalPage) endPage = totalPage;		// 경계검사
	}
}
