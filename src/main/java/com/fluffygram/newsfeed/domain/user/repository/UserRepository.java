package com.fluffygram.newsfeed.domain.user.repository;

import com.fluffygram.newsfeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()-> new RuntimeException("[id = " + id + "] 에 해당하는 유저가 존재하지 않습니다."));
    }

}
