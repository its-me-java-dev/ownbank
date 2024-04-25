package com.ownbank.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserLoginResponseModel {
    private String jwtToken;
    private String userName;
}
