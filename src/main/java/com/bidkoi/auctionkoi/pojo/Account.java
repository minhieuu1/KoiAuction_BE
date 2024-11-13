package com.bidkoi.auctionkoi.pojo;


import com.bidkoi.auctionkoi.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "AccountID")
    String id;
    String username;
    @JsonIgnore
    String password;
    String email;
    @Column(name = "Phone_number")
    String phone;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column(name = "Fcm_Token")
    String fcmToken;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // getAuthorities: lấy danh sách quyền
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (this.role != null) {
            authorities.add(new SimpleGrantedAuthority(this.role.toString()));
        }
        return authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }



}
