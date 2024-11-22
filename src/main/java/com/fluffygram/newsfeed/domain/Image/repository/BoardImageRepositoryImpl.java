package com.fluffygram.newsfeed.domain.Image.repository;

import com.fluffygram.newsfeed.domain.Image.entity.BoardImage;
import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.global.config.Const;
import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.tool.GetImage;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardImageRepositoryImpl extends JpaRepository<BoardImage, Long>{

    default Resource getImage(String imageUrl){
        return GetImage.getImage(Const.BOARD_IMAGE_STORAGE_PATH, imageUrl);
    }

    Optional<BoardImage> findBoardImageByDBFileName(String DBFileName);

    default BoardImage getImageOrElseThrow(String DBFileName){
        return findBoardImageByDBFileName(DBFileName).stream().findAny().orElseThrow(()-> new BusinessException(ExceptionType.FILE_NOT_FOUND));
    }

    Optional<BoardImage> findBoardImageByBoard(Board board);


    default BoardImage getImageByBoardOrElseThrow(Board board){
        return findBoardImageByBoard(board).orElseThrow(()-> new BusinessException(ExceptionType.FILE_NOT_FOUND));
    }

}
