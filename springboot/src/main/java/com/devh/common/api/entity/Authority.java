package com.devh.common.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;

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
public class Authority implements GrantedAuthority {
    
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;
    @Id
    @Column(length = 4, nullable = false, unique = true)
    private String code;

    @Column(length = 30, nullable = false, unique = true)
    private String auth;

    @Column(length = 30, nullable = false, unique = true)
    private String name;

    @Column(length = 500, nullable = true)
    @ColumnDefault("NULL")
    private String description;

    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY)
    // @OneToMany(mappedBy = "authority", fetch = FetchType.EAGER)
    @ToString.Exclude
    // @JoinColumn(name="id", insertable = false, updatable = false)
    private final List<WebUserAuthority> webUserAuthorities = new ArrayList<>();

    @Override
    public String getAuthority() {
        return "ROLE_"+auth.toUpperCase();
    }
}
