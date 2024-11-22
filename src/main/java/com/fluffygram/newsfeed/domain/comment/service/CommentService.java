package com.fluffygram.newsfeed.domain.comment.service;

import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.board.repository.BoardRepository;
import com.fluffygram.newsfeed.domain.comment.entity.Comments;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import com.fluffygram.newsfeed.domain.comment.dto.CommentsResponseDto;
import com.fluffygram.newsfeed.domain.comment.dto.CommentsWithUsernameResponseDto;
import com.fluffygram.newsfeed.domain.comment.repository.CommentsRepository;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    //댓글 생성
    public CommentsResponseDto createComment(Long boardId, Long userId,String comment) {
        User user = UserRepository.findByIdOrElseThrow();
        Board board = boardRepository.findBoardByIdOrElseThrow(boardId);

        Comments comments = new Comments(comment, user, board);

        return new CommentsResponseDto(commentsRepository.save(comments));
    }
    //댓글 전체 조회

    public List<CommentsResponseDto> findAllComments() {
        List<Comments> comments = commentsRepository.findAll();

        return comments.stream().map(CommentsResponseDto::toDto).toList();
    }
    //특정 ID로 댓글 단건 조회

    public CommentsResponseDto findCommentsById(Long id) {

        Comments comments = commentsRepository.findById(id).orElseThrow(() -> new BusinessException(ExceptionType.USER_NOT_FOUND));

        return CommentsResponseDto.toDto(comments);
    }
    //댓글 삭제

    public void deleteComment(Long id) {
        Comments findcomments = commentsRepository.findCommentsByIdOrElseThrow(id);
        commentsRepository.delete(findcomments);
    }
    //댓글 수정
    public  void UpdateComments(Long id,Long boardId,Long userId, String comment) {
        Comments setId  = commentsRepository.findCommentsByIdOrElseThrow(id);
        commentsRepository.update(setId);


    }
}