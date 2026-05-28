package tenda.cupon.infrastructure.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static tenda.cupon.util.CupomTestUtils.CODIGO_SANITIZADO;
import static tenda.cupon.util.CupomTestUtils.ID;
import static tenda.cupon.util.CupomTestUtils.cupomEntityResgatado;
import static tenda.cupon.util.CupomTestUtils.cupomRestauradoDeletado;
import static tenda.cupon.util.CupomTestUtils.cupomRestauradoPublicado;

import org.junit.jupiter.api.Test;

import tenda.cupon.domain.enums.StatusCupom;
import tenda.cupon.domain.model.Cupom;
import tenda.cupon.infrastructure.persistence.entity.CupomEntity;

class CupomEntityMapperTest {

	private final CupomEntityMapper mapper = new CupomEntityMapperImpl();

	@Test
	void toEntity_deveMapearStatusEDeletado() {
		var cupom = cupomRestauradoPublicado();

		CupomEntity entidade = mapper.toEntity(cupom);

		assertThat(entidade.getStatus()).isEqualTo("ATIVO");
		assertThat(entidade.getCodigo()).isEqualTo(CODIGO_SANITIZADO);
		assertThat(entidade.isPublicado()).isTrue();
		assertThat(entidade.isDeletado()).isFalse();
	}

	@Test
	void toEntity_deveMapearCupomDeletado() {
		var cupom = cupomRestauradoDeletado();

		CupomEntity entidade = mapper.toEntity(cupom);

		assertThat(entidade.isDeletado()).isTrue();
	}

	@Test
	void toDomain_deveRestaurarCupomComDeletado() {
		var entidade = cupomEntityResgatado(ID);
		entidade.setDeletado(true);

		Cupom cupom = mapper.toDomain(entidade);

		assertThat(cupom.getId()).isEqualTo(ID);
		assertThat(cupom.getStatus()).isEqualTo(StatusCupom.ATIVO);
		assertThat(cupom.isResgatado()).isTrue();
		assertThat(cupom.isDeletado()).isTrue();
	}

}
