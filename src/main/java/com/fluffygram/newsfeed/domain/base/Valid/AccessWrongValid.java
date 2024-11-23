package com.fluffygram.newsfeed.domain.base.Valid;

import com.fluffygram.newsfeed.domain.friend.dto.FriendRequestDto;
import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccessWrongValid {

    public boolean AccessMisMatchString(String str1, String str2) {
        return Objects.equals(str1, str2);

    }



}
