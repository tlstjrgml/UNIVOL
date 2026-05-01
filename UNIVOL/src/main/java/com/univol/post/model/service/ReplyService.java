package com.univol.post.model.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.univol.post.model.Mapper.ReplyMapper;
import com.univol.post.model.vo.Reply;

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

}
