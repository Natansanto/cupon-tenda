package tenda.cupon.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tenda.cupon.domain.enums.StatusCupom;
import tenda.cupon.domain.model.Cupom;
import tenda.cupon.infrastructure.persistence.entity.CupomEntity;

@Mapper(componentModel = "spring")
public interface CupomEntityMapper {

	@Mapping(target = "status", expression = "java(cupom.getStatus().name())")
	CupomEntity toEntity(Cupom cupom);

	default Cupom toDomain(CupomEntity entidade) {
		return Cupom.restaurar(
				entidade.getId(),
				entidade.getCodigo(),
				entidade.getDescricao(),
				entidade.getValorDesconto(),
				entidade.getDataExpiracao(),
				StatusCupom.valueOf(entidade.getStatus()),
				entidade.isPublicado(),
				entidade.isResgatado(),
				entidade.isDeletado());
	}

}
