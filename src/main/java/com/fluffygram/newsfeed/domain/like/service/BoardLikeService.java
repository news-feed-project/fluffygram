package com.fluffygram.newsfeed.domain.like.service;


import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.board.repository.BoardRepository;
import com.fluffygram.newsfeed.domain.like.dto.BoardLikeResponseDto;
import com.fluffygram.newsfeed.domain.like.entity.BoardLike;
import com.fluffygram.newsfeed.domain.like.entity.LikeStatus;
import com.fluffygram.newsfeed.domain.like.repository.BoardLikeRepository;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.NotMatchByUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardLikeService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    //좋아요 생성, 취소 - 활성화, 비활성화
    public BoardLikeResponseDto toggleLike(Long loginUserId, Long userId, Long boardId) {
        //로그인한 id가 동일한 유저가 게시물에 좋아요를 눌렀는지 확인
        if(!loginUserId.equals(userId)) {
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }

//      //게시물 조회
        Board findBoardById = boardRepository.findBoardByIdOrElseThrow(boardId);
//       //사용자 조회
        User findUserById = userRepository.findByIdOrElseThrow(userId);

        //기존 좋아요 데이터 존재 여부 확인
        BoardLike checkLike = boardLikeRepository.findByUserAndBoard(userId, boardId);
        
        //좋아요 데이터가 존재 하지 않는 경우
        if (checkLike == null) {
            //좋아요 활성화
            BoardLike createBoardLike = new BoardLike(findUserById, findBoardById, LikeStatus.REGISTER);
            //DB에 저장
            boardLikeRepository.save(createBoardLike);

            //저장된 좋아요 데이터를 반환
            checkLike = createBoardLike;
        } else {
            //데이터가 존재하는 경우

            //좋아요 상태가 REGISTER(활성화)일 경우 한번 더 클릭하면 DELETE(비활성화)로 상태 변경
            if (checkLike.getLikeStatus().equals(LikeStatus.REGISTER)) {
                checkLike.updateLikeStatus(LikeStatus.DELETE);

                boardLikeRepository.save(checkLike);
            } else if(checkLike.getLikeStatus().equals(LikeStatus.DELETE)){
                //좋아요 상태가 DELETE(비활성화)일 경우 한번 더 클릭하면 REGISTER(활성화)로 변경
                checkLike.updateLikeStatus(LikeStatus.REGISTER);

                boardLikeRepository.save(checkLike);
            }
        }

        // 게시물 ID로 좋아요 개수 카운트
        Long likeCount = boardLikeRepository.countByBoardIdAndLikeStatus(boardId);

        return BoardLikeResponseDto.toDto(checkLike, likeCount);
    }
}
