package com.fluffygram.newsfeed.domain.user.controller;

import com.fluffygram.newsfeed.domain.user.dto.*;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.service.UserService;
import com.fluffygram.newsfeed.global.config.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestParam MultipartFile profileImage, @Valid @ModelAttribute SignUpRequestDto requestDto){
        UserResponseDto userResponseDto =
                userService.signUp(
                        requestDto.getEmail(),
                        requestDto.getPassword(),
                        requestDto.getUserNickname(),
                        requestDto.getPhoneNumber(),
                        profileImage
                );

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }



    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> userResponseDtoList = userService.getAllUsers();

        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }


    @GetMapping("/my_page/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.getUserById(id);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @GetMapping("/others/{id}")
    public ResponseEntity<OtherUserResponseDto> getOtherUser(@PathVariable Long id) {
        OtherUserResponseDto otherUserResponseDto = userService.getOtherUserById(id);

        return new ResponseEntity<>(otherUserResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestParam MultipartFile profileImage, @Valid @ModelAttribute UpdateRequestDto requestDto){
        UserResponseDto userResponseDto = userService.updateUserById(id,
                requestDto.getPresentPassword(), requestDto.getChangePassword(), requestDto.getUserNickname(), requestDto.getPhoneNumber(), profileImage);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> userDelete(@PathVariable Long id, @Valid @RequestBody DeleteRequestDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        userService.delete(id, requestDto.getPassword(), user.getId());
        session.invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletRequest request) {
        User user = userService.login(requestDto.getEmail(), requestDto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
