package com.fluffygram.newsfeed.domain.user.service;

import com.fluffygram.newsfeed.domain.Image.entity.Image;
import com.fluffygram.newsfeed.domain.Image.service.ImageService;
import com.fluffygram.newsfeed.domain.base.enums.ImageStatus;
import com.fluffygram.newsfeed.domain.user.dto.UserResponseDto;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.base.enums.UserStatus;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.config.PasswordEncoder;
import com.fluffygram.newsfeed.global.exception.BadValueException;
import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ImageService imageService;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final ResourceLoader resourceLoader;

    @Transactional
    public UserResponseDto signUp(String email,String password, String userNickname, String phoneNumber, MultipartFile profileImage) {
        // 이미 존재하는 email 인지 확인
        if(userRepository.existsByEmail(email)){
            throw new BusinessException(ExceptionType.EXIST_USER);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 유저 생성
        User user = new User(email, encodedPassword, userNickname, phoneNumber, UserStatus.REGISTER);

        // 유저 DB 저장
        User savedUser = userRepository.save(user);

        if(profileImage == null || profileImage.isEmpty()){
            Resource resource = resourceLoader.getResource("classpath:static/user.jpg");
            profileImage = (MultipartFile) resource;
        }

        // 이미지 저장
        Image image = imageService.saveImage(profileImage, savedUser.getId());

        // 유저의 이미지 이름을 고유한 이름으로 업데이트
        user.updateProfileImage(image);

        savedUser = userRepository.save(savedUser);

        return UserResponseDto.ToDtoForMine(savedUser);
    }

    public List<UserResponseDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).stream().map(UserResponseDto::ToDtoForMine).toList();
    }

    public UserResponseDto getUserById(Long id, Long loginUserId) {
        User user = userRepository.findByIdOrElseThrow(id);

        if (!id.equals(loginUserId)) {
            return UserResponseDto.ToDtoForOther(user);
        }

        return UserResponseDto.ToDtoForMine(user);
    }


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
            throw new BusinessException(ExceptionType.USER_NOT_MATCH);
        }

        User user = userRepository.findByIdOrElseThrow(id);

        // 비밀번호 일치 확인
        if(passwordEncoder.matches(presentPassword, user.getPassword())){
            throw new BusinessException(ExceptionType.PASSWORD_NOT_CORRECT);
        }

        // 동일한 비밀번호 변경 시도 확인
        if(!passwordEncoder.matches(changePassword, user.getPassword())){
            throw new BadValueException(ExceptionType.PASSWORD_SAME);
        }

        // 이미지 파일 저장하기
        Image image = imageService.updateImage(profileImage, id);

        // user 정보 변경하기
        user = user.updateUser(changePassword, userNickname, phoneNumber, image);

        User savedUser = userRepository.save(user);

        return UserResponseDto.ToDtoForMine(savedUser);
    }

    @Transactional
    public void delete(Long id, String password, Long loginUserId) {
        // 로그인한 사용자와 아이디(id) 일치 여부 확인
        if(!id.equals(loginUserId)){
            throw new BusinessException(ExceptionType.USER_NOT_MATCH);
        }

        User userById = userRepository.findByIdOrElseThrow(id);

        // 탈퇴 여부 확인
        if(userById.getUserStatus().equals(UserStatus.DELETE)){
            throw new BusinessException(ExceptionType.DELETED_USER);
        }

        // 비밀번호 일치 여부 확인
        if(passwordEncoder.matches(password, userById.getPassword())){
            throw new BusinessException(ExceptionType.PASSWORD_NOT_CORRECT);
        }

        // 유저 상태를 탈퇴로 변경
        userById.updateUserStatus(UserStatus.DELETE);

        //유저 이미지 데이터 삭제
        imageService.deleteImage(id, ImageStatus.USER);

        // 유저 닉네임을 '탈퇴한 사용자' 로 변경
        userById.updateUserNickname();


        userRepository.save(userById);
    }


    public User login(String email, String password) {
        User user = userRepository.findUserByEmailOrElseThrow(email);

        // 탈퇴 여부 확인
        if(user.getUserStatus().equals(UserStatus.DELETE)){
            throw new BusinessException(ExceptionType.DELETED_USER);
        }

        // 비밀번호 일치 여부 확인
        if(passwordEncoder.matches(password, user.getPassword())){
            throw new BusinessException(ExceptionType.PASSWORD_NOT_CORRECT);
        }

        return user;
    }
}
