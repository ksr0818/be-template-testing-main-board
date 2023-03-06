package com.codestates.board.dto;

import com.codestates.board.entity.Board;
import com.codestates.member.entity.Member;
import com.codestates.order.dto.OrderCoffeeDto;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;


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

        private Board.BoardStatus boardStatus;

        private Board.BoardScope boardScope;

    }

    @Getter
    public static class Patch {

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
