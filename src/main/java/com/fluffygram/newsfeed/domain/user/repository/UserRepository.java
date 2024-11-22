package com.fluffygram.newsfeed.domain.user.repository;

import com.fluffygram.newsfeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    static User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()-> new RuntimeException("[id = " + id + "] 에 해당하는 유저가 존재하지 않습니다."));
    }

    Optional<User> findByEmail(String email);

    default User findUserByEmailOrElseThrow(String email){
        return findByEmail(email).orElseThrow(()-> new RuntimeException("[email = " + email + "] 에 해당하는 유저가 존재하지 않습니다."));
    }
}
