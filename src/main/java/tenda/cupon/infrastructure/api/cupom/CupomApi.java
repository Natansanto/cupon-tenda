package tenda.cupon.infrastructure.api.cupom;

import org.springframework.http.ResponseEntity;
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

}
