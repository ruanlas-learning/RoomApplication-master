package br.com.alura.roomapplication.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class GeradorDeBancoDeDados {

    public AluraDatabase gera(Context context){
        String nomeDoBanco = "aluradb";
        AluraDatabase aluraDatabase = Room
                .databaseBuilder(context, AluraDatabase.class, nomeDoBanco)
                .allowMainThreadQueries() // este método faz com que a aplicação permita fazer o
                // acesso ao banco na Thread principal, que por padrão, não é permitido.
                // O que também poderia solucionar este problema seria utilizar o AsyncTask para
                // fazer em background
                .build();

        return aluraDatabase;
    }
}
