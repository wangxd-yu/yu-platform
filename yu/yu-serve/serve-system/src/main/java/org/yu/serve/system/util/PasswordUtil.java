package org.yu.serve.system.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wangxd
 * @date 2020-11-05 15:08
 */
public class PasswordUtil {
    //final private static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    final private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static String encodedPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
