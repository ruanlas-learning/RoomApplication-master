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
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.alura.roomapplication.AlunosDelegate;
import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.AlunoDao;
import br.com.alura.roomapplication.database.AluraDatabase;
import br.com.alura.roomapplication.database.GeradorDeBancoDeDados;
import br.com.alura.roomapplication.models.Aluno;

public class ListaAlunosFragment extends Fragment {

    private AlunosDelegate delegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        delegate = (AlunosDelegate)getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        delegate.alteraNomeDoToolbar("Lista de Alunos");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
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

        GeradorDeBancoDeDados gerador = new GeradorDeBancoDeDados();
        AluraDatabase aluraDatabase = gerador.gera(getContext());
        final AlunoDao alunoDao = aluraDatabase.getAlunoDao();

        List<Aluno> listAlunos = alunoDao.buscaTodos();
        final ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(getContext(), android.R.layout.simple_list_item_1, listAlunos);
        fragmentLista.setAdapter(adapter);

        fragmentLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listaAdapter, View view, int posicao, long id) {
                Aluno aluno = (Aluno) listaAdapter.getItemAtPosition(posicao);
                delegate.lidaComAlunoSelecionado(aluno);
            }
        });

        fragmentLista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterLista, View view, int posicao, long id) {
                final Aluno aluno = (Aluno) adapterLista.getItemAtPosition(posicao);
                Snackbar.make(view, "Excluir o aluno " + aluno.getNome() + " ? ", Snackbar.LENGTH_LONG)
                        .setAction("Sim", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alunoDao.deleta(aluno);
                                adapter.remove(aluno);
//                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();


                return true; // somente este método irá tratar/resolver o evento de clique longo, portanto, retornará true
            }
        });
    }


}
