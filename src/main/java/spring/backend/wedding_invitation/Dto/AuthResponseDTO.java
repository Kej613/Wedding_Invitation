package spring.backend.wedding_invitation.Dto;

import spring.backend.wedding_invitation.Domain.Auth;
import lombok.*;

/*
   인증 요청에 대해 User 정보와 함께 Token 정보를 응답으로 반환
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String tokenType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public AuthResponseDTO(Auth entity) {
        this.tokenType = entity.getTokenType();
        this.accessToken = entity.getAccessToken();
        this.refreshToken = entity.getRefreshToken();
    }
}