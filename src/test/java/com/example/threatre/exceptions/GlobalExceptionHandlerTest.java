package com.example.threatre.exceptions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGenericException_thenReturnInternalServerError() throws Exception {
        mockMvc.perform(get("/filmes/erro"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(500)) // status do erro
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Erro interno")) // mensagem de erro
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Erro interno")); // mensagem detalhada
    }

    @Test
    public void whenIllegalArgumentException_thenReturnBadRequest() throws Exception {
        mockMvc.perform(post("/filmes/buscar")
                        .contentType("application/json")
                        .content("{\"title\": \"\"}")) // corpo vazio
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Parâmetro inválido"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("O título do filme não pode ser vazio"));
    }


    @Test
    public void whenResourceNotFoundException_thenReturnNotFound() throws Exception {
        mockMvc.perform(get("/filmes/9999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404)) // status do erro
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Recurso não encontrado")) // mensagem de erro
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Filme com ID 9999 não encontrado")); // mensagem detalhada
    }
}
