package BonApp.BonApp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import BonApp.BonApp.entities.Indirizzo;
import BonApp.BonApp.payload.NewUserPayload;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
	}
	
	Indirizzo indirizzo = new Indirizzo("40137", "abc", "abc", "abc", "abc", "abc");

	@Test
	public void testPayloadNonValido() throws Exception {
		NewUserPayload PayloadNonValido = new NewUserPayload("", "", "", "luca.it", indirizzo, "luca");

		// PASSARE SEMPRE UN TOKEN VALIDO PER VERIFICARE I TEST
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZTZmMTFlMy05OWE0LTQxNDMtYWE4My0wNDU2NDkwZTMxMWUiLCJpYXQiOjE2OTU4ODg1NDQsImV4cCI6MTY5NjQ5MzM0NH0.tJSXi6Hye7EOGUPvyWISNkyBswG4bcD-jHvNR3cZu2w";

		MvcResult risultato = mockMvc.perform(post("/users").header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(PayloadNonValido)))
				.andReturn();

		String rispostaBody = risultato.getResponse().getContentAsString();

		// MESSAGGI ERRORE SPECIFICI
		assertThat(rispostaBody).contains("Il nome deve avere min 3 caratteri e max 30 caratteri");
		String rispostaBodyUtf8 = new String(rispostaBody.getBytes("ISO-8859-1"), "UTF-8");
		assertThat(rispostaBodyUtf8).contains("La password inserita non è valida");
		assertThat(rispostaBody).contains("La password non soddisfa i requisiti di sicurezza");
	}

	@Test
	public void testValidPayloadShouldReturnCreated() throws Exception {
		NewUserPayload PayloadValido = new NewUserPayload("Ciccio", "Luca", "Iannice", "luca@gmail.com", indirizzo,
				"Infedele1980!");

		// PASSARE SEMPRE UN TOKEN VALIDO PER VERIFICARE I TEST
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZTZmMTFlMy05OWE0LTQxNDMtYWE4My0wNDU2NDkwZTMxMWUiLCJpYXQiOjE2OTU4ODg1NDQsImV4cCI6MTY5NjQ5MzM0NH0.tJSXi6Hye7EOGUPvyWISNkyBswG4bcD-jHvNR3cZu2w";

		MvcResult risultato = mockMvc.perform(post("/users").header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(PayloadValido)))
				.andReturn();

		String rispostaBody = risultato.getResponse().getContentAsString();

		assertThat(rispostaBody).contains("luca@gmail.com");
		assertThat(rispostaBody).contains("luca");

	}

}
