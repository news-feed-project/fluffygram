package user.dto;

import lombok.Getter;

@Getter
public class UpdateCommentsRequestDto {
    private final String comment;

    public UpdateCommentsRequestDto(String comment) {
        this.comment = comment;
    }
}
