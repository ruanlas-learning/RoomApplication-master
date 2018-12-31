package br.com.alura.roomapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import br.com.alura.roomapplication.ProvasDelegate;
import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.GeradorDeBancoDeDados;
import br.com.alura.roomapplication.database.conversor.ConversorDeData;
import br.com.alura.roomapplication.database.daos.ProvaDao;
import br.com.alura.roomapplication.models.Prova;

public class FormularioProvasFragment extends Fragment {

    private Prova prova;
    private ProvasDelegate delegate;

    // Este método é chamado para montar o fragment antes de criar a view do fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        delegate = (ProvasDelegate) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        delegate.alteraNomeDoToolbar("Adicionar prova");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_provas, container, false);

        Button cadastrar = view.findViewById(R.id.formulario_prova_cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaInformacoesDaProva();

                GeradorDeBancoDeDados geradorDeBancoDeDados = new GeradorDeBancoDeDados();
                ProvaDao provaDao = geradorDeBancoDeDados.gera(getContext()).getProvaDao();

                if (prova.getId() == null){
                    provaDao.insere(prova);
                }else {
                    provaDao.altera(prova);
                }

                Toast.makeText(getContext(), "Prova salva!", Toast.LENGTH_SHORT).show();
                delegate.voltaParaTelaAnterior();
            }
        });

        Bundle arguments = getArguments();
        if (arguments != null){
            prova = (Prova) arguments.getSerializable("prova");
            EditText campoMateria = view.findViewById(R.id.formulario_prova_materia);
            EditText campoData = view.findViewById(R.id.formulario_prova_data);

            campoMateria.setText(prova.getMateria());
            String data = ConversorDeData.converte(prova.getDataRealizacao());
            campoData.setText(data);

        }

        return view;
    }

    private void atualizaInformacoesDaProva() {
        if (prova == null){
            prova = new Prova();
        }
        EditText campoMateria = getView().findViewById(R.id.formulario_prova_materia);
        EditText campoData = getView().findViewById(R.id.formulario_prova_data);

        prova.setMateria(campoMateria.getText().toString());
        Calendar calendar = ConversorDeData.converte(campoData.getText().toString());
        prova.setDataRealizacao(calendar);
    }
}
