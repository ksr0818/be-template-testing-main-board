package com.codestates.board.controller;

import com.codestates.board.dto.QuestionDto;
import com.codestates.board.entity.Question;
import com.codestates.board.mapper.QuestionMapper;
import com.codestates.board.service.QuestionService;
import com.codestates.dto.MultiResponseDto;
import com.codestates.dto.SingleResponseDto;
import com.codestates.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

public class AnswerController {
    private final static String BOARD_DEFAULT_URL = "/v11/boards";
    private final QuestionService questionService;
    private final QuestionMapper mapper;


    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody QuestionDto.Post requestBody) {
        Question question = mapper.boardPostToBoard(requestBody);

        Question createdQuestion = questionService.createBoard(question);
        URI location = UriCreator.createUri(BOARD_DEFAULT_URL, createdQuestion.getBoardId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{board-id}")
    public ResponseEntity patchBoard(
            @PathVariable("board-id") @Positive long boardId,
            @Valid @RequestBody QuestionDto.Patch requestBody) {
        requestBody.setBoardId(boardId);

        Question question =
                questionService.updateBoard(mapper.boardPatchToBoard(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.boardToBoardResponse(question)),
                HttpStatus.OK);
    }
    //
    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(
            @PathVariable("board-id") @Positive long boardId) {
        Question question = questionService.findBoard(boardId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.boardToBoardResponse(question))
                , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getBoards(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Question> pageBoards = questionService.findBoards(page - 1, size);
        List<Question> questions = pageBoards.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.boardsToBoardResponses(questions),
                        pageBoards),
                HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard(
            @PathVariable("board-id") @Positive long boardId){
        questionService.deleteBoard(boardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
