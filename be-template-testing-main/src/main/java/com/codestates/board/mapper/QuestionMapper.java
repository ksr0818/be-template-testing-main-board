package com.codestates.board.mapper;

import com.codestates.board.dto.QuestionDto;
import com.codestates.board.entity.Question;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

        Question boardPostToBoard(QuestionDto.Post requestBody);
        Question boardPatchToBoard(QuestionDto.Patch requestBody);
        QuestionDto.Response boardToBoardResponse(Question Question);
        List<QuestionDto.Response> boardsToBoardResponses(List<Question> questions);

}
