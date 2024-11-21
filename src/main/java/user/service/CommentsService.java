package user.service;

import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import user.dto.CommentsResponseDto;
import user.dto.CommentsWithUsernameResponseDto;
import user.entity.Comments;
import user.repository.CommentsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;

    //댓글 생성
    public CommentsResponseDto save(long boardId, long userId, String comment){
        User findUser
    }

    //댓글 전체 조회
    public List<CommentsResponseDto> findAllComments(){
        return CommentsRepository.findAll()
                .stream()
                .map(CommentsResponseDto::toDto)
                .toList();
    }

    //댓글 ID로 특정 일정 조회
    public CommentsWithUsernameResponseDto findCommentsById(Long id){
        Comments findComments = CommentsRepository.findCommentsByIdOrElseThrow(id);
    }
}
