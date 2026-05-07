package com.univol.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.univol.member.model.vo.Member;
import com.univol.post.model.service.ReplyService;
import com.univol.post.model.vo.Reply;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/post/{pNumber}/reply")
    public String insertReply(
            @PathVariable(name = "pNumber") int pNumber,
            Reply reply,
            @SessionAttribute(name = "loginUser", required = false) Object loginUser) {

        // 로그인 확인
        if(loginUser == null) {
            return "redirect:/login";
        }

        Member member = (Member) loginUser;
        reply.setPNumber(pNumber);
        reply.setUserId(member.getUserId());
        replyService.insertReply(reply);
        return "redirect:/post/" + pNumber;
    }

    @PostMapping("/post/reply/{cNumber}/update")
    public String updateReply(
            @PathVariable(name = "cNumber") int cNumber,
            Reply reply,
            @SessionAttribute(name = "loginUser", required = false) Object loginUser) {

        // 로그인 확인
        if(loginUser == null) {
            return "redirect:/login";
        }

        // 기존 댓글 조회
        Reply existingReply = replyService.selectOne(cNumber);

        // 댓글 작성자 확인
        Member member = (Member) loginUser;
        if(!existingReply.getUserId().equals(member.getUserId())) {
            return "redirect:/post/" + existingReply.getPNumber();
        }

        reply.setCNumber(cNumber);
        replyService.updateReply(reply);

        return "redirect:/post/" + reply.getPNumber();
    }

    @PostMapping("/post/reply/{cNumber}/delete")
    public String deleteReply(
            @PathVariable(name = "cNumber") int cNumber,
            @RequestParam(name = "pNumber") int pNumber,
            @SessionAttribute(name = "loginUser", required = false) Object loginUser) {

        // 로그인 확인
        if(loginUser == null) {
            return "redirect:/login";
        }

        // 기존 댓글 조회
        Reply existingReply = replyService.selectOne(cNumber);

        // 댓글 작성자 확인
        Member member = (Member) loginUser;
        if(!existingReply.getUserId().equals(member.getUserId())) {
            return "redirect:/post/" + pNumber;
        }

        replyService.deleteReply(cNumber);

        return "redirect:/post/" + pNumber;
    }
}