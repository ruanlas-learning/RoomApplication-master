package br.com.alura.roomapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.alura.roomapplication.models.Aluno;

@Dao
public interface AlunoDao{

    @Insert
    void insere(Aluno aluno);

    @Query("SELECT * FROM aluno")
    List<Aluno> buscaTodos();

}
