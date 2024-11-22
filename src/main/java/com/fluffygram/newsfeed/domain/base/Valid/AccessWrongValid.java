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

    public void validateFriendRequestDto(Long loginUserId, FriendRequestDto requestDto) {

        if (loginUserId == null) {
            throw new BusinessException(ExceptionType.USER_NOT_FOUND);
        }
        if (!loginUserId.equals(requestDto.getSendUserId()) || loginUserId.equals(requestDto.getReceivedUserId())) {
            throw new BusinessException(ExceptionType.USER_NOT_MATCH);
        }
    }

    public void validateFriendRequestByUserId(Long loginUserId, long userId) {

        if (loginUserId == null) {
            throw new BusinessException(ExceptionType.USER_NOT_FOUND);
        }
        if (!loginUserId.equals(userId)) {
            throw new BusinessException(ExceptionType.USER_NOT_MATCH);
        }
    }

}
