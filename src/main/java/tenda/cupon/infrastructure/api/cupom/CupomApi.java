package tenda.cupon.infrastructure.api.cupom;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import tenda.cupon.infrastructure.api.cupom.controller.post.request.RequestCriarCupomDTO;
import tenda.cupon.infrastructure.api.cupom.controller.post.response.ResponseCriarCupomDTO;

@Tag(name = "Cupons", description = "Operações de cupons")
@RequestMapping("/cupom")
public interface CupomApi {

	@PostMapping
	@Operation(
			summary = "Criar cupom",
			description = "Cadastra um novo cupom com código sanitizado e status ATIVO",
			responses = {
					@ApiResponse(responseCode = "201", description = "Cupom criado com sucesso", useReturnTypeSchema = true),
					@ApiResponse(responseCode = "400", description = "Requisição inválida") })
	ResponseEntity<ResponseCriarCupomDTO> criar(@RequestBody @Valid RequestCriarCupomDTO requisicao);

	@DeleteMapping("/{id}")
	@Operation(
			summary = "Deletar cupom",
			description = "Realiza exclusao do cupom",
			responses = {
					@ApiResponse(responseCode = "204", description = "Cupom deletado com sucesso"),
					@ApiResponse(responseCode = "400", description = "Cupom ja deletado"),
					@ApiResponse(responseCode = "404", description = "Cupom nao encontrado") })
	ResponseEntity<Void> deletar(@PathVariable UUID id);

}
