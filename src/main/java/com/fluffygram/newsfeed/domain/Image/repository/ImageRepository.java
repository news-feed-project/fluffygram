package com.fluffygram.newsfeed.domain.Image.repository;

import com.fluffygram.newsfeed.domain.Image.entity.Image;
import com.fluffygram.newsfeed.domain.base.enums.ImageStatus;
import com.fluffygram.newsfeed.global.config.Const;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.NotFoundByIdException;
import com.fluffygram.newsfeed.global.tool.GetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{

    default String getImage(String imageUrl){
        return GetImage.getImage(Const.USER_IMAGE_STORAGE, imageUrl);
    }

    Optional<Image> findImageByStatusIdAndStatus(Long id, ImageStatus status);

    default Image getImageByIdOrElseThrow(Long id, ImageStatus status){
        return findImageByStatusIdAndStatus(id, status).orElseThrow(()-> new NotFoundByIdException(ExceptionType.FILE_NOT_FOUND));
    }

    List<Image> findAllByStatusIdAndStatus(Long id, ImageStatus status);

    void deleteByStatusIdAndStatus(Long id, ImageStatus status);

}
