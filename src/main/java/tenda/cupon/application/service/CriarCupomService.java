package tenda.cupon.application.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tenda.cupon.application.contracts.CriarCupomUseCase;
import tenda.cupon.domain.model.Cupom;
import tenda.cupon.domain.repository.CupomRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriarCupomService implements CriarCupomUseCase {

    private final CupomRepository cupomRepository;

    @Override
    @Transactional
    public Cupom criar(Cupom cupom) {
        log.info("Criando cupom {}", cupom.getCodigo());
        return cupomRepository.salvar(cupom);
    }

}
