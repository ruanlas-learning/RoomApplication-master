package br.com.alura.roomapplication.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Calendar;
import java.util.List;

import br.com.alura.roomapplication.models.Prova;

@Dao
public interface ProvaDao {

    @Insert
    void insere(Prova prova);

    @Query("SELECT * FROM prova")
    List<Prova> buscaTodos();

    @Query("SELECT * FROM prova WHERE data_realizacao BETWEEN :inicio AND :fim")
    List<Prova> buscaPeloPeriodo(Calendar inicio, Calendar fim);

    @Update
    void altera(Prova prova);

    @Delete
    void deleta(Prova prova);
}
