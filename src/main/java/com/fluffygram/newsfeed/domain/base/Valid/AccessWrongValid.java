package com.fluffygram.newsfeed.domain.base.Valid;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccessWrongValid {

    public void AccessMisMatchId(Long numberOne, Long numberTwo) {
        if(!Objects.equals(numberOne, numberTwo)){
        }
    }
}
