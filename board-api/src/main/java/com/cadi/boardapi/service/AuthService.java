package com.cadi.boardapi.service;

import com.cadi.boardapi.mapper.UserMapper;
import com.cadi.boardapi.model.DefaultRes;
import com.cadi.boardapi.model.LoginReq;
import com.cadi.boardapi.dto.User;
import com.cadi.boardapi.model.SignUpReq;
import com.cadi.boardapi.util.PasswordUtil;
import com.cadi.boardapi.util.ResponseMessage;
import com.cadi.boardapi.util.StatusCode;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;

    private final JwtService jwtService;

    // 런타임시 mapper DI
    public AuthService(final UserMapper userMapper, JwtService jwtService) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    /**
     * 로그인 서비스
     * @param loginReq 로그인 객체
     * @return DefaultRes
     */

    public DefaultRes<JwtService.TokenRes> login (final LoginReq loginReq) {
        final User user = userMapper.findByNameAndPassword(loginReq.getId(), loginReq.getPassword());
        if(user != null) {
            // 토큰 생성
            final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.createJwtToken(user.getUserIdx()));
            return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
    }

    /**
     * 회원가입
     * @param signUpReq 회원가입 객체
     * @return DefaultRes
     */

    public DefaultRes signUp (final SignUpReq signUpReq) {
        // 비밀번호 확인
        if(!signUpReq.getPassword().equals(signUpReq.getConfirmPw())) {
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.CONFIRM_USER_PW);
        }

        // pw 암호화
        PasswordUtil util = new PasswordUtil();
        signUpReq.setPassword(util.encryptSHA256(signUpReq.getPassword()));
        // db create
        userMapper.signUp(signUpReq.getId(), signUpReq.getPassword(), signUpReq.getName(), signUpReq.getEmail(), signUpReq.getPhone());
        return DefaultRes.res(StatusCode.OK, ResponseMessage.CREATED_USER);
    }
}
