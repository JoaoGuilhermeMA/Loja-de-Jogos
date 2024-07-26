package br.com.loja.jogos.lojajogos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "O nome do jogo não pode estar em branco")
    @Size(max = 100, message = "O nome do jogo não pode ter mais que 100 caracteres")
    private String nome;

    @NotNull(message = "A data de lançamento não pode ser nula")
    private LocalDate dataLancamento;

    @NotBlank(message = "O URI da imagem não pode estar em branco")
    private String imageUri;

    @NotNull(message = "O preço não pode ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotBlank(message = "O gênero não pode estar em branco")
    private String genero;

    @NotBlank(message = "O desenvolvedor não pode estar em branco")
    private String desenvolvedor;

    @NotNull(message = "O campo isDeleted não pode ser nulo")
    private Boolean isDeleted = false;
}
