package tenda.cupon.infrastructure.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static tenda.cupon.test.util.CupomTestUtils.CODIGO_SANITIZADO;
import static tenda.cupon.test.util.CupomTestUtils.ID;
import static tenda.cupon.test.util.CupomTestUtils.cupomEntityResgatado;
import static tenda.cupon.test.util.CupomTestUtils.cupomRestauradoPublicado;

import org.junit.jupiter.api.Test;

import tenda.cupon.domain.enums.StatusCupom;
import tenda.cupon.domain.model.Cupom;
import tenda.cupon.infrastructure.persistence.entity.CupomEntity;

class CupomEntityMapperTest {

	private final CupomEntityMapper mapper = new CupomEntityMapperImpl();

	@Test
	void toEntity_deveMapearStatusComoString() {
		var cupom = cupomRestauradoPublicado();

		CupomEntity entidade = mapper.toEntity(cupom);

		assertThat(entidade.getStatus()).isEqualTo("ATIVO");
		assertThat(entidade.getCodigo()).isEqualTo(CODIGO_SANITIZADO);
		assertThat(entidade.isPublicado()).isTrue();
	}

	@Test
	void toDomain_deveRestaurarCupom() {
		var entidade = cupomEntityResgatado(ID);

		Cupom cupom = mapper.toDomain(entidade);

		assertThat(cupom.getId()).isEqualTo(ID);
		assertThat(cupom.getStatus()).isEqualTo(StatusCupom.ATIVO);
		assertThat(cupom.isResgatado()).isTrue();
	}

}
