package com.fluffygram.newsfeed.domain.comment.service;

import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.board.repository.BoardRepository;
import com.fluffygram.newsfeed.domain.comment.entity.Comments;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fluffygram.newsfeed.domain.comment.dto.CommentsResponseDto;
import com.fluffygram.newsfeed.domain.comment.repository.CommentsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;

    private final UserRepository UserRepository;
    private final BoardRepository boardRepository;

    //댓글 생성
    public CommentsResponseDto createComment(Long boardId, Long userId,String comment) {
        User user = UserRepository.findByIdOrElseThrow(userId);
        Board board = boardRepository.findBoardByIdOrElseThrow(boardId);

        Comments comments = new Comments(comment, user, board);

        return CommentsResponseDto.toDto(commentsRepository.save(comments));
    }

    public List<CommentsResponseDto> findAllComments() {
        List<Comments> comments = commentsRepository.findAll();

        return comments.stream().map(CommentsResponseDto::toDto).toList();
    }

    public CommentsResponseDto findCommentsById(Long id) {

        Comments comments = commentsRepository.findById(id).orElseThrow(() -> new BusinessException(ExceptionType.USER_NOT_FOUND));

        return CommentsResponseDto.toDto(comments);
    }

    public void deleteComment(Long id) {
        commentsRepository.deleteById(id);
    }
    public  void UpdateComments(Long id, String comment){
        Comments comments = commentsRepository.findCommentsByIdOrElseThrow(id);


        comments.updateComment(comment);

    }

}