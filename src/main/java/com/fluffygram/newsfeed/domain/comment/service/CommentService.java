package com.fluffygram.newsfeed.domain.comment.service;

import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.board.repository.BoardRepository;
import com.fluffygram.newsfeed.domain.comment.entity.Comment;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.NotMatchByUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fluffygram.newsfeed.domain.comment.dto.CommentResponseDto;
import com.fluffygram.newsfeed.domain.comment.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    //댓글 생성
    public CommentResponseDto createComment(Long boardId, Long userId, String commentContents, Long loginUserId) {
        // 로그인한 사용자와 아이디(id) 일치 여부 확인
        if(!userId.equals(loginUserId)){//userid 검증
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH); //userid가 일치하지 않을시 예외처리
        }

        User user = userRepository.findByIdOrElseThrow(userId);
        Board board = boardRepository.findBoardByIdOrElseThrow(boardId);

        Comment comment = new Comment(commentContents, user, board);
        
        Comment savedComment = commentRepository.save(comment);

        return CommentResponseDto.toDto(savedComment);
    }


    public List<CommentResponseDto> findAllCommentByBoardId(Pageable pageable, Long boardId) {//페이징 구현
        Page<Comment> comments = commentRepository.findCommentByBoardIdOrderByCreatedAtDesc(pageable, boardId);

    //댓글 생성일 기준 내림차순
    public List<CommentResponseDto> findAllCommentByBoardId(Pageable pageable, Long boardId) {
        Page<Comment> comments = commentRepository.findCommentByBoardIdOrderByCreatedAtDesc(pageable, boardId);


        return comments.stream().map(CommentResponseDto::toDto).toList();
    }

    public CommentResponseDto findCommentById(Long id) {

        Comment comment = commentRepository.findCommentsByIdOrElseThrow(id);

        return CommentResponseDto.toDto(comment);
    }

    //댓글 수정
    public CommentResponseDto UpdateComments(Long id, String commentContents, Long loginUserId){
        Comment comment = commentRepository.findCommentsByIdOrElseThrow(id);

        // 로그인한 사용자와 아이디(id) 일치 여부 확인 및 게시물 작성자 일치 여부 확인
        if(!comment.getUser().getId().equals(loginUserId) ||
                !comment.getBoard().getUser().getId().equals(loginUserId)){
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }

        comment.updateComment(commentContents);

        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }

    //댓글 삭제
    public void deleteComment(Long id, Long loginUserId) {
        Comment comment = commentRepository.findCommentsByIdOrElseThrow(id);

        // 로그인한 사용자와 아이디(id) 일치 여부 확인 및 게시물 작성자 일치 여부 확인
        if(!comment.getUser().getId().equals(loginUserId) ||
                !comment.getBoard().getUser().getId().equals(loginUserId)){
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }

        commentRepository.deleteById(id);
    }
}