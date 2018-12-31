package br.com.alura.roomapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.alura.roomapplication.ProvasDelegate;
import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.GeradorDeBancoDeDados;
import br.com.alura.roomapplication.database.daos.ProvaDao;
import br.com.alura.roomapplication.models.Prova;

public class ListaProvasFragment extends Fragment {

    private ProvasDelegate delegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        delegate = (ProvasDelegate) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        delegate.alteraNomeDoToolbar("Provas realizadas");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        boolean adicionarAoPaiAgora = false;
        View view = inflater.inflate(R.layout.fragment_lista, container, adicionarAoPaiAgora);

        configuraComponentesDaView(view);

        return view;
    }

    private void configuraComponentesDaView(View view) {
        configuraLista(view);
        configuraFAB(view);
    }

    private void configuraFAB(View view) {
        FloatingActionButton botaoAdd = view.findViewById(R.id.fragment_lista_fab);
        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.lidaComClickFAB();
            }
        });
    }

    private void configuraLista(View view) {
        ListView fragmentLista = view.findViewById(R.id.fragment_lista);


        GeradorDeBancoDeDados aluraDatabase = new GeradorDeBancoDeDados();
        ProvaDao provaDao = aluraDatabase.gera(getContext()).getProvaDao();
        List<Prova> listProva = provaDao.buscaTodos();
        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, listProva);
        fragmentLista.setAdapter(adapter);

        fragmentLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listaAdapter, View view, int posicao, long id) {
                Prova prova = (Prova) listaAdapter.getItemAtPosition(posicao);
                delegate.lidaComProvaSelecionado(prova);
            }
        });

        fragmentLista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterLista, View view, int posicao, long id) {
                final Prova prova = (Prova) adapterLista.getItemAtPosition(posicao);
                Snackbar.make(view, "Excluir a prova ? ", Snackbar.LENGTH_LONG)
                        .setAction("Sim", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .show();


                return true; // somente este método irá tratar/resolver o evento de clique longo, portanto, retornará true
            }
        });
    }


}
