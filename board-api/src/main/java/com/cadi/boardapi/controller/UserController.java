package com.cadi.boardapi.controller;

import com.cadi.boardapi.model.DefaultRes;
import com.cadi.boardapi.model.LoginReq;
import com.cadi.boardapi.model.SignUpReq;
import com.cadi.boardapi.service.AuthService;
import com.cadi.boardapi.util.Auth;
import com.cadi.boardapi.util.ResponseMessage;
import com.cadi.boardapi.util.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    /**
    * 로그인
    * @param loginReq 객체
     * @return ResponseEntity
     */
    @PostMapping("login")
    public ResponseEntity login(@RequestBody final LoginReq loginReq) {
        try {
            return new ResponseEntity(authService.login(loginReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("signup")
    public ResponseEntity signUp(@RequestBody final SignUpReq signUpReq) {
        try {
            return new ResponseEntity(authService.signUp(signUpReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
