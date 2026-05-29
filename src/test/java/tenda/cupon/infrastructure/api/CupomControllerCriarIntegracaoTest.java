package tenda.cupon.infrastructure.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tenda.cupon.util.CupomTestUtils.CODIGO_SANITIZADO;
import static tenda.cupon.util.CupomTestUtils.DATA_EXPIRACAO_FUTURA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CupomControllerCriarIntegracaoTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Deve retornar 201 e criar cupom com código sanitizado")
	void deveRetornar201ECriarCupomComCodigoSanitizado() throws Exception {
		String corpo = """
				{
				  "codigo": "ABC-123",
				  "descricao": "Cupom integração",
				  "valorDesconto": 0.8,
				  "dataExpiracao": "2030-11-04T17:14:45",
				  "publicado": true
				}
				""";

		mockMvc.perform(post("/cupom")
						.contentType(MediaType.APPLICATION_JSON)
						.content(corpo))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.codigo").value(CODIGO_SANITIZADO))
				.andExpect(jsonPath("$.descricao").value("Cupom integração"))
				.andExpect(jsonPath("$.valorDesconto").value(0.8))
				.andExpect(jsonPath("$.dataExpiracao").value(DATA_EXPIRACAO_FUTURA.toString()))
				.andExpect(jsonPath("$.status").value("ATIVO"))
				.andExpect(jsonPath("$.publicado").value(true))
				.andExpect(jsonPath("$.resgatado").value(false));
	}

	@Test
	@DisplayName("Deve retornar 400 quando código é inválido no domínio")
	void deveRetornar400QuandoCodigoInvalidoNoDominio() throws Exception {
		String corpo = """
				{
				  "codigo": "ABC12",
				  "descricao": "Cupom inválido",
				  "valorDesconto": 0.8,
				  "dataExpiracao": "2030-11-04T17:14:45",
				  "publicado": false
				}
				""";

		mockMvc.perform(post("/cupom")
						.contentType(MediaType.APPLICATION_JSON)
						.content(corpo))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Deve retornar 400 quando valor do desconto está abaixo do mínimo")
	void deveRetornar400QuandoValorDescontoAbaixoDoMinimo() throws Exception {
		String corpo = """
				{
				  "codigo": "VAL-049",
				  "descricao": "Cupom desconto baixo",
				  "valorDesconto": 0.49,
				  "dataExpiracao": "2030-11-04T17:14:45",
				  "publicado": false
				}
				""";

		mockMvc.perform(post("/cupom")
						.contentType(MediaType.APPLICATION_JSON)
						.content(corpo))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Deve retornar 400 quando data de expiração está no passado")
	void deveRetornar400QuandoDataExpiracaoNoPassado() throws Exception {
		String corpo = """
				{
				  "codigo": "DAT-001",
				  "descricao": "Cupom expirado",
				  "valorDesconto": 0.8,
				  "dataExpiracao": "2020-01-01T00:00:00",
				  "publicado": false
				}
				""";

		mockMvc.perform(post("/cupom")
						.contentType(MediaType.APPLICATION_JSON)
						.content(corpo))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Deve retornar 400 quando campos obrigatórios estão ausentes")
	void deveRetornar400QuandoCamposObrigatoriosAusentes() throws Exception {
		String corpo = """
				{
				  "descricao": "Sem código"
				}
				""";

		mockMvc.perform(post("/cupom")
						.contentType(MediaType.APPLICATION_JSON)
						.content(corpo))
				.andExpect(status().isBadRequest());
	}

}
