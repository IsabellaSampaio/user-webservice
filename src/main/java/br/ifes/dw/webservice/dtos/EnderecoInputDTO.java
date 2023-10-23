package br.ifes.dw.webservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoInputDTO(@NotBlank String logradouro, @NotBlank String bairro, @NotBlank String cidade, @NotBlank String estado, @NotNull int numero) {
}
