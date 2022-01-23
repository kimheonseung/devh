package com.devh.common.api.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WebUser implements UserDetails{
    @Id
    @Column(length = 16)
    private String id;

    @Column(length = 20, unique = true, nullable = false)
    private String userId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 500, nullable = true)
    @ColumnDefault("NULL")
    private String email;

    @Column(length = 13, nullable = true)
    @ColumnDefault("NULL")
    private String phone;

    @Column(nullable = true)
    @ColumnDefault("NULL")
    private LocalDateTime loginAt;

    @Column(length = 15, nullable = true)
    @ColumnDefault("NULL")
    private String loginIp;

    @Column(nullable = true)
    @ColumnDefault("0")
    private int loginFailCount;

    @Column(nullable = true)
    @ColumnDefault("NULL")
    private LocalDateTime loginFailedAt;

    @Column(length = 15, nullable = true)
    @ColumnDefault("NULL")
    private String loginFailIp;

    @Column(nullable = true)
    @ColumnDefault("NOW()")
    private LocalDateTime passwordChangedAt;

    @Column(length = 15, nullable = false)
    @ColumnDefault("'*.*.*.*'")
    private String accessIp;

    @Column(length = 500, nullable = true)
    @ColumnDefault("NULL")
    private String description;

    // @OneToMany(mappedBy = "webUser", fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "webUser", fetch = FetchType.EAGER)
    @ToString.Exclude
    // @JoinColumn(name="web_user_id")
    private final List<WebUserAuthority> webUserAuthorities = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof WebUser) {
            WebUser objWebUser = (WebUser) obj;
            return this.id.equals(objWebUser.userId);
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> authorities = new ArrayList<>();
        webUserAuthorities.forEach(webUserAuthority -> {
            authorities.add(webUserAuthority.getAuthority());
        });
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        // password change
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // login fail count
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // last password change
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonExpired() && isAccountNonLocked() && isCredentialsNonExpired();
    }
}
