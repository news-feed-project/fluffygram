package com.fluffygram.newsfeed.domain.user.repository;

import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()-> new BusinessException(ExceptionType.USER_NOT_FOUND));
    }

    Optional<User> findByEmail(String email);

    default User findUserByEmailOrElseThrow(String email){
        return findByEmail(email).orElseThrow(()-> new BusinessException(ExceptionType.USER_NOT_FOUND));
    }

    Boolean existsByEmail(String email);


}
