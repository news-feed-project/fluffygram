package com.fluffygram.newsfeed.domain.user.service;

import com.fluffygram.newsfeed.domain.Image.entity.Image;
import com.fluffygram.newsfeed.domain.Image.service.ImageService;
import com.fluffygram.newsfeed.domain.base.enums.ImageStatus;
import com.fluffygram.newsfeed.domain.user.dto.UserResponseDto;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.base.enums.UserStatus;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.config.PasswordEncoder;
import com.fluffygram.newsfeed.global.exception.*;
import com.fluffygram.newsfeed.global.tool.CustomMultipartFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ImageService imageService;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final ResourceLoader resourceLoader;

    /**
     * 회원가입 서비스
     * 사용자 정보와 이미지를 저장
     */
    @Transactional
    public UserResponseDto signUp(String email, String password, String userNickname, String phoneNumber,
                                  MultipartFile profileImage) throws IOException {
        // 이미 존재하는 email 인지 확인
        if(userRepository.existsByEmail(email)){
            throw new BadValueException(ExceptionType.EXIST_USER);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 유저 생성
        User user = new User(email, encodedPassword, userNickname, phoneNumber, UserStatus.REGISTER);

        // 유저 DB 저장
        User savedUser = userRepository.save(user);

        if(profileImage == null || profileImage.isEmpty()){
            Resource resource = resourceLoader.getResource("classpath:static/user.jpg");
            byte[] content = Files.readAllBytes(resource.getFile().toPath());

            profileImage =  new CustomMultipartFile(
                    "profileImage",
                    resource.getFilename(),
                    "image/jpeg",
                    content
            );
        }

        // 이미지 저장
        Image image = imageService.saveImage(profileImage, savedUser.getId(), ImageStatus.USER);

        // 유저의 이미지 이름을 고유한 이름으로 업데이트
        user.updateProfileImage(image);

        savedUser = userRepository.save(savedUser);

        return UserResponseDto.ToDtoForMine(savedUser);
    }

    /**
     *  사용자 전체 조회 서비스
     *  관리자용 사용자 전체 조회
     *
     */
    public List<UserResponseDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).stream().map(UserResponseDto::ToDtoForAll).toList();
    }

    /**
     *  사용자 단건 조회 서비스
     *  사용자 프로필을 조회
     *  본인과 타인의 전달하는 정보가 다르다.
     *
     */
    public UserResponseDto getUserById(Long id, Long loginUserId) {
        User user = userRepository.findByIdOrElseThrow(id);

        // 로그인한 사용자와 조회하는 사용자 다르면 민감한 정보 제외한 정보 전달
        if (!id.equals(loginUserId)) {
            return UserResponseDto.ToDtoForOther(user);
        }

        // 사용자 모든 정보 전달
        return UserResponseDto.ToDtoForMine(user);
    }

    /**
     *  사용자 정보 수정 서비스
     *  본인인 경우에만 수정 가능
     *  비밀번호는 영어 대소문자 1개, 특수기호 1개, 숫자 1개 최소 포함 8글자 이상 조건
     *  수정값이 비어있으면 기존 내용 그대로 유지
     *
     */
    @Transactional
    public UserResponseDto updateUserById(Long id,
                                          String presentPassword,
                                          String changePassword,
                                          String userNickname,
                                          String phoneNumber,
                                          MultipartFile profileImage,
                                          Long loginUserId) {
        // 로그인한 사용자와 아이디(id) 일치 여부 확인
        if(!id.equals(loginUserId)){
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }

        User user = userRepository.findByIdOrElseThrow(id);

        // 비밀번호 일치 확인
        if(passwordEncoder.matches(presentPassword, user.getPassword())){
            throw new BadValueException(ExceptionType.PASSWORD_NOT_CORRECT);
        }

        // 비밀번호 조건에 맞는지 확인
        if(!changePassword.isEmpty()){
            // 정규식에 맞는지 확인
            if(!changePassword.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,40}$")){
                throw new BadValueException(ExceptionType.BAD_PASSWORD);
            }
        }

        // 동일한 비밀번호 변경 시도 확인
        if(!passwordEncoder.matches(changePassword, user.getPassword())){
            throw new BadValueException(ExceptionType.PASSWORD_SAME);
        }

        // 이미지 파일 저장하기
        Image image = imageService.updateImage(profileImage, id, ImageStatus.USER);

        // user 정보 변경하기
        user = user.updateUser(changePassword, userNickname, phoneNumber, image);

        User savedUser = userRepository.save(user); // 확인용

        return UserResponseDto.ToDtoForMine(savedUser);
    }

    /**
     *  사용자 삭제 서비스
     *  논리적인 삭제
     *  이미지도 논리적인 데이터만 삭제
     *  삭제되면 사용자 이름 '탈퇴한 사용자'로 변경
     *
     */
    @Transactional
    public void delete(Long id, String password, Long loginUserId) {
        // 로그인한 사용자와 아이디(id) 일치 여부 확인
        if(!id.equals(loginUserId)){
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }

        User userById = userRepository.findByIdOrElseThrow(id);

        // 탈퇴 여부 확인
        if(userById.getUserStatus().equals(UserStatus.DELETE)){
            throw new WrongAccessException(ExceptionType.DELETED_USER);
        }

        // 비밀번호 일치 여부 확인
        if(passwordEncoder.matches(password, userById.getPassword())){
            throw new BadValueException(ExceptionType.PASSWORD_NOT_CORRECT);
        }

        // 유저 상태를 탈퇴로 변경
        userById.updateUserStatus(UserStatus.DELETE);

        //유저 이미지 데이터 삭제
        imageService.deleteImage(id, ImageStatus.USER);

        // 유저 닉네임을 '탈퇴한 사용자' 로 변경
        userById.updateUserNickname();

    }

    /**
     *  로그인 서비스
     *
     */
    public User login(String email, String password) {
        User user = userRepository.findUserByEmailOrElseThrow(email);

        // 탈퇴 여부 확인
        if(user.getUserStatus().equals(UserStatus.DELETE)){
            throw new WrongAccessException(ExceptionType.DELETED_USER);
        }

        // 비밀번호 일치 여부 확인
        if(passwordEncoder.matches(password, user.getPassword())){
            throw new BadValueException(ExceptionType.PASSWORD_NOT_CORRECT);
        }

        return user;
    }
}
