package tenda.cupon.infrastructure.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tenda.cupon.util.CupomTestUtils.ID_INEXISTENTE;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CupomControllerDeletarIntegracaoTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void deletar_deveRetornar204EExclusaoLogica() throws Exception {
		String id = criarCupomERetornarId();

		mockMvc.perform(delete("/cupom/{id}", id))
				.andExpect(status().isNoContent());

		mockMvc.perform(delete("/cupom/{id}", id))
				.andExpect(status().isBadRequest());
	}

	@Test
	void deletar_deveRetornar404QuandoCupomNaoExiste() throws Exception {
		mockMvc.perform(delete("/cupom/{id}", ID_INEXISTENTE))
				.andExpect(status().isNotFound());
	}

	private String criarCupomERetornarId() throws Exception {
		String corpo = """
				{
				  "codigo": "DEL-001",
				  "descricao": "Cupom para delete",
				  "valorDesconto": 0.8,
				  "dataExpiracao": "2030-11-04T17:14:45",
				  "publicado": false
				}
				""";

		MvcResult resultado = mockMvc.perform(post("/cupom")
						.contentType(MediaType.APPLICATION_JSON)
						.content(corpo))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn();

		return JsonPath.read(resultado.getResponse().getContentAsString(), "$.id");
	}

}
