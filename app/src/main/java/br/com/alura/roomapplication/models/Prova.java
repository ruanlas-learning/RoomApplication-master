package br.com.alura.roomapplication.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;

@Entity(tableName = "prova")
public class Prova implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String materia;

    @ColumnInfo(name = "data_realizacao")
    private Calendar dataRealizacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
//        return this;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
//        return this;
    }

    public Calendar getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(Calendar dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    @Override
    public String toString() {
        return materia;
    }
}
