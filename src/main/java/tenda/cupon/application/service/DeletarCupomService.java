package tenda.cupon.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tenda.cupon.application.contracts.DeletarCupomUseCase;
import tenda.cupon.domain.exception.CupomNaoEncontradoException;
import tenda.cupon.domain.repository.CupomRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeletarCupomService implements DeletarCupomUseCase {

	private final CupomRepository cupomRepository;

	@Override
	@Transactional
	public void deletar(UUID id) {
		var cupom = cupomRepository.buscarPorId(id)
				.orElseThrow(() -> new CupomNaoEncontradoException("Cupom não encontrado"));

		cupom.deletar();
		cupomRepository.salvar(cupom);

		log.info("Cupom {} deletado logicamente", id);
	}

}
