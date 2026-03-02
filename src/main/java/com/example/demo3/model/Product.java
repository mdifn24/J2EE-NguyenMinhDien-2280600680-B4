package com.example.demo3.model;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 200, message = "Tên tối đa 200 ký tự")
    private String name;

    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá phải lớn hơn 0")
    @Max(value = 9999999, message = "Giá tối đa là 9,999,999")
    private Long price;

    private String image;

    @NotBlank(message = "Phải chọn loại")
    private String category;
}