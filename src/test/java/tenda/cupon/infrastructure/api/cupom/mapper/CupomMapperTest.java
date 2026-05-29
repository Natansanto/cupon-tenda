package tenda.cupon.infrastructure.api.cupom.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static tenda.cupon.util.CupomTestUtils.CODIGO_SANITIZADO;
import static tenda.cupon.util.CupomTestUtils.DESCRICAO;
import static tenda.cupon.util.CupomTestUtils.cupomNovo;
import static tenda.cupon.util.CupomTestUtils.requestCriarCupom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tenda.cupon.domain.enums.StatusCupom;
import tenda.cupon.domain.model.Cupom;

class CupomMapperTest {

	private final CupomMapper mapper = new CupomMapperImpl();

	@Test
	@DisplayName("Deve criar cupom de domínio a partir da requisição")
	void deveCriarCupomDeDominio() {
		Cupom cupom = mapper.fromRequest(requestCriarCupom());

		assertThat(cupom.getCodigo()).isEqualTo(CODIGO_SANITIZADO);
		assertThat(cupom.getDescricao()).isEqualTo(DESCRICAO);
		assertThat(cupom.isPublicado()).isTrue();
		assertThat(cupom.getStatus()).isEqualTo(StatusCupom.ATIVO);
	}

	@Test
	@DisplayName("Deve mapear status como string na resposta")
	void deveMapearStatusComoString() {
		Cupom cupom = cupomNovo();

		var response = mapper.toResponse(cupom);

		assertThat(response.codigo()).isEqualTo(CODIGO_SANITIZADO);
		assertThat(response.status()).isEqualTo("ATIVO");
		assertThat(response.resgatado()).isFalse();
		assertThat(response.id()).isEqualTo(cupom.getId());
	}

}
