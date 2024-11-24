package com.fluffygram.newsfeed.domain.like.repository;



import com.fluffygram.newsfeed.domain.like.entity.BoardLike;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.NotFountByIdException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    //좋아요id가 없을 경우 예외처리
    default BoardLike findBoardLikeByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFountByIdException(ExceptionType.LIKE_NOT_FOUND));
    }

    @Query("SELECT bl FROM BoardLike bl "
            + " LEFT JOIN bl.user u "
            + " LEFT JOIN bl.board b "
            + " WHERE u.id = :userId AND b.id = :boardId")
    BoardLike findByUserAndBoard(Long userId, Long boardId);

    //likeStatus가 REGISTER 상태만 카운트 하기
    @Query("select COUNT(bl) CASE FROM BoardLike bl WHERE bl.board.id = :boardId AND bl.likeStatus = 'REGISTER'")
    Long countByBoardIdAndLikeStatus(Long boardId);
}
