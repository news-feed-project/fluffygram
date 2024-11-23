package com.fluffygram.newsfeed.domain.board.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@Setter
public class PaginationCriteria {

    String dateType = "create";

    String likeManySort = "noLike";

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAt;

}
