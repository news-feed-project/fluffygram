package com.fluffygram.newsfeed.domain.like.service;


import com.fluffygram.newsfeed.domain.comment.entity.Comment;
import com.fluffygram.newsfeed.domain.comment.repository.CommentRepository;
import com.fluffygram.newsfeed.domain.like.dto.CommentLikeResponseDto;
import com.fluffygram.newsfeed.domain.like.entity.CommentLike;
import com.fluffygram.newsfeed.domain.base.enums.LikeStatus;
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

        //댓글 조회
        Comment findCommentById = commentRepository.findCommentsByIdOrElseThrow(commentId);
        //사용자 조회
        User findUserById = userRepository.findByIdOrElseThrow(userId);

        //기존 좋아요 데이터 존재 여부 확인
        CommentLike checkLike = commentLikeRepository.findByUserAndComment(userId, commentId);
        
        //좋아요 데이터가 존재 하지 않는 경우
        if (checkLike == null) {
            //좋아요 활성화
            CommentLike createCommentLike = new CommentLike(findUserById, findCommentById, LikeStatus.REGISTER);

            commentLikeRepository.save(createCommentLike);

            //저장된 좋아요 데이터를 반환
            checkLike = createCommentLike;
        } else {
            //데이터가 존재하는 경우

            //좋아요 상태가 REGISTER(활성화)일 경우 한번 더 클릭하면 DELETE(비활성화)로 상태 변경
            if (checkLike.getLikeStatus().equals(LikeStatus.REGISTER)) {
                //비활성화로 업데이트
                checkLike.updateLikeStatus(LikeStatus.DELETE);

                commentLikeRepository.save(checkLike);
            } else if(checkLike.getLikeStatus().equals(LikeStatus.DELETE)){
                //좋아요 상태가 DELETE(비활성화)일 경우 한번 더 클릭하면 REGISTER(활성화)로 변경
                checkLike.updateLikeStatus(LikeStatus.REGISTER);

                commentLikeRepository.save(checkLike);
            }
        }

        // 게시물 ID로 좋아요 개수 카운트
        Long likeCount = commentLikeRepository.countByCommentIdAndLikeStatus(commentId);

        return CommentLikeResponseDto.toDto(checkLike, likeCount);
    }
}
