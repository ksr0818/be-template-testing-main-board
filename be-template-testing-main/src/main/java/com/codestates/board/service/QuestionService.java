package com.codestates.board.service;

import com.codestates.board.entity.Question;
import com.codestates.board.repository.QuestionRepository;
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
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createBoard(Question question) {
        verifyExistsEmail(question.getEmail());
        return questionRepository.save(question);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Question updateBoard(Question question) {
        Question findQuestion = findVerifiedBoard(question.getBoardId());

        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(question.getContent())
                .ifPresent(content -> findQuestion.setContent(content));
        Optional.ofNullable(question.getBoardScope())
                .ifPresent(boardScope -> findQuestion.setBoardScope(boardScope));

        return questionRepository.save(findQuestion);
    }
//
    @Transactional(readOnly = true)
    public Question findBoard(long boardId) {
        return findVerifiedBoard(boardId);
    }
//

    public Page<Question> findBoards(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size,
                Sort.by("boardId").descending()));
    }

    public void deleteBoard(long boardId) {
        Question findQuestion = findVerifiedBoard(boardId);
        findQuestion.setBoardStatus(Question.BoardStatus.QUESTION_DELETE);
        questionRepository.save(findQuestion);

    }
//
    @Transactional(readOnly = true)
    public Question findVerifiedBoard(long boardId) {
        Optional<Question> optionalBoard =
                questionRepository.findById(boardId);
        Question findQuestion =
                optionalBoard.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findQuestion;
    }

    private void verifyExistsEmail(String email) {
        Optional<Question> board = questionRepository.findByEmail(email);
        if (board.isPresent())
            throw new BusinessLogicException(ExceptionCode.BOARD_EXISTS);
    }
}
