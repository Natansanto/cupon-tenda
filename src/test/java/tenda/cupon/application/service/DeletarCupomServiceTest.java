package tenda.cupon.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tenda.cupon.util.CupomTestUtils.ID;
import static tenda.cupon.util.CupomTestUtils.ID_INEXISTENTE;
import static tenda.cupon.util.CupomTestUtils.cupomRestaurado;
import static tenda.cupon.util.CupomTestUtils.cupomRestauradoDeletado;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tenda.cupon.domain.exception.CupomInvalidoException;
import tenda.cupon.domain.exception.CupomNaoEncontradoException;
import tenda.cupon.domain.repository.CupomRepository;

@ExtendWith(MockitoExtension.class)
class DeletarCupomServiceTest {

	@Mock
	private CupomRepository cupomRepository;

	private DeletarCupomService service;

	@BeforeEach
	void setUp() {
		service = new DeletarCupomService(cupomRepository);
	}

	@Test
	void deletar_deveMarcarCupomComoDeletadoEPersistir() {
		var cupom = cupomRestaurado();
		when(cupomRepository.buscarPorId(ID)).thenReturn(Optional.of(cupom));
		when(cupomRepository.salvar(cupom)).thenReturn(cupom);

		service.deletar(ID);

		verify(cupomRepository).salvar(cupom);
		org.assertj.core.api.Assertions.assertThat(cupom.isDeletado()).isTrue();
	}

	@Test
	void deletar_deveRetornar404QuandoCupomNaoExiste() {
		when(cupomRepository.buscarPorId(ID_INEXISTENTE)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> service.deletar(ID_INEXISTENTE))
				.isInstanceOf(CupomNaoEncontradoException.class)
				.hasMessageContaining("não encontrado");

		verify(cupomRepository, never()).salvar(any());
	}

	@Test
	void deletar_deveRetornarErroQuandoCupomJaDeletado() {
		var cupom = cupomRestauradoDeletado();
		when(cupomRepository.buscarPorId(ID)).thenReturn(Optional.of(cupom));

		assertThatThrownBy(() -> service.deletar(ID))
				.isInstanceOf(CupomInvalidoException.class)
				.hasMessageContaining("já foi deletado");

		verify(cupomRepository, never()).salvar(any());
	}

}
