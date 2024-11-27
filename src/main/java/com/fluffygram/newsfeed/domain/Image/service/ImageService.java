package com.fluffygram.newsfeed.domain.Image.service;

import com.fluffygram.newsfeed.domain.Image.repository.ImageRepository;
import com.fluffygram.newsfeed.domain.base.enums.ImageStatus;
import com.fluffygram.newsfeed.domain.Image.entity.Image;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.tool.UploadImage;
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

    public Image saveImage(MultipartFile multipartFile, Long statusId, ImageStatus status) {

        String DBfileName = UploadImage.uploadUserImage(status.getPath(), multipartFile);

        Image image = new Image(multipartFile.getOriginalFilename(), DBfileName, status.getPathUrl(), statusId, status);

        return imageRepository.save(image);
    }

    public void saveImages(List<MultipartFile> multipartFiles, Long statusId, ImageStatus status) {
        if (multipartFiles == null || multipartFiles.isEmpty()) {
            return;
        }

        for (MultipartFile profileImage : multipartFiles) {
            String DBfileName = UploadImage.uploadUserImage(status.getPath(), profileImage);

            Image image = new Image(profileImage.getOriginalFilename(), DBfileName, status.getPathUrl(), statusId, ImageStatus.BOARD);

            imageRepository.save(image);
        }
    }

    public String getImage(Long statusId, ImageStatus status) {
        if (status.equals(ImageStatus.USER)) {
            User user = userRepository.findByIdOrElseThrow(statusId);

            return imageRepository.getImage(user.getProfileImage().getDBFileName());
        }
        else {
            return imageRepository.getImage("/defaultImage.jpg");
        }
    }

    public List<Image> getImages(Long id, ImageStatus status) {
        return imageRepository.findAllByStatusIdAndStatus(id, status);
    }

    public Image updateImage(MultipartFile multipartFile, Long statusId, ImageStatus status) {
        if (multipartFile == null){
            return null;
        }

        String DBfileName = UploadImage.uploadUserImage(status.getPath(), multipartFile);

        Image image = imageRepository.getImageByIdOrElseThrow(statusId, status);

        image.updateFileName(multipartFile.getOriginalFilename());
        image.updateDBFileName(DBfileName);
        image.updateFileUrl(status.getPathUrl());

        return image;
    }

    @Transactional
    public List<Image> updateImages(List<MultipartFile> multipartFiles, Long statusId, ImageStatus status) {
        //업데이트 이미지가 없으면 기존 이미지 리턴
        if (multipartFiles == null){
           return imageRepository.findAllByStatusIdAndStatus(statusId, status).stream().toList();
        }



        List<Image> updateImages = new ArrayList<>();

        // 기존 게시물의 이미지들 정보 제거
        imageRepository.deleteByStatusIdAndStatus(statusId, status);

        for (MultipartFile multipartFile : multipartFiles) {
            String DBfileName = UploadImage.uploadUserImage(status.getPath(), multipartFile);

            Image image = new Image(multipartFile.getOriginalFilename(), DBfileName, status.getPathUrl(), statusId, status);

            updateImages.add(image);

            imageRepository.save(image);
        }

        return updateImages;
    }

    @Transactional
    public void deleteImage(Long id, ImageStatus status) {
        if (status != ImageStatus.ORPHANAGE) {
            imageRepository.deleteByStatusIdAndStatus(id, status);
        } else{
            imageRepository.deleteById(id);
        }
    }
}
