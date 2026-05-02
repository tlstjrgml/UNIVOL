package com.univol.post;

import com.univol.post.model.vo.PageInfo;

public class Pagenation {

	public static PageInfo getPageInfo(int currentPage, int listCount, int boardLimit) {
		int pageLimit = 10;
		
		int maxPage = (int)Math.ceil((double)listCount / boardLimit);
		if(maxPage == 0) { maxPage = 1;}
		
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		if(endPage > maxPage) {endPage = maxPage;}
		
		PageInfo p = new PageInfo();
		p.setCurrentPage(currentPage);
		p.setListCount(listCount);
		p.setPageLimit(pageLimit);
		p.setMaxPage(maxPage);
		p.setStartPage(startPage);
		p.setEndPage(endPage);
		p.setBoardLimit(boardLimit);
		
		
		
		
		return p;
		
	}
}