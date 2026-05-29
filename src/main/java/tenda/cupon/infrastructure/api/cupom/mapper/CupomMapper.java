package tenda.cupon.infrastructure.api.cupom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tenda.cupon.domain.model.Cupom;
import tenda.cupon.infrastructure.api.cupom.controller.post.request.RequestCriarCupomDTO;
import tenda.cupon.infrastructure.api.cupom.controller.post.response.ResponseCriarCupomDTO;

@Mapper(componentModel = "spring")
public interface CupomMapper {

	default Cupom fromRequest(RequestCriarCupomDTO requisicao) {
		return new Cupom(
				requisicao.codigo(),
				requisicao.descricao(),
				requisicao.valorDesconto(),
				requisicao.dataExpiracao(),
				Boolean.TRUE.equals(requisicao.publicado()));
	}

	@Mapping(target = "status", expression = "java(cupom.getStatus().name())")
	ResponseCriarCupomDTO toResponse(Cupom cupom);

}
