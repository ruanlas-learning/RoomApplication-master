package br.com.alura.roomapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import br.com.alura.roomapplication.database.conversor.ConversorDeData;
import br.com.alura.roomapplication.database.daos.AlunoDao;
import br.com.alura.roomapplication.database.daos.ProvaDao;
import br.com.alura.roomapplication.models.Aluno;
import br.com.alura.roomapplication.models.Prova;

@Database(entities = { Aluno.class, Prova.class}, version = 3)
@TypeConverters(ConversorDeData.class) // é necessário informar o(s) conversor(es) de dados que
// o ROOM deve utilizar para a conversão de alguns dos campos da entidade (como por exemplo o
// data_realização do tipo Calendar)
public abstract class AluraDatabase extends RoomDatabase {

    public abstract AlunoDao getAlunoDao();
    public abstract ProvaDao getProvaDao();
}
