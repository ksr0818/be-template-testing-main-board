package com.codestates.board.question.repository;

import com.codestates.board.question.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByEmail(String email);
}
