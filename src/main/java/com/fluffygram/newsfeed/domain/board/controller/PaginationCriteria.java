package com.fluffygram.newsfeed.domain.board.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@Setter
public class PaginationCriteria {

    String dateType = "create"; //생성일 순 or 수정일 순

    String likeManySort = "like"; //좋아요 순

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt; //시작일

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAt; //마지막 일

}
