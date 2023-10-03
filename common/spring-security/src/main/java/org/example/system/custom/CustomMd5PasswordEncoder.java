package org.example.system.custom;

/**
 * Created by 27 on 2023/10/3
 */
/*
自定义md5加密器
 */

import org.example.common.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 密码处理
 * </p>
 *
 */
@Component
public class CustomMd5PasswordEncoder implements PasswordEncoder {

    @Override
    // 编码
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    @Override
    // 匹配
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }
}
