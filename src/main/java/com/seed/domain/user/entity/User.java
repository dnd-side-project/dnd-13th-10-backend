package com.seed.domain.user.entity;

import com.seed.domain.schedule.entity.Schedule;
import com.seed.global.entity.BaseEntity;
import com.seed.domain.user.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private String phoneNumber;

    private String socialId;

    private String email;

    private String name;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }
    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isUse = true;

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return socialId;
    }
    public static User ofId(String userId) {
        return User.builder().id(Long.parseLong(userId)).build();
    }
}
