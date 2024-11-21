package com.fluffygram.newsfeed.domain.user.service;

import com.fluffygram.newsfeed.domain.base.Valid.AccessWrongValid;
import com.fluffygram.newsfeed.domain.user.dto.UserResponseDto;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.image.GetUserImage;
import com.fluffygram.newsfeed.domain.user.image.UploadUserImage;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UploadUserImage uploadUserImage;
    private final PasswordEncoder passwordEncoder;
    private final AccessWrongValid accessWrongValid;

    public UserResponseDto signUp(String email,String password, String userNickname, String phoneNumber, MultipartFile profileImage) {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        userByEmail.ifPresent(user -> {throw new RuntimeException("이미 존재하는 유저입니다.");});

        String encodedPassword = passwordEncoder.encode(password);

        String profileImageUrl = uploadUserImage.uploadUserImage(profileImage);

        User user = new User(email, encodedPassword, userNickname, phoneNumber, profileImageUrl);

        User savedUser = userRepository.save(user);

        return UserResponseDto.toDto(savedUser);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponseDto::toDto).toList();
    }

    public UserResponseDto findById(Long id) {

        User user = userRepository.findByIdOrElseThrow(id);

        return UserResponseDto.toDto(user);
    }

    @Transactional
    public UserResponseDto updateUserById(Long id, String password, String userNickname, String phoneNumber, MultipartFile profileImage) {
        String profileImageUrl = uploadUserImage.uploadUserImage(profileImage);

        User user = userRepository.findByIdOrElseThrow(id);

        user.updatePassword(passwordEncoder.encode(password));
        user.updateUserNickname(userNickname);
        user.updatePhoneNumber(phoneNumber);
        user.updateProfileImage(profileImageUrl);

        User savedUser = userRepository.save(user);

        return UserResponseDto.toDto(savedUser);
    }

    @Transactional
    public void delete(Long id, String password, Long userId) {
        accessWrongValid.AccessMisMatchId(id, userId);

        User user = userRepository.findByIdOrElseThrow(id);

//        if(passwordEncoder.matches(password, user.getPassword())){
//            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
//        }

        userRepository.delete(user);
    }

    public Resource getUserImage(Long id) {
        String imageUrl = userRepository.findByIdOrElseThrow(id).getProfileImage();

        return GetUserImage.getImage(imageUrl);
    }
}
