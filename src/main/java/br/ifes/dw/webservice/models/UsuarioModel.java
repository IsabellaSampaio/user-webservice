package br.ifes.dw.webservice.models;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "USUARIOS")
@Data
public class UsuarioModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false, unique = false)
    private String nome;

    @Column(nullable = false, unique = false)
    private String dataDeNascimento;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "endereco_id")
    private EnderecoModel endereco;

}
