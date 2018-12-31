package br.com.alura.roomapplication.models;

public class Aluno {

    private String nome;
    private String email;

    public String getNome() {
        return nome;
    }

    public Aluno setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Aluno setEmail(String email) {
        this.email = email;
        return this;
    }
}
