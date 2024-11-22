package com.fluffygram.newsfeed.domain.Image.service;

import com.fluffygram.newsfeed.domain.Image.repository.UserImageRepositoryImpl;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.Image.entity.UserImage;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.tool.UploadImage;
import com.fluffygram.newsfeed.global.config.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserImageServiceImpl {

    private final UserRepository userRepository;
    private final UserImageRepositoryImpl userImageRepositoryImpl;

    @Transactional
    public UserImage saveImage(MultipartFile profileImage, Long userId) {

        String DBfileName = UploadImage.uploadUserImage(Const.USER_IMAGE_STORAGE_PATH, profileImage);

        User user = userRepository.findByIdOrElseThrow(userId);

        UserImage userImage = new UserImage(user, profileImage.getOriginalFilename(), DBfileName, Const.USER_IMAGE_STORAGE);

        return userImageRepositoryImpl.save(userImage);
    }


    public Resource getImage(String imageUrl) {

        return userImageRepositoryImpl.getImage(imageUrl);
    }

    public void deleteImage(String imageName) {
        UserImage userImage = userImageRepositoryImpl.getImageOrElseThrow(imageName);

        userImageRepositoryImpl.delete(userImage);
    }


    public UserImage updateImage(MultipartFile profileImage, Object user) {
        String DBfileName = UploadImage.uploadUserImage(Const.USER_IMAGE_STORAGE_PATH, profileImage);

        UserImage userImage = userImageRepositoryImpl.getImageByUserOrElseThrow((User) user);

        userImage.updateFileName(profileImage.getOriginalFilename());
        userImage.updateDBFileName(DBfileName);
        userImage.updateFileUrl(Const.USER_IMAGE_STORAGE);

        return userImage;
    }
}
