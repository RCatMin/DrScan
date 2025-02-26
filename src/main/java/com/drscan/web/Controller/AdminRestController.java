package com.drscan.web.Controller;

import com.drscan.web.primary.users.domain.User;
import com.drscan.web.primary.users.domain.UserRequestDto;
import com.drscan.web.primary.users.service.UserService;
import com.drscan.web.primary.users.util.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminRestController {

    private final UserService userService;

    @GetMapping("/session")
    public ResponseEntity<ResponseDto> createSession(HttpServletRequest request) {
        List<User> userList = userService.findAllByAccountType("user");
        List<User> temporaryList = userService.findAllByAccountTypeAndStatus("temporary", "pending");
        List<User> deleteList = userService.findAllByAccountTypeAndStatus("user", "pending");

        HttpSession session = request.getSession();

        session.setAttribute("userList", userList);
        session.setAttribute("temporaryList", temporaryList);
        session.setAttribute("deleteList", deleteList);

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "세션 생성 성공!"));
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseDto> edit(@RequestBody UserRequestDto userRequestDto) {
        boolean isSuccess = userService.updateUser(userRequestDto);

        if(!isSuccess)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "회원정보 수정에 실패하였습니다."));

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "회원정보가 성공적으로 수정되었습니다."));
    }

    @PutMapping("/approve")
    public ResponseEntity<ResponseDto> approve(@RequestBody UserRequestDto userRequestDto) {
        boolean isSuccess = userService.approveUser(userRequestDto);

        if(!isSuccess)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "승인에 실패하였습니다."));

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "성공적으로 승인되었습니다."));
    }

    @PostMapping("/check")
    public ResponseEntity<ResponseDto> check(@RequestBody Map<String, String> request) {
        String type = request.get("type");
        String value = request.get("value");
        String code = request.get("code");

        User user = userService.findUserByUserCode(code);

        if(type.equals("username") && userService.existsByUsernameAndUserCodeNot(value, user.getUserCode())){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "ID가 중복됩니다."));
        } else if(type.equals("email") && userService.existsByEmailAndUserCodeNot(value, user.getUserCode())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "이메일이 중복됩니다."));
        }

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "성공적으로 승인되었습니다."));
    }
}
