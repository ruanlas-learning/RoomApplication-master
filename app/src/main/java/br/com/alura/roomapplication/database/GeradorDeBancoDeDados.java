package br.com.alura.roomapplication.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

public class GeradorDeBancoDeDados {

    public AluraDatabase gera(Context context){
        String nomeDoBanco = "aluradb";
        AluraDatabase aluraDatabase = Room
                .databaseBuilder(context, AluraDatabase.class, nomeDoBanco)
                .allowMainThreadQueries() // este método faz com que a aplicação permita fazer o
                // acesso ao banco na Thread principal, que por padrão, não é permitido.
                // O que também poderia solucionar este problema seria utilizar o AsyncTask para
                // fazer em background

//                .fallbackToDestructiveMigration() // Este método diz ao banco para destruí-lo e
//                // recriá-lo se caso houver uma alteração em sua estrutura (quando tiver a versão do
//                // banco alterada) - Recomendado apenas para dev, com o app fora de produção

                .addMigrations(doisParaTres()) // Este método diz que queremos alterar a estrutura do
                // banco de dados mantendo os dados que foram registrados, ou seja, queremos migrar os dados
                // da estrutura antiga para a estrutura nova (como se fosse apenas um "alter table").
                // Em seus parâmetros, podemos criar no mínimo 1 Migration, e no máximo N Migrations.
                // As Migrations irá especificar as alterações necessárias que será necessário
                // realizar na estrutura do banco de dados.
                .build();

        return aluraDatabase;
    }

    private Migration doisParaTres() {
        return new Migration(2,3) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                String sql = "ALTER TABLE prova ADD COLUMN data_realizacao INTEGER;";
                database.execSQL(sql);
            }
        };
    }
}
