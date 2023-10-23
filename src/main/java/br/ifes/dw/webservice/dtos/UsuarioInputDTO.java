package br.ifes.dw.webservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record UsuarioInputDTO(@NotBlank String nome, @NotBlank String dataDeNascimento, EnderecoInputDTO endereco) {
}
