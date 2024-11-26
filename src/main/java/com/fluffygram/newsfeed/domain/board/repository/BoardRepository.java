package com.fluffygram.newsfeed.domain.board.repository;

import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.NotFoundByIdException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;


public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {

    //게시물 id가 없을 경우 예외처리
    default Board findBoardByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundByIdException(ExceptionType.BOARD_NOT_FOUND));
    }

    @Query("SELECT b FROM Board b " +
            "WHERE (:startDate IS NULL OR b.modifiedAt >= :startDate) " +
            "AND (:endDate IS NULL OR b.modifiedAt <= :endDate)" +
            "ORDER BY " +
            "CASE WHEN :like = 'like' THEN COUNT(b.boardLikeList) END DESC, " +
            "b.modifiedAt DESC")
    Page<Board> findPostsByDateAndLikes(
            Pageable pageable,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("like") String like);
}


