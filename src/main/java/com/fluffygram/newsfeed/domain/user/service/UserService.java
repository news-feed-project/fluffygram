package com.fluffygram.newsfeed.domain.user.service;

import com.fluffygram.newsfeed.domain.base.Valid.AccessWrongValid;
import com.fluffygram.newsfeed.domain.user.dto.OtherUserResponseDto;
import com.fluffygram.newsfeed.domain.user.dto.UserResponseDto;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.entity.UserStatus;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.domain.userImage.service.UserImageService;
import com.fluffygram.newsfeed.global.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserImageService userImageService;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AccessWrongValid accessWrongValid;

    @Transactional
    public UserResponseDto signUp(String email,String password, String userNickname, String phoneNumber, MultipartFile profileImage) {
        Optional<User> userByEmail = userRepository.findByEmail(email);

        // 중복 email(사용자 아이디) 확인 여부
        userByEmail.ifPresent(user -> {throw new RuntimeException("이미 존재하는 유저입니다.");});

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(email, encodedPassword, userNickname, phoneNumber, profileImage.getOriginalFilename(), UserStatus.REGISTER);

        User savedUser = userRepository.save(user);

        String profileImageUrl = userImageService.saveUserImage(profileImage, savedUser);

        savedUser.updateProfileImage(profileImageUrl);

        return UserResponseDto.toDto(savedUser);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponseDto::toDto).toList();
    }

    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findByIdOrElseThrow(id);

        return UserResponseDto.toDto(user);
    }

    public OtherUserResponseDto getOtherUserById(Long id) {

        User user = userRepository.findByIdOrElseThrow(id);

        return OtherUserResponseDto.toDto(user);
    }

    @Transactional
    public UserResponseDto updateUserById(Long id, String PresentPassword, String ChangePassword, String userNickname, String phoneNumber, MultipartFile profileImage) {
        User user = userRepository.findByIdOrElseThrow(id);

        if(passwordEncoder.matches(PresentPassword, user.getPassword())){
            throw new RuntimeException("본인 확인 비밀번호가 일치하지 않습니다.");
        }

        if(!passwordEncoder.matches(ChangePassword, user.getPassword())){
            throw new RuntimeException("비밀번호가 동일합니다. 다시 입력해주세요.");
        }

        if(!ChangePassword.isEmpty()){
            user.updatePassword(passwordEncoder.encode(ChangePassword));
        }

        if(userNickname != null && !userNickname.isEmpty()){
            user.updateUserNickname(userNickname);
        }

        if(phoneNumber != null && !phoneNumber.isEmpty()){
            user.updatePhoneNumber(phoneNumber);
        }

        if(profileImage != null && !profileImage.isEmpty()){
            String profileImageUrl = userImageService.updateUserImage(profileImage, user);
            user.updateProfileImage(profileImageUrl);
        }

        User savedUser = userRepository.save(user);

        return UserResponseDto.toDto(savedUser);
    }

    @Transactional
    public void delete(Long id, String password, Long loginUserId) {
        User userById = userRepository.findByIdOrElseThrow(id);
        User userByLoginUserId = userRepository.findByIdOrElseThrow(loginUserId);

        // 탈퇴 여부 확인
        if(userById.getUserStatus().equals(UserStatus.DELETE)){
            throw new RuntimeException("이미 탈퇴한 유저입니다.");
        }

        // 비밀번호 일치 여부 확인
        if(passwordEncoder.matches(password, userById.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 사용자 아이디(email) 일치 여부 확인
        if(!accessWrongValid.AccessMisMatchString(userById.getEmail(), userByLoginUserId.getEmail())){
            throw new RuntimeException("사용자 아이디가 일치하지 않습니다.");
        }

        userById.updateUserStatus(UserStatus.DELETE);
        //userRepository.delete(userById.get());
    }


    public User login(String email, String password) {
        User user = userRepository.findUserByEmailOrElseThrow(email);

        if(user.getUserStatus().equals(UserStatus.DELETE)){
            throw new RuntimeException("이미 탈퇴한 유저입니다.");
        }

        if(passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}
