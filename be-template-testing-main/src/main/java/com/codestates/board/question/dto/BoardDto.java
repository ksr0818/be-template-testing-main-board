package com.codestates.board.question.dto;

import com.codestates.board.question.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


public class BoardDto {
    @Getter
    public static class Post {

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String title;

        @NotBlank
        private String content;

    }

    @Getter
    @Setter
    public static class Patch {

        private long boardId;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        private Board.BoardStatus boardStatus;

        private Board.BoardScope boardScope;

    }

    @AllArgsConstructor
    @Getter
    public static class Response {

        private long boardId;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        private Board.BoardStatus boardStatus;

        private Board.BoardScope boardScope;

    }

}
