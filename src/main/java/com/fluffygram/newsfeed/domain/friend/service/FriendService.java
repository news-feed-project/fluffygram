package com.fluffygram.newsfeed.domain.friend.service;

import com.fluffygram.newsfeed.domain.base.enums.UserStatus;
import com.fluffygram.newsfeed.domain.friend.dto.FriendResponseDto;
import com.fluffygram.newsfeed.domain.friend.entity.Friend;
import com.fluffygram.newsfeed.domain.friend.repository.FriendRepository;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.NotFountByIdException;
import com.fluffygram.newsfeed.global.exception.NotMatchByUserIdException;
import com.fluffygram.newsfeed.global.exception.WrongAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    /**
     * 친구 요청 보내기
     *
     * @param loginUserId       요청을 보낼 사용자 ID
     * @param receivedUserId   요청을 받을 사용자 ID
     *
     */
    public void sendFriendRequest(long loginUserId, long receivedUserId) {

        // 로그인되어있는 유저ID와 친구요청받는 유저ID가 같을때 예외처리.
        if (loginUserId == receivedUserId) {
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }

        User sendUser = userRepository.findByIdOrElseThrow(loginUserId);
        User receivedUser = userRepository.findByIdOrElseThrow(receivedUserId);

        // 탈퇴한 유저 여부 확인
        if(receivedUser.getUserStatus().equals(UserStatus.DELETE)){
            throw new WrongAccessException(ExceptionType.DELETED_USER);
        }

        // 중복 여부 확인
        // ID : 1 -> ID : 2  신청한 후 ID : 2 -> ID : 1 도 신청이 되는 현상 발견 후, 넣은 로직.
        boolean isDuplicate = friendRepository.existsBySendUserAndReceivedUser(sendUser, receivedUser)
                || friendRepository.existsBySendUserAndReceivedUser(receivedUser, sendUser);

        if (isDuplicate) {
            throw new NotMatchByUserIdException(ExceptionType.EXIST_USER);
        }

        Friend friend = new Friend(sendUser, receivedUser, Friend.FriendStatus.REQUESTED);

        // 대기상태로 DB 친구요청 저장.
        friendRepository.save(friend);
    }

    /**
     * 친구 요청 수락
     *
     * @param loginUserId       요청을 보낸 사용자 ID
     * @param receivedUserId   요청을 받은 사용자 ID
     *
     */
    @Transactional
    public void acceptFriendRequest(long loginUserId, long receivedUserId) {

        // 로그인되어있는 유저ID와 수락받는 유저ID가 같을때 예외처리.
        if (loginUserId == receivedUserId) {
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }

        // 친구수락은 요청받은 사람이 수락할수 있음.
        Friend friend = friendRepository.findFriendByReceivedUserIdAndSendUserIdOrThrow(loginUserId, receivedUserId);

        // 친구요청상태 ACCEPT 로 변경.
        friend.acceptFriendRequest();
    }

    /**
     * 친구 데이터베이스에서 삭제
     *
     * @param loginUserId       요청을 보낸 사용자 ID
     * @param receivedUserId   요청을 받은 사용자 ID
     *
     */
    @Transactional
    public void deleteFriend(Long loginUserId, long receivedUserId) {

        // 내가 나를 삭제할때 에러처리.
        if (loginUserId == receivedUserId) {
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }
        //아이디 1번이 -> 3번 요청함
        //로그인은 3번이 하고있음 receivedUserId : 1번 얘를 삭제하려고 함
        //isPresent 있으면 true 없으면 false
        if (friendRepository.findBySendUserIdAndReceivedUserId(loginUserId, receivedUserId).isPresent()) {
            Friend friend = friendRepository.findBySendUserIdAndReceivedUserIdOrThrow(loginUserId, receivedUserId);
            friendRepository.delete(friend);

        } else if (friendRepository.findBySendUserIdAndReceivedUserId(receivedUserId, loginUserId).isPresent()) {
            Friend friend = friendRepository.findBySendUserIdAndReceivedUserIdOrThrow(receivedUserId, loginUserId);
            friendRepository.delete(friend);
        } else {
            throw new NotFountByIdException(ExceptionType.FRIEND_NOT_FOUND);
        }

    }

    /**
     * 전체친구조회
     *
     * @param loginUserId       요청을 보낸 사용자 ID
     * @return List<FriendResponseDto>
     */
    public List<FriendResponseDto> findAllFriends(Long loginUserId) {

        // userId가 보낸 친구 요청 중 ACCEPTED 상태인 친구 목록 조회
        List<Friend> friends = friendRepository.findBySendUserAndFriendStatusOrThrow(loginUserId, Friend.FriendStatus.ACCEPTED);

        return friends.stream()
                .map(friend -> {
                    // 친구 요청의 상대방 정보를 userId 기준으로 가져옴
                    User friendUser = friend.getSendUser().getId().equals(loginUserId)
                            ? friend.getReceivedUser() // userId가 보낸 경우 상대는 receivedUser
                            : friend.getSendUser(); // userId가 받은 경우 상대는 sendUser
                    return new FriendResponseDto(friendUser);  // FriendResponseDto 객체 생성
                })
                .collect(Collectors.toList());  // 결과 리스트 반환
    }
}
