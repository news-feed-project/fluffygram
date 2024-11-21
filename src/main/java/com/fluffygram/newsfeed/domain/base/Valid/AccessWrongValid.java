package com.fluffygram.newsfeed.domain.base.Valid;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccessWrongValid {

    public void accessMisMatchId(Long numberOne, Long numberTwo) {
        if (!Objects.equals(numberOne, numberTwo)) {
            throw new RuntimeException("값 불일치");
        }
    }
}
