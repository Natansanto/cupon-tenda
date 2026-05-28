package tenda.cupon.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;


@Entity
@Table(name = "cupom")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CupomEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 6)
    private String codigo;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Column(name = "valor_desconto", nullable = false)
    private BigDecimal valorDesconto;

    @Column(name = "data_expiracao", nullable = false)
    private LocalDateTime dataExpiracao;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false)
    private boolean publicado;

    @Column(nullable = false)
    private boolean resgatado;

}
