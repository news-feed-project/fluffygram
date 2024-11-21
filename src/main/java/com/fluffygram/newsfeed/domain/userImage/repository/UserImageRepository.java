package com.fluffygram.newsfeed.domain.userImage.repository;

import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.userImage.entity.UserImage;
import com.fluffygram.newsfeed.global.tool.GetUserImage;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    default Resource getImage(String imageUrl){
        return GetUserImage.getImage(imageUrl);
    }

    Optional<UserImage> findUserImageByUser(User user);

    default UserImage getUserImageByUser(User user){
        return findUserImageByUser(user).stream().findAny().orElseThrow(()-> new RuntimeException("[id = " + user.getId() + "] 에 해당하는 유저가 존재하지 않습니다."));
    }
}
