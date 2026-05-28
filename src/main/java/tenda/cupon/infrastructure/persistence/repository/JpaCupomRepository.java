package tenda.cupon.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import tenda.cupon.infrastructure.persistence.entity.CupomEntity;

public interface JpaCupomRepository extends JpaRepository<CupomEntity, UUID> {
}
