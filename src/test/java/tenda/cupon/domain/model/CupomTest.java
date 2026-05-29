package tenda.cupon.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tenda.cupon.util.CupomTestUtils.CODIGO_BRUTO;
import static tenda.cupon.util.CupomTestUtils.CODIGO_SANITIZADO;
import static tenda.cupon.util.CupomTestUtils.DATA_EXPIRACAO_FUTURA;
import static tenda.cupon.util.CupomTestUtils.DATA_EXPIRACAO_PASSADA;
import static tenda.cupon.util.CupomTestUtils.DESCRICAO;
import static tenda.cupon.util.CupomTestUtils.ID;
import static tenda.cupon.util.CupomTestUtils.VALOR_DESCONTO;
import static tenda.cupon.util.CupomTestUtils.cupomRestaurado;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tenda.cupon.domain.enums.StatusCupom;
import tenda.cupon.domain.exception.CupomInvalidoException;

class CupomTest {

	@Test
	@DisplayName("Deve criar cupom válido com código sanitizado")
	void deveCriarCupomValidoComCodigoSanitizado() {
		Cupom cupom = new Cupom(CODIGO_BRUTO, DESCRICAO, VALOR_DESCONTO, DATA_EXPIRACAO_FUTURA, true);

		assertThat(cupom.getId()).isNotNull();
		assertThat(cupom.getCodigo()).isEqualTo(CODIGO_SANITIZADO);
		assertThat(cupom.getDescricao()).isEqualTo(DESCRICAO);
		assertThat(cupom.getValorDesconto()).isEqualByComparingTo(VALOR_DESCONTO);
		assertThat(cupom.getDataExpiracao()).isEqualTo(DATA_EXPIRACAO_FUTURA);
		assertThat(cupom.getStatus()).isEqualTo(StatusCupom.ATIVO);
		assertThat(cupom.isPublicado()).isTrue();
		assertThat(cupom.isResgatado()).isFalse();
		assertThat(cupom.isDeletado()).isFalse();
	}

	@Test
	@DisplayName("Deve remover espaços da descrição")
	void deveRemoverEspacosDaDescricao() {
		Cupom cupom = new Cupom(CODIGO_BRUTO, "  " + DESCRICAO + "  ", VALOR_DESCONTO, DATA_EXPIRACAO_FUTURA,
				false);

		assertThat(cupom.getDescricao()).isEqualTo(DESCRICAO);
		assertThat(cupom.isPublicado()).isFalse();
	}

	@Test
	@DisplayName("Deve aceitar valor de desconto mínimo")
	void deveAceitarValorDescontoMinimo() {
		Cupom cupom = new Cupom(CODIGO_BRUTO, DESCRICAO, new BigDecimal("0.5"), DATA_EXPIRACAO_FUTURA, false);

		assertThat(cupom.getValorDesconto()).isEqualByComparingTo("0.5");
	}

	@Test
	@DisplayName("Deve rejeitar código com tamanho diferente de seis")
	void deveRejeitarCodigoComTamanhoDiferenteDeSeis() {
		assertThatThrownBy(() -> new Cupom("ABC12", DESCRICAO, VALOR_DESCONTO, DATA_EXPIRACAO_FUTURA, false))
				.isInstanceOf(CupomInvalidoException.class)
				.hasMessage("Código deve possuir 6 caracteres");

		assertThatThrownBy(() -> new Cupom("ABC1234", DESCRICAO, VALOR_DESCONTO, DATA_EXPIRACAO_FUTURA, false))
				.isInstanceOf(CupomInvalidoException.class)
				.hasMessage("Código deve possuir 6 caracteres");
	}

	@Test
	@DisplayName("Deve rejeitar código sem caracteres alfanuméricos")
	void deveRejeitarCodigoSemCaracteresAlfanumericos() {
		assertThatThrownBy(() -> new Cupom("@#$%-_", DESCRICAO, VALOR_DESCONTO, DATA_EXPIRACAO_FUTURA, false))
				.isInstanceOf(CupomInvalidoException.class)
				.hasMessage("Código deve possuir 6 caracteres");
	}

	@Test
	@DisplayName("Deve rejeitar valor de desconto abaixo do mínimo")
	void deveRejeitarValorDescontoAbaixoDoMinimo() {
		assertThatThrownBy(
				() -> new Cupom(CODIGO_BRUTO, DESCRICAO, new BigDecimal("0.49"), DATA_EXPIRACAO_FUTURA, false))
				.isInstanceOf(CupomInvalidoException.class)
				.hasMessage("Valor mínimo do desconto é 0.5");
	}

	@Test
	@DisplayName("Deve rejeitar data de expiração no passado")
	void deveRejeitarDataExpiracaoNoPassado() {
		assertThatThrownBy(
				() -> new Cupom(CODIGO_BRUTO, DESCRICAO, VALOR_DESCONTO, DATA_EXPIRACAO_PASSADA, false))
				.isInstanceOf(CupomInvalidoException.class)
				.hasMessage("Data de expiração inválida");
	}

	@Test
	@DisplayName("Deve reconstruir cupom sem validar")
	void deveReconstruirCupomSemValidar() {
		Cupom cupom = Cupom.restaurar(ID, CODIGO_SANITIZADO, DESCRICAO, VALOR_DESCONTO, DATA_EXPIRACAO_PASSADA,
				StatusCupom.ATIVO, true, true, true);

		assertThat(cupom.getId()).isEqualTo(ID);
		assertThat(cupom.getCodigo()).isEqualTo(CODIGO_SANITIZADO);
		assertThat(cupom.getDataExpiracao()).isEqualTo(DATA_EXPIRACAO_PASSADA);
		assertThat(cupom.isPublicado()).isTrue();
		assertThat(cupom.isResgatado()).isTrue();
		assertThat(cupom.isDeletado()).isTrue();
	}

	@Test
	@DisplayName("Deve marcar cupom como resgatado")
	void deveMarcarCupomComoResgatado() {
		Cupom cupom = cupomRestaurado();

		cupom.resgatar();

		assertThat(cupom.isResgatado()).isTrue();
	}

	@Test
	@DisplayName("Deve rejeitar resgate quando cupom já foi resgatado")
	void deveRejeitarResgateQuandoJaResgatado() {
		var cupom = cupomRestaurado(ID, CODIGO_SANITIZADO, DESCRICAO, VALOR_DESCONTO, DATA_EXPIRACAO_FUTURA, false,
				true, false);

		assertThatThrownBy(cupom::resgatar)
				.isInstanceOf(CupomInvalidoException.class)
				.hasMessage("Cupom já resgatado");
	}

	@Test
	@DisplayName("Deve marcar cupom como deletado")
	void deveMarcarCupomComoDeletado() {
		Cupom cupom = cupomRestaurado();

		cupom.deletar();

		assertThat(cupom.isDeletado()).isTrue();
	}

	@Test
	@DisplayName("Deve rejeitar exclusão quando cupom já foi deletado")
	void deveRejeitarExclusaoQuandoJaDeletado() {
		var cupom = cupomRestaurado(ID, CODIGO_SANITIZADO, DESCRICAO, VALOR_DESCONTO, DATA_EXPIRACAO_FUTURA, false,
				false, true);

		assertThatThrownBy(cupom::deletar)
				.isInstanceOf(CupomInvalidoException.class)
				.hasMessage("Cupom já foi deletado");
	}

}
