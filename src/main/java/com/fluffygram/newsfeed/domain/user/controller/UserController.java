package com.fluffygram.newsfeed.domain.user.controller;

import com.fluffygram.newsfeed.domain.user.dto.*;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.service.UserService;
import com.fluffygram.newsfeed.global.config.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    /**
     *  회원 가입(사용자 생성) API
     *
     * @param profileImage  사용자 프로필 이미지
     * @param requestDto    사용자 정보를 입력한 요청 Dto
     * @return ResponseEntity<UserResponseDto>  사용자 정보 및 http 상태 전달
     *
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@Valid @RequestParam MultipartFile profileImage, @Valid @ModelAttribute SignUpRequestDto requestDto){
        UserResponseDto userResponseDto = userService.signUp(
                        requestDto.getEmail(),
                        requestDto.getPassword(),
                        requestDto.getUserNickname(),
                        requestDto.getPhoneNumber(),
                        profileImage
                );

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }


    /**
     *  사용자 전체 조회 API
     *  관리자가 회원 전체를 조회할 때 사용
     *
     * @return ResponseEntity<List<UserResponseDto>>  사용자들 정보 및 http 상태 전달
     *
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(@RequestParam @PageableDefault() Pageable pageable) {
        List<UserResponseDto> userResponseDtoList = userService.getAllUsers(pageable);

        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    /**
     *  사용자 단건 조회 API
     *  자신의 프로필을 조회할 때 사용
     *
     * @param id    사용자 id
     *
     * @return ResponseEntity<UserResponseDto>  사용자 정보 및 http 상태 전달
     *
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User LoginUser = (User) session.getAttribute(Const.LOGIN_USER);

        UserResponseDto userResponseDto = userService.getUserById(id, LoginUser.getId());

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     *  사용자 정보 수정 API
     *  본인의 정보를 수정할 때 사용
     *
     * @param id    사용자 id
     * @param profileImage 프로필 이미지
     * @param requestDto 수정할 내용이 담긴 dto
     *
     * @return ResponseEntity<UserResponseDto>  수정한 사용자 정보 및 http 상태 전달
     *
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                      @RequestParam MultipartFile profileImage,
                                                      @Valid @ModelAttribute UpdateUserRequestDto requestDto,
                                                      HttpServletRequest request){
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        UserResponseDto userResponseDto = userService.updateUserById(
                id,
                requestDto.getPresentPassword(),
                requestDto.getChangePassword(),
                requestDto.getUserNickname(),
                requestDto.getPhoneNumber(),
                profileImage,
                user.getId());

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     *  사용자 삭제 API
     *  사용자를 삭제할 때 사용
     *
     * @param id    사용자 id
     * @param requestDto 수정할 내용이 담긴 dto
     * @param request 요청 : Session 확인
     *
     * @return ResponseEntity<Void>  http 상태 전달
     *
     */
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> userDelete(@PathVariable Long id, @Valid @RequestBody DeleteUserRequestDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        userService.delete(id, requestDto.getPassword(), user.getId());
        session.invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *  로그인 API
     *
     * @param requestDto 수정할 내용이 담긴 dto
     * @param request 요청 : Session 확인
     *
     * @return ResponseEntity<Void>  http 상태 전달
     *
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletRequest request) {
        User user = userService.login(requestDto.getEmail(), requestDto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     *  로그아웃 API
     *
     * @param request 요청 : Session 확인
     *
     * @return ResponseEntity<Void>  http 상태 전달
     *
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
