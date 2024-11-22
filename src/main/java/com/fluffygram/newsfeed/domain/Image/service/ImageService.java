package com.fluffygram.newsfeed.domain.Image.service;

import com.fluffygram.newsfeed.domain.Image.repository.ImageRepository;
import com.fluffygram.newsfeed.domain.base.enums.ImageStatus;
import com.fluffygram.newsfeed.domain.Image.entity.Image;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.tool.UploadImage;
import com.fluffygram.newsfeed.global.config.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Transactional
    public Image saveImage(MultipartFile profileImage, Long id) {

        String DBfileName = UploadImage.uploadUserImage(Const.USER_IMAGE_STORAGE_PATH, profileImage);

        Image image = new Image(profileImage.getOriginalFilename(), DBfileName, Const.USER_IMAGE_STORAGE, id, ImageStatus.USER);

        return imageRepository.save(image);
    }

    @Transactional
    public void saveImages(List<MultipartFile> profileImages, Long boardId) {

        for (MultipartFile profileImage : profileImages) {
            String DBfileName = UploadImage.uploadUserImage(Const.BOARD_IMAGE_STORAGE_PATH, profileImage);

            Image image = new Image(profileImage.getOriginalFilename(), DBfileName, Const.BOARD_IMAGE_STORAGE, boardId, ImageStatus.BOARD);

            imageRepository.save(image);
        }

    }

    public String getImage(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);

        return imageRepository.getUserImage(user.getProfileImage().getDBFileName());
    }

    public List<Image> getImages(Long id, ImageStatus status) {
        return imageRepository.findAllByStatusIdAndStatus(id, status);
    }

    public void deleteImage(Long id, ImageStatus status) {
        if (status == ImageStatus.BOARD || status == ImageStatus.USER) {
            imageRepository.deleteByStatusIdAndStatus(id, status);
        } else if (status == ImageStatus.ORPHANAGE) {
            imageRepository.deleteById(id);
        }
    }


    public Image updateImage(MultipartFile multipartFile, Long id) {
        if (multipartFile == null){
            return null;
        }

        String DBfileName = UploadImage.uploadUserImage(Const.USER_IMAGE_STORAGE_PATH, multipartFile);

        Image image = imageRepository.getImageByIdOrElseThrow(id);

        image.updateFileName(multipartFile.getOriginalFilename());
        image.updateDBFileName(DBfileName);
        image.updateFileUrl(Const.USER_IMAGE_STORAGE);

        return image;
    }

    public List<Image> updateImages(List<MultipartFile> multipartFiles, Long id) {
        if (multipartFiles == null){
            return null;
        }

        List<Image> images = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String DBfileName = UploadImage.uploadUserImage(Const.USER_IMAGE_STORAGE_PATH, multipartFile);

            Image image = imageRepository.getImageByIdOrElseThrow(id);

            image.updateFileName(multipartFile.getOriginalFilename());
            image.updateDBFileName(DBfileName);
            image.updateFileUrl(Const.USER_IMAGE_STORAGE);

            images.add(image);
        }

        return images;
    }
}
