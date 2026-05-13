package com.univol.post.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.univol.post.mapper.ReplyMapper;
import com.univol.post.vo.Reply;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {

	private final ReplyMapper replyMapper;

	public int insertReply(Reply reply) {
		return replyMapper.insertReply(reply);
	}
	
	public ArrayList<Reply> selectReplyList(int pNumber) {
		return replyMapper.selectReplyList(pNumber);
	}

	public Reply selectOne(int cNumber) {
		return replyMapper.selectOne(cNumber);
	}

	public int updateReply(Reply reply) {
		return replyMapper.updateReply(reply);
	}

	public int deleteReply(int cNumber) {
		return replyMapper.deleteReply(cNumber);
	}
	
	public ArrayList<Reply> selectAllReply(){
		return replyMapper.selectAllReply();
	}
	
	public int adminDeleteReply(int rNumber) {
		return replyMapper.adminDeleteReply(rNumber);
	}

	public int adminRollbackReply(int rNumber) {
		return replyMapper.adminRollbackReply(rNumber);
	}

	public ArrayList<Reply> selectAllReply(int replyStartRow, int replyEndRow) {
		return replyMapper.selectAllReply(replyStartRow, replyEndRow);
	}

	public int getReplyCount() {
		return replyMapper.getReplyCount();
	}
}
