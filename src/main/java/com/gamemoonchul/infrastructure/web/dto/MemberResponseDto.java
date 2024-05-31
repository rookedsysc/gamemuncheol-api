package com.gamemoonchul.infrastructure.web.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberResponseDto {
    private String name;
    private String nickname;
    private String email;
    private String picture;
    private boolean privacyAgreed;
    private Double score;
}
