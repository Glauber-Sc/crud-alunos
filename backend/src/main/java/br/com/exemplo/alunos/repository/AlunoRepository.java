package br.com.exemplo.alunos.repository;

import br.com.exemplo.alunos.dto.AlunoRequest;
import br.com.exemplo.alunos.model.Aluno;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlunoRepository {
    private final JdbcTemplate jdbc;

    public AlunoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Aluno> listar() {
        return jdbc.query("SELECT id, nome, email, idade FROM alunos ORDER BY id", (rs, index) -> new Aluno(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getInt("idade")));
    }

    public Aluno criar(AlunoRequest dados) {
        Long id = jdbc.queryForObject(
                "INSERT INTO alunos (nome, email, idade) VALUES (?, ?, ?) RETURNING id",
                Long.class, dados.nome(), dados.email(), dados.idade());
        return new Aluno(id, dados.nome(), dados.email(), dados.idade());
    }

    public void alterar(Long id, AlunoRequest dados) {
        jdbc.update("UPDATE alunos SET nome = ?, email = ?, idade = ? WHERE id = ?",
                dados.nome(), dados.email(), dados.idade(), id);
    }

    public void excluir(Long id) {
        jdbc.update("DELETE FROM alunos WHERE id = ?", id);
    }
}
