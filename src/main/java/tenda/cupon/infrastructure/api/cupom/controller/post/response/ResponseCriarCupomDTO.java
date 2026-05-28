package tenda.cupon.infrastructure.api.cupom.controller.post.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseCriarCupomDTO(
		UUID id,
		String codigo,
		String descricao,
		BigDecimal valorDesconto,
		LocalDateTime dataExpiracao,
		String status,
		boolean publicado,
		boolean resgatado
) {
}
