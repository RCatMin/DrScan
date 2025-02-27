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

@RequiredArgsConstructor
@RequestMapping("/users/action")
@RestController
public class UsersRestController {

    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<List<User>> findUserAll(){
        return ResponseEntity.ok(userService.findUserAll());
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody UserRequestDto userDto, HttpServletRequest request) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        String hospital = userDto.getHospital();
        String department = userDto.getDepartment();
        String name = userDto.getName();
        String email = userDto.getEmail();
        String phone = userDto.getPhone();
        String otpKey = userDto.getCode();

        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("authCode");

        if(!otpKey.equals(code)){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "인증코드가 일치하지 않습니다."));
        }

        User user = new User(username, password, hospital, department, name, email, phone, otpKey);

        String isSuccess = userService.createUser(user);

        if(!isSuccess.equals("success")){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), isSuccess));
        }

        session.removeAttribute("authCode");
        session.invalidate();

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "회원가입 요청이 완료되었습니다."));
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> signin(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request) {
        User user = userService.findUserByUsername(userRequestDto.getUsername());

        if(user == null || !userRequestDto.getPassword().equals(user.getPassword())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "아이디 혹은 비밀번호가 일치하지 않습니다."));
        }

        String otpCode = userRequestDto.getCode();

        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("authCode");

        if(!otpCode.equals(code)){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "인증코드가 일치하지 않습니다."));
        }

        session = request.getSession();
        session.setAttribute("authUser", user);

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "로그인에 성공했습니다."));
    }

    @DeleteMapping("/signout")
    public ResponseEntity<ResponseDto> signout(HttpSession session) {
        session.removeAttribute("authUser");
        session.invalidate();

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "로그아웃 완료"));
    }

    @PostMapping("/checkDuplication-phone")
    public ResponseEntity<ResponseDto> checkDuplicationPhone(@RequestBody UserRequestDto userRequestDto) {
        String code = userRequestDto.getCode();
        String phone = userRequestDto.getPhone();

        boolean check = userService.existsByPhoneAndUserCodeNot(phone, code);

        if(check) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "전화번호가 중복됩니다."));
        }
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "사용가능한 전화번호입니다"));
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseDto> edit(@RequestBody UserRequestDto userRequestDto) {
        boolean isSuccess = userService.updateUser2(userRequestDto);

        if(!isSuccess)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "회원정보 수정에 실패하였습니다."));

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "회원정보가 성공적으로 수정되었습니다."));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ResponseDto> withdraw(@RequestBody UserRequestDto userRequestDto) {
        User user = userService.findUserByUserCode(userRequestDto.getCode());

        if(!user.getPassword().equals(userRequestDto.getPassword()))
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "탈퇴신청에 실패하였습니다."));

        boolean isSuccess = userService.updateUser3(user);

        if(!isSuccess)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "탈퇴신청에 실패하였습니다."));

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "탈퇴신청을 성공적으로 수정되었습니다."));
    }
}
