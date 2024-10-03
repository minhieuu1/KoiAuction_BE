package com.bidkoi.auctionkoi.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePasswordRequest {
    @NotBlank(message = "Current password is required.")
    String currentPassword;
    @NotBlank(message = "New password is required.")
    String newPassword;
}
