package br.com.exemplo.alunos.service;

import br.com.exemplo.alunos.dto.AlunoRequest;
import br.com.exemplo.alunos.model.Aluno;
import br.com.exemplo.alunos.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {
    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public List<Aluno> listar() {
        return repository.listar();
    }

    public Aluno criar(AlunoRequest dados) {
        return repository.criar(dados);
    }

    public Aluno alterar(Long id, AlunoRequest dados) {
        repository.alterar(id, dados);
        return new Aluno(id, dados.nome(), dados.email(), dados.idade());
    }

    public void excluir(Long id) {
        repository.excluir(id);
    }
}
