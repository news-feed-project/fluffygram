package com.fluffygram.newsfeed.domain.like.service;


import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.board.repository.BoardRepository;
import com.fluffygram.newsfeed.domain.like.dto.BoardLikeResponseDto;
import com.fluffygram.newsfeed.domain.like.entity.BoardLike;
import com.fluffygram.newsfeed.domain.like.repository.BoardLikeRepository;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardLikeService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

}
