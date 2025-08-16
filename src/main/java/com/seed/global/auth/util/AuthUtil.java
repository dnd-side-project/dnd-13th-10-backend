package com.seed.global.auth.util;

import com.seed.domain.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    
    /**
     * 현재 인증된 사용자 정보를 반환
     * @return 현재 로그인한 User 엔티티
     */
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증되지 않은 사용자입니다.");
        }
        
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            throw new IllegalStateException("잘못된 인증 정보입니다.");
        }
        
        return (User) principal;
    }
    
    /**
     * 현재 인증된 사용자의 socialId 반환
     * @return socialId
     */
    public static String getCurrentSocialId() {
        return getCurrentUser().getSocialId();
    }
    
    /**
     * 현재 인증된 사용자의 이메일 반환
     * @return email
     */
    public static String getCurrentEmail() {
        return getCurrentUser().getEmail();
    }
    
    /**
     * 현재 인증된 사용자의 이름 반환
     * @return name
     */
    public static String getCurrentName() {
        return getCurrentUser().getName();
    }
    
    /**
     * 현재 인증된 사용자의 ID 반환
     * @return user ID
     */
    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}