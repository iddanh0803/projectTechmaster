package com.example.bookinghotel.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpsertBlogRequest {
    @NotNull(message = "Tiêu đề không được để trống")
    @NotEmpty(message = "Tiêu đề không được để trống")
    private String title;

    @NotNull(message = "Mô tả không được để trống")
    @NotEmpty(message = "Mô tả không được để trống")
    private String description;

    @NotNull(message = "Nội dung không được để trống")
    @NotEmpty(message = "Nội dung không được để trống")
    private String content;
    private String thumbnail;
    private Boolean status;
}
