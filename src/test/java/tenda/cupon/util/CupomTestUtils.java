package tenda.cupon.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import tenda.cupon.domain.enums.StatusCupom;
import tenda.cupon.domain.model.Cupom;
import tenda.cupon.infrastructure.api.cupom.controller.post.request.RequestCriarCupomDTO;
import tenda.cupon.infrastructure.persistence.entity.CupomEntity;

public final class CupomTestUtils {

	public static final UUID ID = UUID.fromString("cef9d1e3-aae5-4ab6-a297-358c6032b1e7");
	public static final UUID ID_INEXISTENTE = UUID.fromString("00000000-0000-0000-0000-000000000001");

	public static final String CODIGO_BRUTO = "ABC-123";
	public static final String CODIGO_SANITIZADO = "ABC123";
	public static final String CODIGO_BRUTO_ALTERNATIVO = "XYZ-999";
	public static final String CODIGO_SANITIZADO_ALTERNATIVO = "XYZ999";

	public static final String DESCRICAO = "Descrição";
	public static final String DESCRICAO_SERVICO = "Serviço teste";

	public static final BigDecimal VALOR_DESCONTO = new BigDecimal("0.8");
	public static final BigDecimal VALOR_DESCONTO_SERVICO = new BigDecimal("0.7");

	public static final LocalDateTime DATA_EXPIRACAO_FUTURA = LocalDateTime.of(2030, 11, 4, 17, 14, 45);
	public static final LocalDateTime DATA_EXPIRACAO_PASSADA = LocalDateTime.of(2020, 1, 1, 0, 0, 0);

	private CupomTestUtils() {
	}

	public static Cupom cupomNovo() {
		return cupomNovo(CODIGO_BRUTO, DESCRICAO, VALOR_DESCONTO, false);
	}

	public static Cupom cupomNovoServico() {
		return cupomNovo(CODIGO_BRUTO_ALTERNATIVO, DESCRICAO_SERVICO, VALOR_DESCONTO_SERVICO, false);
	}

	public static Cupom cupomNovo(String codigoBruto, String descricao, BigDecimal valorDesconto, boolean publicado) {
		return new Cupom(codigoBruto, descricao, valorDesconto, DATA_EXPIRACAO_FUTURA, publicado);
	}

	public static Cupom cupomRestaurado() {
		return cupomRestaurado(ID, CODIGO_SANITIZADO, DESCRICAO, VALOR_DESCONTO, DATA_EXPIRACAO_FUTURA, false, false,
				false);
	}

	public static Cupom cupomRestauradoPublicado() {
		return cupomRestaurado(ID, CODIGO_SANITIZADO, DESCRICAO, VALOR_DESCONTO, DATA_EXPIRACAO_FUTURA, true, false,
				false);
	}

	public static Cupom cupomRestauradoDeletado() {
		return cupomRestaurado(ID, CODIGO_SANITIZADO, DESCRICAO, VALOR_DESCONTO, DATA_EXPIRACAO_FUTURA, false, false,
				true);
	}

	public static Cupom cupomRestaurado(
			UUID id,
			String codigo,
			String descricao,
			BigDecimal valorDesconto,
			LocalDateTime dataExpiracao,
			boolean publicado,
			boolean resgatado,
			boolean deletado) {
		return Cupom.restaurar(id, codigo, descricao, valorDesconto, dataExpiracao, StatusCupom.ATIVO, publicado,
				resgatado, deletado);
	}

	public static RequestCriarCupomDTO requestCriarCupom() {
		return requestCriarCupom(true);
	}

	public static RequestCriarCupomDTO requestCriarCupom(boolean publicado) {
		return new RequestCriarCupomDTO(
				CODIGO_BRUTO,
				DESCRICAO,
				VALOR_DESCONTO,
				DATA_EXPIRACAO_FUTURA,
				publicado);
	}

	public static CupomEntity cupomEntity(Cupom cupom) {
		return CupomEntity.builder()
				.id(cupom.getId())
				.codigo(cupom.getCodigo())
				.descricao(cupom.getDescricao())
				.valorDesconto(cupom.getValorDesconto())
				.dataExpiracao(cupom.getDataExpiracao())
				.status(cupom.getStatus().name())
				.publicado(cupom.isPublicado())
				.resgatado(cupom.isResgatado())
				.deletado(cupom.isDeletado())
				.build();
	}

	public static CupomEntity cupomEntityResgatado(UUID id) {
		return CupomEntity.builder()
				.id(id)
				.codigo(CODIGO_SANITIZADO)
				.descricao(DESCRICAO)
				.valorDesconto(VALOR_DESCONTO)
				.dataExpiracao(DATA_EXPIRACAO_PASSADA)
				.status(StatusCupom.ATIVO.name())
				.publicado(false)
				.resgatado(true)
				.deletado(false)
				.build();
	}

}
