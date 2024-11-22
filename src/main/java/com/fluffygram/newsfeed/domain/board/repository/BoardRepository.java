package com.fluffygram.newsfeed.domain.board.repository;

import com.fluffygram.newsfeed.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {
    
    //조회 - 게시물 id가 없을 경우 예외처리
    default Board findBoardByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException(id + "해당 게시물이 존재하지 않습니다."));
    }

    Page<Board> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.modifiedAt BETWEEN :start AND :end ORDER BY b.boardLikeListList.size DESC")
    Page<Board> findAllByOrderByModifiedAtDesc(Pageable pageable,
                                               @Param("start") String startAt,
                                               @Param("end") String endAt,
                                               @Param("like") String likeManySort);

}
