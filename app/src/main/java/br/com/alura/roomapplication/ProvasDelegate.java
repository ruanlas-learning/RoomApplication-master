package br.com.alura.roomapplication;


import br.com.alura.roomapplication.models.Prova;

public interface ProvasDelegate {
    void lidaComClickFAB();

    void voltaParaTelaAnterior();

    void alteraNomeDoToolbar(String nome);

    void lidaComProvaSelecionado(Prova prova);
}
