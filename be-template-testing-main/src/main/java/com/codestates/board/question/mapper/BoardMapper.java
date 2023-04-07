package com.codestates.board.question.mapper;

import com.codestates.board.question.dto.BoardDto;
import com.codestates.board.question.entity.Board;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {

        Board boardPostToBoard(BoardDto.Post requestBody);
        Board boardPatchToBoard(BoardDto.Patch requestBody);
        BoardDto.Response boardToBoardResponse(Board Board);
        List<BoardDto.Response> boardsToBoardResponses(List<Board> boards);

}
