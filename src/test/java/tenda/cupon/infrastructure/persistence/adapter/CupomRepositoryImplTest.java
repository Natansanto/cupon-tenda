package tenda.cupon.infrastructure.persistence.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tenda.cupon.test.util.CupomTestUtils.cupomEntity;
import static tenda.cupon.test.util.CupomTestUtils.cupomRestaurado;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tenda.cupon.domain.model.Cupom;
import tenda.cupon.infrastructure.persistence.mapper.CupomEntityMapper;
import tenda.cupon.infrastructure.persistence.repository.JpaCupomRepository;

@ExtendWith(MockitoExtension.class)
class CupomRepositoryImplTest {

	@Mock
	private JpaCupomRepository jpaCupomRepository;

	@Mock
	private CupomEntityMapper cupomEntityMapper;

	@InjectMocks
	private CupomRepositoryImpl cupomRepositoryImpl;

	@Test
	void salvar_deveConverterPersistirERetornarDominio() {
		var cupom = cupomRestaurado();
		var entidade = cupomEntity(cupom);

		when(cupomEntityMapper.toEntity(cupom)).thenReturn(entidade);
		when(jpaCupomRepository.save(entidade)).thenReturn(entidade);
		when(cupomEntityMapper.toDomain(entidade)).thenReturn(cupom);

		Cupom resultado = cupomRepositoryImpl.salvar(cupom);

		assertThat(resultado).isSameAs(cupom);
		verify(cupomEntityMapper).toEntity(cupom);
		verify(jpaCupomRepository).save(entidade);
		verify(cupomEntityMapper).toDomain(entidade);
	}

}
