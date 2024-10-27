package spring.backend.wedding_invitation.Config;

import spring.backend.wedding_invitation.Domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/*
   Spring Security 에서 인증에 사용하기 위한 User 정보를 담은 객체
   User에 Authority가 추가된 CustomUser Class
 */
public class CustomUserDetails implements UserDetails {
    private final User user;

    // Constructor
    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }

    public Long getId() {
        return user.getId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getContact() {
        return user.getContact();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 계정이 만료되지 않았는지 확인, true 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않았는지 확인, true 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자의 자격증명이 만료되지 않았는지 반환, true 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자의 계정이 활성화 되어있는지 반환, true 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}
