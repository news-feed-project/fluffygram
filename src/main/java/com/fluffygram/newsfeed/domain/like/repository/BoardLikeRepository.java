package com.fluffygram.newsfeed.domain.like.repository;


import com.fluffygram.newsfeed.domain.like.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    //조회 - 좋아요id가 없을 경우 예외처리
    default BoardLike findBoardLikeByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException(id + "해당 좋아요가 존재하지 않습니다."));
    }

    //게시물 id, 사용자 id
    Optional<BoardLike> findByboardIdAndUserId(Long userId, Long boardId);

}
