package br.com.exemplo.alunos.controller;

import br.com.exemplo.alunos.dto.AlunoRequest;
import br.com.exemplo.alunos.model.Aluno;
import br.com.exemplo.alunos.service.AlunoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlunoController.class)
class AlunoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private AlunoService service;

    @Test
    void getListaAlunos() throws Exception {
        given(service.listar()).willReturn(List.of(new Aluno(1L, "Ana", "ana@email.com", 20)));

        mvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Ana"));
    }

    @Test
    void postCriaAluno() throws Exception {
        given(service.criar(any(AlunoRequest.class)))
                .willReturn(new Aluno(1L, "Ana", "ana@email.com", 20));

        mvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Ana\",\"email\":\"ana@email.com\",\"idade\":20}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void putAlteraAluno() throws Exception {
        given(service.alterar(eq(1L), any(AlunoRequest.class)))
                .willReturn(new Aluno(1L, "Ana Maria", "ana@email.com", 21));

        mvc.perform(put("/alunos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Ana Maria\",\"email\":\"ana@email.com\",\"idade\":21}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idade").value(21));
    }

    @Test
    void deleteExcluiAluno() throws Exception {
        mvc.perform(delete("/alunos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void postInvalidoRetorna400() throws Exception {
        mvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"\",\"email\":\"invalido\",\"idade\":-1}"))
                .andExpect(status().isBadRequest());
    }
}
