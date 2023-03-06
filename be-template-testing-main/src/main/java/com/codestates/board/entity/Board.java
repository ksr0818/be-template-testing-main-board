package com.codestates.board.entity;

import com.codestates.audit.Auditable;
import com.codestates.member.entity.Member;
import com.codestates.order.entity.Order;
import com.codestates.stamp.Stamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Board extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Board.BoardStatus boardStatus = BoardStatus.QUESTION_REGISTRATION;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Board.BoardScope boardScope = BoardScope.PUBLIC;

    //Todo:: 바꿀 예정!!
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    // 수정된 부분
    @OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Stamp stamp;


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

