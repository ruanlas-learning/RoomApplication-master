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

import java.io.Serializable;

import br.com.alura.roomapplication.AlunosDelegate;
import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.AlunoDao;
import br.com.alura.roomapplication.database.AluraDatabase;
import br.com.alura.roomapplication.database.GeradorDeBancoDeDados;
import br.com.alura.roomapplication.models.Aluno;

public class FormularioAlunosFragment extends Fragment {

    private Aluno aluno;
    private AlunosDelegate delegate;

    // Este método é chamado para montar o fragment antes de criar a view do fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        delegate = (AlunosDelegate) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        delegate.alteraNomeDoToolbar("Cadastro de Aluno");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_alunos, container, false);

        Button cadastrar = view.findViewById(R.id.formulario_alunos_cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaInformacoesDoAluno();

                GeradorDeBancoDeDados gerador = new GeradorDeBancoDeDados();
                AluraDatabase aluraDatabase = gerador.gera(getContext());
                AlunoDao alunoDao = aluraDatabase.getAlunoDao();

                if (aluno.getId() == null){
                    alunoDao.insere(aluno);
                }else {
                    alunoDao.altera(aluno);
                }

                Toast.makeText(getContext(), "Aluno salvo!", Toast.LENGTH_SHORT).show();
                delegate.voltaParaTelaAnterior();
            }
        });

        Bundle arguments = getArguments();
        if (arguments != null){
            aluno = (Aluno) arguments.getSerializable("aluno");
            EditText campoNome = view.findViewById(R.id.formulario_alunos_nome);
            EditText campoEmail = view.findViewById(R.id.formulario_alunos_email);
            campoEmail.setText(aluno.getEmail());
            campoNome.setText(aluno.getNome());
        }

        return view;
    }

    private void atualizaInformacoesDoAluno() {
        if (aluno == null){
            aluno = new Aluno();
        }
        EditText campoNome = getView().findViewById(R.id.formulario_alunos_nome);
        EditText campoEmail = getView().findViewById(R.id.formulario_alunos_email);
        aluno.setEmail(campoEmail.getText().toString());
        aluno.setNome(campoNome.getText().toString());
    }
}
