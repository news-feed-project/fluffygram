package com.fluffygram.newsfeed.domain.Image.repository;

import com.fluffygram.newsfeed.domain.Image.entity.UserImage;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.global.config.Const;
import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.tool.GetImage;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserImageRepositoryImpl extends JpaRepository<UserImage, Long>{

    default Resource getImage(String imageUrl){
        return GetImage.getImage(Const.USER_IMAGE_STORAGE_PATH, imageUrl);
    }

    Optional<UserImage> findBoardImageByDBFileName(String DBFileName);


    default UserImage getImageOrElseThrow(String DBFileName){
        return findBoardImageByDBFileName(DBFileName).stream().findAny().orElseThrow(()-> new BusinessException(ExceptionType.FILE_NOT_FOUND));
    }

    Optional<UserImage> findUserImageByUser(User user);


    default UserImage getImageByUserOrElseThrow(User user){
        return findUserImageByUser(user).orElseThrow(()-> new BusinessException(ExceptionType.FILE_NOT_FOUND));
    }

}
