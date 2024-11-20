package com.fluffygram.newsfeed.domain.user.service;

import com.fluffygram.newsfeed.domain.base.Valid.AccessWrongValid;
import com.fluffygram.newsfeed.domain.user.dto.UserResponseDto;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.config.Const;
import com.fluffygram.newsfeed.global.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UploadUserImage uploadUserImage;
    private final PasswordEncoder passwordEncoder;
    private final AccessWrongValid accessWrongValid;

    public UserResponseDto signUp(String email,String password, String userNickname, String phoneNumber, MultipartFile profileImage) {
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
    public UserResponseDto updateUserById(Long id, String password, String userNickname, String phoneNumber, String profileImage) {
        User user = userRepository.findByIdOrElseThrow(id);

        user.updatePassword(passwordEncoder.encode(password));
        user.updateUserNickname(userNickname);
        user.updatePhoneNumber(phoneNumber);
        user.updateProfileImage(profileImage);

        User savedUser = userRepository.save(user);

        return UserResponseDto.toDto(savedUser);
    }

    @Transactional
    public void delete(Long id, String password, Long userId) {
        accessWrongValid.AccessMisMatchId(id, userId);

        User user = userRepository.findByIdOrElseThrow(id);

        if(passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }

    public Resource getUserImage(Long id) {
        Resource resource;
        String imageUrl = userRepository.findByIdOrElseThrow(id).getProfileImage();

        try {
            // 파일 경로 생성
            Path filePath = Const.IMAGE_STORAGE_PATH.resolve(imageUrl).normalize();

            // 파일을 Resource 객체로 변환
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("파일이 없습니다.");
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return resource;
    }
}
