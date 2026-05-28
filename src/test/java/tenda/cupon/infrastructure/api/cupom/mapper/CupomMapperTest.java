package tenda.cupon.infrastructure.api.cupom.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static tenda.cupon.test.util.CupomTestUtils.CODIGO_SANITIZADO;
import static tenda.cupon.test.util.CupomTestUtils.DESCRICAO;
import static tenda.cupon.test.util.CupomTestUtils.cupomNovo;
import static tenda.cupon.test.util.CupomTestUtils.requestCriarCupom;

import org.junit.jupiter.api.Test;

import tenda.cupon.domain.enums.StatusCupom;
import tenda.cupon.domain.model.Cupom;

class CupomMapperTest {

	private final CupomMapper mapper = new CupomMapperImpl();

	@Test
	void fromRequest_deveCriarCupomDeDominio() {
		Cupom cupom = mapper.fromRequest(requestCriarCupom());

		assertThat(cupom.getCodigo()).isEqualTo(CODIGO_SANITIZADO);
		assertThat(cupom.getDescricao()).isEqualTo(DESCRICAO);
		assertThat(cupom.isPublicado()).isTrue();
		assertThat(cupom.getStatus()).isEqualTo(StatusCupom.ATIVO);
	}

	@Test
	void toResponse_deveMapearStatusComoString() {
		Cupom cupom = cupomNovo();

		var response = mapper.toResponse(cupom);

		assertThat(response.codigo()).isEqualTo(CODIGO_SANITIZADO);
		assertThat(response.status()).isEqualTo("ATIVO");
		assertThat(response.resgatado()).isFalse();
		assertThat(response.id()).isEqualTo(cupom.getId());
	}

}
