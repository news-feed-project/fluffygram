package com.fluffygram.newsfeed.domain.comment.repository;

import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.NotFoundByIdException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fluffygram.newsfeed.domain.comment.entity.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findCommentsByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() -> new NotFoundByIdException(ExceptionType.COMMENT_NOT_FOUND));
    }

    Page<Comment> findCommentByBoardIdOrderByCreatedAtDesc(Pageable pageable, Long boardId);
}
