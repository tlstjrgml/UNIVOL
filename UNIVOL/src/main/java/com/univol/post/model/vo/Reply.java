package com.univol.post.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Reply {
	private int cNumber;      // 댓글 번호
	private String comments;  // 댓글 내용
	private String cDate;     // 댓글 작성일
	private int pNumber;      // 게시글 번호 (외래키)
	private String userId;    // 사용자 ID
	private String userName;  // 사용자명 (조인으로 가져올 때 필요)
}
