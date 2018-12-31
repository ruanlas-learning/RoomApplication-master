package br.com.alura.roomapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.alura.roomapplication.AlunosDelegate;
import br.com.alura.roomapplication.R;

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

        FloatingActionButton botaoAdd = view.findViewById(R.id.fragment_lista_fab);
        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.lidaComClickFAB();
            }
        });

        return view;
    }


}
