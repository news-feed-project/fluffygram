package com.fluffygram.newsfeed.domain.userImage.repository;

import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.userImage.entity.UserImage;
import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.tool.GetImage;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    default Resource getImage(String imageUrl){
        return GetImage.getImage(imageUrl);
    }

    Optional<UserImage> findUserImageByUser(User user);

    default UserImage getUserImageByUser(User user){
        return findUserImageByUser(user).stream().findAny().orElseThrow(()-> new BusinessException(ExceptionType.USER_NOT_FOUND));
    }
}
