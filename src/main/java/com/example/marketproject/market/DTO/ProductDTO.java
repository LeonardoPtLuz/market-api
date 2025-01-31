package com.example.marketproject.market.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "O nome do produto é necessário!")
    private String name;

    @NotBlank(message = "A descrição do produto é necessária!")
    private String description;

    @NotNull(message = "É necessário informar o preço!")
    @Min(value = 0, message = "O valor preço deve ser <= 0")
    private Double price;

    @NotNull(message = "É necessário informar quantidade do produto em estoque!")
    @Min(value = 0, message = "O estoque deve ser <= 0")
    private Integer stock;

}
