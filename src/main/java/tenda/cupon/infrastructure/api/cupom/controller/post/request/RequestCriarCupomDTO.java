package tenda.cupon.infrastructure.api.cupom.controller.post.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestCriarCupomDTO(

        @NotBlank(message = "Código é obrigatório")
        String codigo,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @NotNull(message = "Valor do desconto é obrigatório")
        @DecimalMin(value = "0.5", message = "Valor do desconto deve ser no mínimo 0.5")
        BigDecimal valorDesconto,

        @NotNull(message = "Data de expiração é obrigatória")
        @Future(message = "Data de expiração não pode estar no passado")
        LocalDateTime dataExpiracao,

        @NotNull(message = "Indicador de publicação é obrigatório")
        Boolean publicado) {
}
