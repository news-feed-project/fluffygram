package com.fluffygram.newsfeed.domain.board.service;

import com.fluffygram.newsfeed.domain.board.dto.BoardResponseDto;
import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.board.repository.BoardRepository;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;


    //게시물 생성(저장)
    public BoardResponseDto save(Long id, String title, String contents){
        //사용자 id로 사용자 조회
        User findUserById = userRepository.findByIdOrElseThrow(id);

        Board board = new Board(id, title, contents);
        board.setUser(findUserById);//사용자를 조회해서 참조
        
        //게시물 저장
        Board saveBoard = boardRepository.save(board);

        return new BoardResponseDto(saveBoard.getId(), saveBoard.getTitle(), saveBoard.getContents(), findUserById.getUserNickname(), saveBoard.getCreatedAt(), saveBoard.getModifiedAt());
    }

    //게시물 전체 List 조회
    public List<BoardResponseDto> findAllBoard() {
        return boardRepository.findAll()
                .stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    //게시물 id로 특정 게시물 단건 조회
    public BoardResponseDto findBoardById(Long id) {
        //게시물 조회
        Board findBoard = boardRepository.findBoardByIdOrElseThrow(id);

        //사용자 id로 사용자 조회
        User findUserById = userRepository.findByIdOrElseThrow(id);

        return new BoardResponseDto(findBoard.getId(),findBoard.getTitle(),findBoard.getContents(), findUserById.getUserNickname(), findBoard.getCreatedAt(),findBoard.getModifiedAt());
    }

    //게시물 id로 특정 게시물 수정
    public BoardResponseDto updateBoard(Long id, @NotBlank String title, String contents) {
        //게시물 조회
        Board findBoard = boardRepository.findBoardByIdOrElseThrow(id);

        //사용자 id로 사용자 조회
        User findUserById = userRepository.findByIdOrElseThrow(id);

        findBoard.serUpdateBoard(title, contents);
        
        //수정된 게시물 저장하기
        Board saveBoard = boardRepository.save(findBoard);
        return new BoardResponseDto(saveBoard.getId(), saveBoard.getTitle(), saveBoard.getContents(), findUserById.getUserNickname(), saveBoard.getCreatedAt(), saveBoard.getModifiedAt());
    }
    
    //게시물 id로 특정 게시물 삭제
    public void deleteBoard(Long id) {
        //게시물 조회
        Board findBoard = boardRepository.findBoardByIdOrElseThrow(id);

        boardRepository.delete(findBoard);
    }
    
}
