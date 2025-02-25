package com.drscan.web.Controller;

import com.drscan.web.primary.users.domain.User;
import com.drscan.web.primary.users.service.UserService;
import com.drscan.web.primary.users.util.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
