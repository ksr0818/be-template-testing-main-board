package com.codestates.board.dto;

import com.codestates.board.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


public class QuestionDto {
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

        private Question.BoardStatus boardStatus;

        private Question.BoardScope boardScope;

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

        private Question.BoardStatus boardStatus;

        private Question.BoardScope boardScope;

    }

}
