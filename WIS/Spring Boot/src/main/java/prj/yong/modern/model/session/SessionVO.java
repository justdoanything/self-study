package prj.yong.modern.model.session;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// @Getter
// @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionVO {
    //    @Builder.Default

    private String sessionId;
    private String accessToken;
    private String refreshToken;
    /**
     * Session에 담을 필드들
     * id, name, token, uuid, email, nickname, role, profile 등
     */
    private String memberId;

    private String memberInfo;
    private String memberToken;
}
