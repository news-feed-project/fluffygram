package com.fluffygram.newsfeed.domain.board.service;

import com.fluffygram.newsfeed.domain.board.dto.BoardResponseDto;
import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.board.repository.BoardRepository;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final UserRepository userRepository;

    //게시물 생성(저장)
    public BoardResponseDto save(Long userId, String title, String contents){
        //이메일로 사용자 조회
        User findUserByEmail = userRepository.findUserByEmailOrElseThrow(email);

        Board board = new Board(title, contents);
        board.setUser(findUserByEmail);//사용자를 조회해서 참조
        
        //게시물 저장
        Board saveBoard = BoardRepository.save(board);

        return new BoardResponseDto(saveBoard.getId(), saveBoard.getTitle(), saveBoard.getContents());
    }

    //게시물 전체 조회
    public BoardResponseDto findAllBoard() {
        return BoardRepository.findAll()
                .stream()
                .map(BoardResponseDto::toDto)
                .toList();;
    }
}
