package com.printpoisoning.bookfull.dto.response;
import lombok.Data;

@Data
public class RefreshTokenResDTO {
    private String accessToken;
    private String refreshToken;

}
