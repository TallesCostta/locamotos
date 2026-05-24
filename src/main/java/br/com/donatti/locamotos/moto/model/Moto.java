package br.com.donatti.locamotos.moto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "moto", schema = "public")
@Schema(description = "Entidade que representa uma Moto no sistema de locação")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da moto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Modelo da moto", example = "Honda CG 160 Titan", requiredMode = Schema.RequiredMode.REQUIRED)
    private String modelo;

    @Schema(description = "Placa da moto (deve ser única)", example = "ABC-1234", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(unique = true)
    private String placa;

    @Schema(description = "Cilindradas do motor da moto", example = "160")
    private int cilindrada;

    @Schema(description = "Status de disponibilidade para locação", example = "true")
    private boolean disponivel;
}