package com.bidkoi.auctionkoi.pojo;

import com.bidkoi.auctionkoi.enums.KoiStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Koi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KoiID")
    Long koiId;
    Double length;
    String varieties;
    String age;
    String sex;
    String image;
    String video;
    String description;
    int method;
    @Column(name = "Initial_price")
    Double initialPrice;
    @Column(name = "Final_price")
    Double finalPrice;
    @Builder.Default
    int status = 0;
    @ManyToOne
    @JoinColumn(name = "BreederID",referencedColumnName = "BreederID")
    Breeder breeder;

    public KoiStatus getStatus() {
        return KoiStatus.fromValue(status);
    }

    public void setStatus(KoiStatus status) {
        this.status = status.getValue();
    }
}
