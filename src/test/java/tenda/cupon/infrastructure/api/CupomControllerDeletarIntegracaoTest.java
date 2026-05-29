package tenda.cupon.infrastructure.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tenda.cupon.util.CupomTestUtils.ID_INEXISTENTE;
import static tenda.cupon.util.CupomTestUtils.codigoUnicoSeisCaracteres;
import static tenda.cupon.util.CupomTestUtils.criarCupomERetornarId;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import tenda.cupon.infrastructure.persistence.repository.JpaCupomRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CupomControllerDeletarIntegracaoTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JpaCupomRepository jpaCupomRepository;

	@Test
	@DisplayName("Deve retornar 204 e persistir exclusão lógica no banco")
	void deveRetornar204EPersistirExclusaoLogicaNoBanco() throws Exception {
		String codigo = codigoUnicoSeisCaracteres("DL");
		String id = criarCupomERetornarId(mockMvc, codigo, "Cupom para delete");
		UUID cupomId = UUID.fromString(id);

		assertThat(jpaCupomRepository.findById(cupomId))
				.isPresent()
				.get()
				.satisfies(entidade -> assertThat(entidade.isDeletado()).isFalse());

		mockMvc.perform(delete("/cupom/{id}", id))
				.andExpect(status().isNoContent());

		assertThat(jpaCupomRepository.findById(cupomId))
				.isPresent()
				.get()
				.satisfies(entidade -> assertThat(entidade.isDeletado()).isTrue());

		mockMvc.perform(delete("/cupom/{id}", id))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Deve retornar 404 quando cupom não existe")
	void deveRetornar404QuandoCupomNaoExiste() throws Exception {
		mockMvc.perform(delete("/cupom/{id}", ID_INEXISTENTE))
				.andExpect(status().isNotFound());
	}

}
