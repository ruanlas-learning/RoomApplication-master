package br.com.alura.roomapplication.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "aluno")
public class Aluno {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String nome;
    private String email;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
//        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
//        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
//        return this;
    }

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
