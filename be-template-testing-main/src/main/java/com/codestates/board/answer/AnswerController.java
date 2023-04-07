package com.codestates.board.answer;

import com.codestates.board.question.dto.BoardDto;
import com.codestates.board.question.entity.Board;
import com.codestates.board.question.mapper.BoardMapper;
import com.codestates.board.question.service.BoardService;
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
    private final BoardService boardService;
    private final BoardMapper mapper;


    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody BoardDto.Post requestBody) {
        Board board = mapper.boardPostToBoard(requestBody);

        Board createdBoard = boardService.createBoard(board);
        URI location = UriCreator.createUri(BOARD_DEFAULT_URL, createdBoard.getBoardId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{board-id}")
    public ResponseEntity patchBoard(
            @PathVariable("board-id") @Positive long boardId,
            @Valid @RequestBody BoardDto.Patch requestBody) {
        requestBody.setBoardId(boardId);

        Board board =
                boardService.updateBoard(mapper.boardPatchToBoard(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.boardToBoardResponse(board)),
                HttpStatus.OK);
    }
    //
    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(
            @PathVariable("board-id") @Positive long boardId) {
        Board board = boardService.findBoard(boardId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.boardToBoardResponse(board))
                , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getBoards(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Board> pageBoards = boardService.findBoards(page - 1, size);
        List<Board> boards = pageBoards.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.boardsToBoardResponses(boards),
                        pageBoards),
                HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard(
            @PathVariable("board-id") @Positive long boardId){
        boardService.deleteBoard(boardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
