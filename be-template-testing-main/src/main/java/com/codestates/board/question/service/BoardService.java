package com.codestates.board.question.service;

import com.codestates.board.question.entity.Board;
import com.codestates.board.question.repository.BoardRepository;
import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board createBoard(Board board) {
        verifyExistsEmail(board.getEmail());
        return boardRepository.save(board);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Board updateBoard(Board board) {
        Board findBoard = findVerifiedBoard(board.getBoardId());

        Optional.ofNullable(board.getTitle())
                .ifPresent(title -> findBoard.setTitle(title));
        Optional.ofNullable(board.getContent())
                .ifPresent(content -> findBoard.setContent(content));
        Optional.ofNullable(board.getBoardScope())
                .ifPresent(boardScope -> findBoard.setBoardScope(boardScope));

        return boardRepository.save(findBoard);
    }
//
    @Transactional(readOnly = true)
    public Board findBoard(long boardId) {
        return findVerifiedBoard(boardId);
    }
//

    public Page<Board> findBoards(int page, int size) {
        return boardRepository.findAll(PageRequest.of(page, size,
                Sort.by("boardId").descending()));
    }

    public void deleteBoard(long boardId) {
        Board findBoard = findVerifiedBoard(boardId);
        findBoard.setBoardStatus(Board.BoardStatus.QUESTION_DELETE);
        boardRepository.save(findBoard);

    }
//
    @Transactional(readOnly = true)
    public Board findVerifiedBoard(long boardId) {
        Optional<Board> optionalBoard =
                boardRepository.findById(boardId);
        Board findBoard =
                optionalBoard.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findBoard;
    }

    private void verifyExistsEmail(String email) {
        Optional<Board> board = boardRepository.findByEmail(email);
        if (board.isPresent())
            throw new BusinessLogicException(ExceptionCode.BOARD_EXISTS);
    }
}
