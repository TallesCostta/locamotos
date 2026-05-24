package br.com.donatti.locamotos.moto.controller;

import br.com.donatti.locamotos.moto.model.Moto;
import br.com.donatti.locamotos.moto.service.MotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motos")
@RequiredArgsConstructor
@Tag(name = "Motos", description = "Endpoints para gerenciamento de motos")
class MotoController {
    private final MotoService motoService;

    @Operation(summary = "Listar todas as motos", description = "Retorna uma lista contendo todas as motos cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    List<Moto> listarTodasMotos() {
        return motoService.listarTodasMotos();
    }

    @Operation(summary = "Buscar moto por ID", description = "Retorna os detalhes de uma moto específica através do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Moto encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    })
    @GetMapping("/{id}")
    ResponseEntity<Moto> buscarMotoPorId(
            @Parameter(description = "ID da moto que será buscada") @PathVariable @NotNull final Long id) {
        return motoService.buscarMotoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @Operation(summary = "Buscar moto por modelo", description = "Retorna uma lista de motos baseada em um termo de busca no modelo.")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    @GetMapping("/modelo/{modelo}")
    List<Moto> buscarMotoPorModelo(
            @Parameter(description = "Termo para busca no modelo") @PathVariable @NotEmpty final String modelo) {
        return motoService.buscarMotoPorModelo(modelo);
    }

    @Operation(summary = "Contar inventário de motos", description = "Retorna a quantidade total de motos cadastradas no sistema.")
    @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    @GetMapping("/inventario")
    long contarMotosInventario() {
        return motoService.contarMotosInventario();
    }

    @Operation(summary = "Cadastrar uma nova moto", description = "Salva uma nova moto no sistema. Valida se a placa já existe e se os campos obrigatórios estão preenchidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Moto salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou placa já cadastrada")
    })
    @PostMapping
    Moto salvarMoto(@RequestBody @NotNull final Moto moto) {
        return motoService.salvarMoto(moto);
    }

    @Operation(summary = "Atualizar moto por ID", description = "Atualiza os dados de uma moto existente no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Moto atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada para atualização")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizarMoto(@PathVariable Long id, @RequestBody Moto motoAtualizada) {
        return motoService.buscarMotoPorId(id)
                .map(motoExistente -> {
                    motoExistente.setModelo(motoAtualizada.getModelo());
                    motoExistente.setPlaca(motoAtualizada.getPlaca());
                    motoExistente.setCilindrada(motoAtualizada.getCilindrada());
                    motoExistente.setDisponivel(motoAtualizada.isDisponivel());
                    Moto salvo = motoService.salvarMoto(motoExistente);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar moto por ID", description = "Remove uma moto do sistema através do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Moto deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada para deleção")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarMotoPorId(@Parameter(description = "ID da moto que será deletada") @PathVariable @NotNull final Long id) {
        if (!motoService.buscarMotoPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        motoService.deletarMoto(id);
        return ResponseEntity.noContent().build();
    }
}