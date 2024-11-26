package com.fluffygram.newsfeed.domain.board.service;

import com.fluffygram.newsfeed.domain.Image.entity.Image;
import com.fluffygram.newsfeed.domain.Image.service.ImageService;
import com.fluffygram.newsfeed.domain.base.enums.ImageStatus;
import com.fluffygram.newsfeed.domain.board.dto.BoardPaginationDto;
import com.fluffygram.newsfeed.domain.board.dto.BoardResponseDto;
import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.board.repository.BoardRepository;
import com.fluffygram.newsfeed.domain.board.specification.BoardSpecification;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.NotMatchByUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    private final ImageService imageService;

    // 게시물 생성(저장)
    public BoardResponseDto save(Long userId, String title, String contents, List<MultipartFile> boardImages, Long loginUserId) {
        // 로그인한 사용자와 아이디(id) 일치 여부 확인
        if(!loginUserId.equals(userId)) {
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }
        // 사용자 id로 사용자 조회
        User findUserById = userRepository.findByIdOrElseThrow(userId);

        Board board = new Board(title, contents, findUserById);

        // 게시물 저장
        Board saveBoard = boardRepository.save(board);

        // 이미지들 저장
        imageService.saveImages(boardImages, saveBoard.getId(), ImageStatus.BOARD);

        List<Image> images = imageService.getImages(saveBoard.getId(), ImageStatus.BOARD);

        return BoardResponseDto.toDto(saveBoard, images);
    }

    //게시물 전체 조회
    public List<BoardResponseDto> findAllBoard(Pageable pageable, BoardPaginationDto criteria) {
        Specification<Board> spec = Specification.where(null);

        // day 범위 조건
        if (criteria.getStartAt() != null || criteria.getEndAt() != null) {
            spec = spec.and(BoardSpecification.filterByModifyAtRange(criteria.getStartAt(), criteria.getEndAt()));
        }

        // dateType 에 따른 정렬 조건
        if (criteria.getDateType() != null) {
            spec = spec.and(BoardSpecification.filterByDateType(criteria.getDateType()));
        }

        // likeManySort 조건
        if ("like".equals(criteria.getLikeManySort())) {
            spec = spec.and(BoardSpecification.filterByLikeManySortAndDateType(criteria.getDateType()));
        }

        List<BoardResponseDto> responseDtoList = new ArrayList<>();


        List<Board> boards =  boardRepository.findAll(spec, pageable).toList();
        for (Board board : boards) {
            List<Image> images = imageService.getImages(board.getId(), ImageStatus.BOARD);
            responseDtoList.add(BoardResponseDto.toDtoForAll(board, images));
        }

        return responseDtoList;
    }

    //게시물 id로 특정 게시물 단건 조회
    public BoardResponseDto findBoardById(Long id) {
        // 게시물 조회
        Board findBoard = boardRepository.findBoardByIdOrElseThrow(id);

        // 이미지들 가져오기
        List<Image> images = imageService.getImages(id, ImageStatus.BOARD);

        return BoardResponseDto.toDto(findBoard, images);
    }

    //게시물 id로 특정 게시물 수정
    public BoardResponseDto updateBoard(Long boardId, String title, String contents, Long loginUserId, List<MultipartFile> boardImages) {
        // 게시물 조회
        Board findBoard = boardRepository.findBoardByIdOrElseThrow(boardId);

        // 로그인한 사용자와 아이디(id) 일치 여부 확인
        if(!loginUserId.equals(findBoard.getUser().getId())) {
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }

        // 제목 및 내용 수정하기
        findBoard = findBoard.updateBoard(title, contents);

        // 이미지 파일 수정하기
        List<Image> images = imageService.updateImages(boardImages, boardId, ImageStatus.BOARD);

        // 수정된 게시물 저장하기
        Board saveBoard = boardRepository.save(findBoard);

        return BoardResponseDto.toDto(saveBoard, images);
    }
    
    //게시물 id로 특정 게시물 삭제
    public void deleteBoard(Long boardId, Long loginUserId) {
        // 게시물 조회
        Board findBoard = boardRepository.findBoardByIdOrElseThrow(boardId);

        // 로그인한 사용자와 아이디(id) 일치 여부 확인
        if(!loginUserId.equals(findBoard.getUser().getId())) {
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }

        // 게시물 이미지 데이터 삭제
        imageService.deleteImage(boardId, ImageStatus.BOARD);

        // 게시물 삭제
        boardRepository.delete(findBoard);

    }
    
}
