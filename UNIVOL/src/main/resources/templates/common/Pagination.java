package templates.common;

import com.univol.member.model.vo.PageInfo;

public class Pagination {
	public static PageInfo getPageInfo(int currentPage, int listCount, int ReviewLimit) {
		int pageLimit = 5;
		int maxPage = (int)Math.ceil((double)listCount / ReviewLimit);
		int startPage = (currentPage - 1)/pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit + 1;
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		return new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, ReviewLimit);
	}
}
