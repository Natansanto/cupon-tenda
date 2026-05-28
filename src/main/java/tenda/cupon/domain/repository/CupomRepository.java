package tenda.cupon.domain.repository;

import java.util.Optional;
import java.util.UUID;

import tenda.cupon.domain.model.Cupom;

public interface CupomRepository {

	Cupom salvar(Cupom cupom);

	Optional<Cupom> buscarPorId(UUID id);

}
