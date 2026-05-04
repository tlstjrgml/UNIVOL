package com.univol.post.model.Mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.univol.post.model.vo.Reply;

@Mapper
public interface ReplyMapper {

	int insertReply(Reply reply);

	ArrayList<Reply> selectReplyList(int pNumber);

	Reply selectOne(int cNumber);

	int updateReply(Reply reply);

	int deleteReply(int cNumber);

}
