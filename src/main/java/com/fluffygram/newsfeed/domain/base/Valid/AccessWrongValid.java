package com.fluffygram.newsfeed.domain.base.Valid;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccessWrongValid {

    public boolean AccessMisMatchString(String str1, String str2) {
        return Objects.equals(str1, str2);
    }
}
