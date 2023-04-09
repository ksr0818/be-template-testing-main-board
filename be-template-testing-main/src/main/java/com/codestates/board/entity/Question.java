package com.codestates.board.entity;

import com.codestates.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Question extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS")
    private BoardStatus boardStatus = BoardStatus.QUESTION_REGISTRATION;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "SCOPE")
    private BoardScope boardScope = BoardScope.PUBLIC;


    public enum BoardStatus {
        QUESTION_REGISTRATION("질문 등록 상태"),
        QUESTION_ANSWERED("답변 완료 상태"),
        QUESTION_DELETE("질문 삭제 상태");

        @Getter
        private String status;

        BoardStatus(String status) {
            this.status = status;
        }
    }



    public enum BoardScope {
        PUBLIC("공개글"),
        PRIVATE("비밀글");

        @Getter
        private String scope;

        BoardScope(String scope) {
            this.scope = scope;
        }
    }

}

