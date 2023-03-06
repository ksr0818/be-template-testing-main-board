package com.codestates.board.mapper;

import com.codestates.board.dto.BoardDto;
import com.codestates.board.entity.Board;
import com.codestates.member.dto.MemberDto;
import com.codestates.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {

        Board boardPostToBoard(BoardDto.Post requestBody);
        Board boardPatchToBoard(BoardDto.Patch requestBody);
        MemberDto.response memberToMemberResponse(Member member);
        List<MemberDto.response> membersToMemberResponses(List<Member> members);

}
