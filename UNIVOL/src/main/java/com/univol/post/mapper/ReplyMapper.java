package com.univol.post.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.univol.post.vo.Reply;

@Mapper
public interface ReplyMapper {

	int insertReply(Reply reply);

	ArrayList<Reply> selectReplyList(int pNumber);

	Reply selectOne(int cNumber);

	int updateReply(Reply reply);

	int deleteReply(int cNumber);

	int getReplyCount();
	
	ArrayList<Reply> selectAllReply();
	
	

}
