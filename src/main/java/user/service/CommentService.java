package user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.dto.CommentsResponseDto;
import user.dto.CommentsWithUsernameResponseDto;
import user.dto.UpdateCommentsRequestDto;
import user.repository.CommentsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;
    

    //댓글 생성
    public CommentsResponseDto createComment(long boardId, long userId,String comment) {

    }

    public List<CommentsResponseDto> findAllComments() {
    }

    public CommentsWithUsernameResponseDto findCommentsById(Long id) {
    }

    public void deleteComment(Long id) {
    }
    public  void UpdateComments(String comment){
    }
}