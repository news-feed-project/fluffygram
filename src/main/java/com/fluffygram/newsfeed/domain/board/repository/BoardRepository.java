package com.fluffygram.newsfeed.domain.board.repository;

import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.NotFountByIdException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    
    //조회 - 게시물 id가 없을 경우 예외처리
    default Board findBoardByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFountByIdException(ExceptionType.BOARD_NOT_FOUND));
    }

    Page<Board> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
