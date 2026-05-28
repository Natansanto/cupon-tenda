package tenda.cupon.infrastructure.api.cupom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tenda.cupon.application.contracts.CriarCupomUseCase;
import tenda.cupon.infrastructure.api.cupom.CupomApi;
import tenda.cupon.infrastructure.api.cupom.controller.post.request.RequestCriarCupomDTO;
import tenda.cupon.infrastructure.api.cupom.controller.post.response.ResponseCriarCupomDTO;
import tenda.cupon.infrastructure.api.cupom.mapper.CupomMapper;

@RestController
@RequiredArgsConstructor
public class CupomController implements CupomApi {

	private final CriarCupomUseCase criarCupomUseCase;
	private final CupomMapper cupomMapper;

	@Override
	public ResponseEntity<ResponseCriarCupomDTO> criar(@Valid RequestCriarCupomDTO requisicao) {
		var cupom = criarCupomUseCase.criar(cupomMapper.fromRequest(requisicao));
		return ResponseEntity.status(HttpStatus.CREATED).body(cupomMapper.toResponse(cupom));
	}

}
