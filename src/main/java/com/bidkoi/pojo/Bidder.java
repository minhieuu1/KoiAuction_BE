package com.bidkoi.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bidder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String firstname;
    String lastname;
    String gender;
    String phone;
    String email;
    String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date birthday;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    Account account;
}
