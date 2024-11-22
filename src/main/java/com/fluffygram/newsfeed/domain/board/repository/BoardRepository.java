package com.fluffygram.newsfeed.domain.board.repository;

import com.fluffygram.newsfeed.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface BoardRepository extends JpaRepository<Board, Long> {
    
    //조회 - 게시물 id가 없을 경우 예외처리
    default Board findBoardByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException(id + "해당 게시물이 존재하지 않습니다."));
    }

}
