package tenda.cupon.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import tenda.cupon.domain.model.Cupom;
import tenda.cupon.domain.repository.CupomRepository;
import tenda.cupon.infrastructure.persistence.mapper.CupomEntityMapper;
import tenda.cupon.infrastructure.persistence.repository.JpaCupomRepository;

@Repository
@RequiredArgsConstructor
public class CupomRepositoryImpl implements CupomRepository {

	private final JpaCupomRepository jpaCupomRepository;
	private final CupomEntityMapper cupomEntityMapper;

	@Override
	public Cupom salvar(Cupom cupom) {
		var entidade = cupomEntityMapper.toEntity(cupom);
		var salva = jpaCupomRepository.save(entidade);
		return cupomEntityMapper.toDomain(salva);
	}

	@Override
	public Optional<Cupom> buscarPorId(UUID id) {
		return jpaCupomRepository.findById(id).map(cupomEntityMapper::toDomain);
	}

}
