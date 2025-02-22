package com.drscan.web.primary.users.domain;

import com.drscan.web.primary.users.util.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto extends ResponseDto {

    private long code;
    private String username;
    private String nickname;
    private Timestamp regDate;
    private Timestamp modDate;

    public UserResponseDto(User user) {
        super.setStatusCode(HttpStatus.OK.value());
        super.setMessage("회원정보가 성공적으로 조회되었습니다.");
        this.code = user.getUserCode();
        this.username = user.getUsername();
        this.regDate = user.getRegDate();
        this.modDate = user.getModDate();
    }

}