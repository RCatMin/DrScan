package com.drscan.web.Controller;

import com.drscan.web.primary.log.service.LogService;
import com.drscan.web.primary.permission.service.PermissionService;
import com.drscan.web.primary.users.domain.AuthUser;
import com.drscan.web.primary.users.domain.User;
import com.drscan.web.primary.users.domain.UserRequestDto;
import com.drscan.web.primary.users.service.UserService;
import com.drscan.web.primary.users.util.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users/action")
@RestController
public class UsersRestController {

    private final UserService userService;
    private final PermissionService permissionService;
    private final LogService logService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/list")
    public ResponseEntity<List<User>> findUserAll() {
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

        if (!otpKey.equals(code)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "인증코드가 일치하지 않습니다."));
        }

        String HashPassword = passwordEncoder.encode(password);

        User user = new User(username, HashPassword, hospital, department, name, email, phone, otpKey);

        String isSuccess = userService.createUser(user);

        permissionService.save(userService.findUserByUsername(username));

        if (!isSuccess.equals("success")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), isSuccess));
        }

        session.removeAttribute("authCode");
        session.invalidate();

        logService.saveLog(user, "회원가입");

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "회원가입 요청이 완료되었습니다."));
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> signin(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request) {
        User user = userService.findUserByUsername(userRequestDto.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "아이디 혹은 비밀번호가 일치하지 않습니다."));
        }

        if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            if (user.getFailCount() < 5) {
                userService.incrementFailCountAndCheckSuspension(userRequestDto);
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                        .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "아이디 혹은 비밀번호가 일치하지 않습니다."));
            } else {
                userRequestDto.setStatus("suspended");
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                        .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "로그인 5회 실패로 계정이 정지됐습니다."));
            }
        }

        if (user.getStatus().equals("suspended")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "정지된 계정입니다."));
        }

        if(user.getAccountType().equals("temporary") && user.getStatus().equals("pending")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "가입승인 대기 중인 계정입니다."));
        }

        String otpCode = userRequestDto.getCode();

        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("authCode");

        if (!otpCode.equals(code)) {
            if (user.getFailCount() < 5) {
                userService.incrementFailCountAndCheckSuspension(userRequestDto);
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST.value())
                        .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "인증코드가 일치하지 않습니다."));
            } else {
                userRequestDto.setStatus("suspended");
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                        .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "로그인 5회 실패로 계정이 정지됐습니다."));
            }
        }

        userService.resetFailCount(userRequestDto);
        session = request.getSession();

        AuthUser authUser = new AuthUser(user.getUserCode(), user.getAccountType(), user.getUsername(), user.getHospitalName(),
                user.getDepartment(), user.getName(), user.getEmail(), user.getPhone(), user.getOtpKey(), user.getStatus(), user.getFailCount());

        logService.saveLog(authUser, "로그인");
        session.setAttribute("authUser", authUser);

        return ResponseEntity.status(HttpStatus.OK.value()).body(new ResponseDto(HttpStatus.OK.value(), "로그인 성공!"));
    }

    @GetMapping("/signout")
    public RedirectView signout(HttpSession session) {
        AuthUser user = (AuthUser) session.getAttribute("authUser");

        session.removeAttribute("authUser");
        session.invalidate();

        logService.saveLog(user, "로그아웃");

        return new RedirectView("/");
    }

    @PostMapping("/checkDuplication-phone")
    public ResponseEntity<ResponseDto> checkDuplicationPhone(@RequestBody UserRequestDto userRequestDto) {
        String code = userRequestDto.getCode();
        String phone = userRequestDto.getPhone();

        boolean check = userService.existsByPhoneAndUserCodeNot(phone, code);

        if (check) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "전화번호가 중복됩니다."));
        }
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "사용가능한 전화번호입니다"));
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseDto> edit(@RequestBody UserRequestDto userRequestDto, HttpSession session, HttpServletRequest request) {
        boolean isSuccess = userService.updateUser2(userRequestDto, request);
        AuthUser authUser = (AuthUser) session.getAttribute("authUser");

        if (!isSuccess)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "회원정보 수정에 실패하였습니다."));

        logService.saveLog(authUser, "회원정보수정");

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "회원정보가 성공적으로 수정되었습니다."));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ResponseDto> withdraw(@RequestBody UserRequestDto userRequestDto) {
        User user = userService.findUserByUserCode(userRequestDto.getCode());

        if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "탈퇴신청에 실패하였습니다."));
        }

        boolean isSuccess = userService.updateUser3(user);

        if (!isSuccess)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST.value())
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "탈퇴신청에 실패하였습니다."));

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "탈퇴신청을 성공적으로 수정되었습니다."));
    }
}
