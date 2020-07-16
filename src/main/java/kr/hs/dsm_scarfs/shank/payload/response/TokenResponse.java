package kr.hs.dsm_scarfs.shank.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;
}
