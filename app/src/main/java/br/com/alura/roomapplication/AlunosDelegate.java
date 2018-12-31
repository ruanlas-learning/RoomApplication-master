package br.com.alura.roomapplication;

import br.com.alura.roomapplication.models.Aluno;

public interface AlunosDelegate {
    void lidaComClickFAB();

    void voltaParaTelaAnterior();

    void alteraNomeDoToolbar(String nome);

    void lidaComAlunoSelecionado(Aluno aluno);
}
