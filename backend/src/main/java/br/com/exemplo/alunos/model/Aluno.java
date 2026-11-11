package br.com.exemplo.alunos.model;

public record Aluno(
        Long id,
        String nome,
        String email,
        Integer idade) {
}
