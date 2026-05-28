package tenda.cupon.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import tenda.cupon.domain.enums.StatusCupom;
import tenda.cupon.domain.exception.CupomInvalidoException;

@Getter
public class Cupom {

	private final UUID id;
	private final String codigo;
	private final String descricao;
	private final BigDecimal valorDesconto;
	private final LocalDateTime dataExpiracao;
	private final StatusCupom status;
	private final boolean publicado;
	private boolean resgatado;
	private boolean deletado;

	public Cupom(String codigo, String descricao, BigDecimal valorDesconto, LocalDateTime dataExpiracao,
			boolean publicado) {

		String codigoSanitizado = codigo.replaceAll("[^a-zA-Z0-9]", "");

		if (codigoSanitizado.length() != 6) {
			throw new CupomInvalidoException("Código deve possuir 6 caracteres");
		}

		if (valorDesconto.compareTo(BigDecimal.valueOf(0.5)) < 0) {
			throw new CupomInvalidoException("Valor mínimo do desconto é 0.5");
		}

		if (dataExpiracao.isBefore(LocalDateTime.now())) {
			throw new CupomInvalidoException("Data de expiração inválida");
		}

		this.id = UUID.randomUUID();
		this.codigo = codigoSanitizado;
		this.descricao = descricao.trim();
		this.valorDesconto = valorDesconto;
		this.dataExpiracao = dataExpiracao;
		this.publicado = publicado;
		this.resgatado = false;
		this.deletado = false;
		this.status = StatusCupom.ATIVO;
	}

	private Cupom(UUID id, String codigo, String descricao, BigDecimal valorDesconto, LocalDateTime dataExpiracao,
			StatusCupom status, boolean publicado, boolean resgatado, boolean deletado) {
		this.id = id;
		this.codigo = codigo;
		this.descricao = descricao;
		this.valorDesconto = valorDesconto;
		this.dataExpiracao = dataExpiracao;
		this.status = status;
		this.publicado = publicado;
		this.resgatado = resgatado;
		this.deletado = deletado;
	}

	public static Cupom restaurar(UUID id, String codigo, String descricao, BigDecimal valorDesconto,
			LocalDateTime dataExpiracao, StatusCupom status, boolean publicado, boolean resgatado, boolean deletado) {
		return new Cupom(id, codigo, descricao, valorDesconto, dataExpiracao, status, publicado, resgatado, deletado);
	}

	public void resgatar() {
		if (this.resgatado) {
			throw new CupomInvalidoException("Cupom já resgatado");
		}
		this.resgatado = true;
	}

	public void deletar() {
		if (this.deletado) {
			throw new CupomInvalidoException("Cupom já foi deletado");
		}
		this.deletado = true;
	}

}
