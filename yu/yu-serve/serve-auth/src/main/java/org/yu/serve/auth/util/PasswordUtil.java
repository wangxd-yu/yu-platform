package org.yu.serve.auth.util;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wangxd
 * @date 2020-11-05 15:08
 */
public class PasswordUtil {
    final private static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static String encodedPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
