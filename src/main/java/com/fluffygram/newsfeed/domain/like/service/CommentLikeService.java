package com.fluffygram.newsfeed.domain.like.service;


import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.board.repository.BoardRepository;
import com.fluffygram.newsfeed.domain.comment.entity.Comment;
import com.fluffygram.newsfeed.domain.comment.repository.CommentRepository;
import com.fluffygram.newsfeed.domain.like.dto.BoardLikeResponseDto;
import com.fluffygram.newsfeed.domain.like.dto.CommentLikeResponseDto;
import com.fluffygram.newsfeed.domain.like.entity.BoardLike;
import com.fluffygram.newsfeed.domain.like.entity.CommentLike;
import com.fluffygram.newsfeed.domain.like.entity.LikeStatus;
import com.fluffygram.newsfeed.domain.like.repository.BoardLikeRepository;
import com.fluffygram.newsfeed.domain.like.repository.CommentLikeRepository;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    //좋아요 생성, 취소 - 활성화, 비활성화
    public CommentLikeResponseDto toggleLike(Long loginUserId, Long userId, Long commentId) {
        //로그인한 id가 동일한 유저가 댓글에 좋아요를 눌렀는지 확인
        if(!loginUserId.equals(userId)) {
            throw new RuntimeException("사용자가 일치하지 않습니다.");
        }

//      //댓글 조회
        Comment findCommentById = commentRepository.findCommentsByIdOrElseThrow(commentId);
//       //사용자 조회
        User findUserById = userRepository.findByIdOrElseThrow(userId);

        //기존 좋아요 데이터 존재 여부 확인
//        BoardLike checkLike = boardLikeRepository.findByUserAndBoardOrElseThrow(userId, boardId);
        //-> 데이터 존재하지 않는다고 에러뜨고 이후 코드로 진행이 안됨

        //기존 좋아요 데이터 존재 여부 확인
        CommentLike checkLike = commentLikeRepository.findByUserAndComment(userId, commentId);
        //-> NullPointerException entity.BoardLike.getId()" because "boardLike" is null 메세지 이후 DB에 데이터는 들어감
        
        //좋아요 데이터가 존재 하지 않는 경우
        if (checkLike == null) {
            //좋아요 활성화
            CommentLike createCommentLike = new CommentLike(findUserById, findCommentById, LikeStatus.REGISTER);
            //DB에 저장
            commentLikeRepository.save(createCommentLike);

            //저장된 좋아요 데이터를 반환 * -> 데이터가 존재하지 않을 경우 생성된 좋아요 데이터를 반환해 줘야함
            checkLike = createCommentLike;
        } else {
            //데이터가 존재하는 경우

            //좋아요 상태가 REGISTER일 경우 한번 더 클릭하면 DELETE로 상태 변경
            if (checkLike.getLikeStatus().equals(LikeStatus.REGISTER)) {
                //비활성화로 업데이트
                checkLike.updateLikeStatus(LikeStatus.DELETE);
                //DB에 상태 저장
                commentLikeRepository.save(checkLike);
            } else if(checkLike.getLikeStatus().equals(LikeStatus.DELETE)){
                //좋아요 상태가 DELETE일 경우 한번 더 클릭하면 REGISTER로 변경
                checkLike.updateLikeStatus(LikeStatus.REGISTER);
                //DB에 상태 저장
                commentLikeRepository.save(checkLike);
            }
        }

        // 게시물 ID로 좋아요 개수 카운트 - jpa에서 count는 long 타입
        Long likeCount = commentLikeRepository.countByCommentIdAndLikeStatus(commentId);

        return CommentLikeResponseDto.toDto(checkLike, likeCount);
    }
}
