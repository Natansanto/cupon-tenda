package tenda.cupon.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tenda.cupon.util.CupomTestUtils.CODIGO_SANITIZADO_ALTERNATIVO;
import static tenda.cupon.util.CupomTestUtils.cupomNovoServico;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tenda.cupon.domain.enums.StatusCupom;
import tenda.cupon.domain.model.Cupom;
import tenda.cupon.domain.repository.CupomRepository;

@ExtendWith(MockitoExtension.class)
class CriarCupomServiceTest {

	@Mock
	private CupomRepository cupomRepository;

	private CriarCupomService service;

	@BeforeEach
	void setUp() {
		service = new CriarCupomService(cupomRepository);
	}

	@Test
	void criar_deveDelegarPersistenciaAoRepositorio() {
		when(cupomRepository.salvar(any())).thenAnswer(invocation -> invocation.getArgument(0));

		var cupom = cupomNovoServico();

		Cupom resultado = service.criar(cupom);

		ArgumentCaptor<Cupom> captor = ArgumentCaptor.forClass(Cupom.class);
		verify(cupomRepository).salvar(captor.capture());

		assertThat(resultado).isSameAs(cupom);
		assertThat(resultado.getCodigo()).isEqualTo(CODIGO_SANITIZADO_ALTERNATIVO);
		assertThat(resultado.getStatus()).isEqualTo(StatusCupom.ATIVO);
		assertThat(captor.getValue()).isSameAs(cupom);
	}

}
