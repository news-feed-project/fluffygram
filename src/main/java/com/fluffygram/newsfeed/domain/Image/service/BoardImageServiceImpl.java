package com.fluffygram.newsfeed.domain.Image.service;

import com.fluffygram.newsfeed.domain.Image.dto.ImageResponseDto;
import com.fluffygram.newsfeed.domain.Image.repository.BoardImageRepositoryImpl;
import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.board.repository.BoardRepository;
import com.fluffygram.newsfeed.domain.Image.entity.BoardImage;
import com.fluffygram.newsfeed.global.tool.UploadImage;
import com.fluffygram.newsfeed.global.config.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardImageServiceImpl{

    private final BoardImageRepositoryImpl boardImageRepositoryImpl;
    private final BoardRepository boardRepository;

    @Transactional
    public BoardImage saveImage(MultipartFile profileImage, Long boardId) {
        Board board = boardRepository.findBoardByIdOrElseThrow(boardId);

        String DBfileName = UploadImage.uploadUserImage(Const.BOARD_IMAGE_STORAGE_PATH, profileImage);

        BoardImage boardImage = new BoardImage(board, profileImage.getOriginalFilename(), DBfileName, Const.BOARD_IMAGE_STORAGE);

        return boardImageRepositoryImpl.save(boardImage);
    }

    @Transactional
    public List<ImageResponseDto> saveImages(List<MultipartFile> profileImages, Long boardId) {
        Board board = boardRepository.findBoardByIdOrElseThrow(boardId);

        List<BoardImage> boardImages = new ArrayList<>();

        for (MultipartFile profileImage : profileImages) {
            String DBfileName = UploadImage.uploadUserImage(Const.BOARD_IMAGE_STORAGE_PATH, profileImage);

            BoardImage boardImage = new BoardImage(board, profileImage.getOriginalFilename(), DBfileName, Const.BOARD_IMAGE_STORAGE);

            boardImages.add(boardImageRepositoryImpl.save(boardImage));
        }

        return boardImages.stream().map(ImageResponseDto::BoardImageToDto).toList();
    }


    public Resource getImage(String imageUrl) {

        return boardImageRepositoryImpl.getImage(imageUrl);
    }


    public BoardImage updateImage(MultipartFile profileImage, Object board) {
        String DBfileName = UploadImage.uploadUserImage(Const.BOARD_IMAGE_STORAGE_PATH, profileImage);

        BoardImage boardImage = boardImageRepositoryImpl.getImageByBoardOrElseThrow((Board) board);

        boardImage.updateFileName(profileImage.getOriginalFilename());
        boardImage.updateDBFileName(DBfileName);
        boardImage.updateFileUrl(Const.BOARD_IMAGE_STORAGE);

        return boardImage;
    }

    public void deleteImage(String imageName) {
        BoardImage boardImage = boardImageRepositoryImpl.getImageOrElseThrow(imageName);

        boardImageRepositoryImpl.delete(boardImage);
    }
}
