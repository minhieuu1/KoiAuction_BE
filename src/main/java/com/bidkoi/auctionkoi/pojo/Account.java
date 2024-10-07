package com.bidkoi.auctionkoi.pojo;

import com.bidkoi.auctionkoi.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "AccountID")
    String id;
    String username;
    String password;
    String email;
    @Column(name = "Phone_number")
    String phone;
    int role = 0;

    public Role getRole() {
        return Role.fromValue(role);
    }

    public void setRole(Role role) {
        this.role = role.getValue();
    }
}
