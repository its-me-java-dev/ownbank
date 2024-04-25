package com.ownbank.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginRequestModel {
    private String email;
    private String password;

}
