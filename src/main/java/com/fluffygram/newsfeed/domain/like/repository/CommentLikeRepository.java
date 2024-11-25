package com.fluffygram.newsfeed.domain.like.repository;



import com.fluffygram.newsfeed.domain.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    //좋아요id가 없을 경우 예외처리
    default CommentLike findCommentLikeByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException(id + "해당 좋아요가 존재하지 않습니다."));
    }

    @Query("SELECT bl FROM CommentLike bl "
            + " LEFT JOIN bl.user u "
            + " LEFT JOIN bl.comment b "
            + " WHERE u.id = :userId AND b.id = :commentId")
    CommentLike findByUserAndComment(Long userId, Long commentId);

    //likeStatus가 REGISTER 상태만 카운트 하기
    @Query("select COUNT(cl) CASE FROM CommentLike cl WHERE cl.comment.id = :commentId AND cl.likeStatus = 'REGISTER'")
    Long countByCommentIdAndLikeStatus(Long commentId);
}
