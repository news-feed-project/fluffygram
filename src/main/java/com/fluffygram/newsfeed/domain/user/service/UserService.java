package com.fluffygram.newsfeed.domain.user.service;

import com.fluffygram.newsfeed.domain.Image.entity.UserImage;
import com.fluffygram.newsfeed.domain.Image.service.UserImageServiceImpl;
import com.fluffygram.newsfeed.domain.base.Valid.AccessWrongValid;
import com.fluffygram.newsfeed.domain.user.dto.UserResponseDto;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.enums.UserStatus;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.config.PasswordEncoder;
import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserImageServiceImpl userImageServiceImpl;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AccessWrongValid accessWrongValid;

    private final ResourceLoader resourceLoader;

    @Transactional
    public UserResponseDto signUp(String email,String password, String userNickname, String phoneNumber, MultipartFile profileImage) {
        Optional<User> userByEmail = userRepository.findByEmail(email);

        // 중복 email(사용자 아이디) 확인 여부
        userByEmail.ifPresent(user -> {throw new BusinessException(ExceptionType.EXIST_USER);});

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 유저 생성
        User user = new User(email, encodedPassword, userNickname, phoneNumber, UserStatus.REGISTER);

        // 유저 DB 저장
        User savedUser = userRepository.save(user);

        if(profileImage.isEmpty()){
            Resource resource = resourceLoader.getResource("classpath:static/user.jpg");
            profileImage = (MultipartFile) resource;
        }

        UserImage userImage = userImageServiceImpl.saveImage(profileImage, savedUser.getId());

        // 유저의 이미지 이름을 고유한 이름으로 업데이트
        user.updateProfileImage(userImage);

        return UserResponseDto.ToDtoForMine(savedUser);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponseDto::ToDtoForMine).toList();
    }

    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findByIdOrElseThrow(id);

        return UserResponseDto.ToDtoForMine(user);
    }


    @Transactional
    public UserResponseDto updateUserById(Long id, String presentPassword, String ChangePassword, String userNickname, String phoneNumber, MultipartFile profileImage) {
        User user = userRepository.findByIdOrElseThrow(id);

        // 비밀번호 일치 확인
        if(passwordEncoder.matches(presentPassword, user.getPassword())){
            throw new BusinessException(ExceptionType.PASSWORD_NOT_CORRECT);
        }

        // 동일한 비밀번호 변경 시도 확인
        if(!passwordEncoder.matches(ChangePassword, user.getPassword())){
            throw new BusinessException(ExceptionType.PASSWORD_SAME);
        }

        // 비밀번호 업데이트
        if(!ChangePassword.isEmpty()){
            user.updatePassword(passwordEncoder.encode(ChangePassword));
        }

        // 유저 닉네임 업데이트
        if(userNickname != null && !userNickname.isEmpty()){
            user.updateUserNickname(userNickname);
        }

        // 유저 전화번호 업데이트
        if(phoneNumber != null && !phoneNumber.isEmpty()){
            user.updatePhoneNumber(phoneNumber);
        }

        // 유저 프로필 이미지 변경
        if(profileImage != null && !profileImage.isEmpty()){
            UserImage userImage = userImageServiceImpl.updateImage(profileImage, user);
            user.updateProfileImage(userImage);
        }

        User savedUser = userRepository.save(user);

        return UserResponseDto.ToDtoForMine(savedUser);
    }

    @Transactional
    public void delete(Long id, String password, Long loginUserId) {
        User userById = userRepository.findByIdOrElseThrow(id);
        User userByLoginUserId = userRepository.findByIdOrElseThrow(loginUserId);

        // 탈퇴 여부 확인
        if(userById.getUserStatus().equals(UserStatus.DELETE)){
            throw new BusinessException(ExceptionType.DELETED_USER);
        }

        // 비밀번호 일치 여부 확인
        if(passwordEncoder.matches(password, userById.getPassword())){
            throw new BusinessException(ExceptionType.PASSWORD_NOT_CORRECT);
        }

        // 사용자 아이디(email) 일치 여부 확인
        if(!accessWrongValid.AccessMisMatchString(userById.getEmail(), userByLoginUserId.getEmail())){
            throw new BusinessException(ExceptionType.USER_NOT_MATCH);
        }

        userById.updateUserStatus(UserStatus.DELETE);
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
