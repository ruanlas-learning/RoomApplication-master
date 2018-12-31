package br.com.alura.roomapplication.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

import br.com.alura.roomapplication.ProvasDelegate;
import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.AluraDatabase;
import br.com.alura.roomapplication.database.GeradorDeBancoDeDados;
import br.com.alura.roomapplication.database.conversor.ConversorDeData;
import br.com.alura.roomapplication.database.daos.ProvaDao;
import br.com.alura.roomapplication.models.Prova;

public class ListaProvasFragment extends Fragment {

    private ProvasDelegate delegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        delegate = (ProvasDelegate) getActivity();
        setHasOptionsMenu(true); // está definindo que neste Fragment terá um menu no Toolbar
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lista_provas_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        switch (menuItemId){
            case R.id.menu_lista_prova_intervalo:

                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                final EditText campoDataInicio = new EditText(getContext());
                campoDataInicio.setHint("Início");
                campoDataInicio.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
                final EditText campoDataFim = new EditText(getContext());
                campoDataFim.setHint("Fim");
                campoDataFim.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);

                linearLayout.addView(campoDataInicio);
                linearLayout.addView(campoDataFim);

                new AlertDialog.Builder(getContext())
                        .setView(linearLayout)
                        .setMessage("Digite as datas para busca")
                        .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String dataInicioString = campoDataInicio.getText().toString();
                                String dataFimString = campoDataFim.getText().toString();

                                Calendar dataInicioCalendar = ConversorDeData.converte(dataInicioString);
                                Calendar dataFimCalendar = ConversorDeData.converte(dataFimString);

                                GeradorDeBancoDeDados geradorDeBancoDeDados = new GeradorDeBancoDeDados();
                                AluraDatabase aluraDatabase = geradorDeBancoDeDados.gera(getContext());
                                ProvaDao provaDao = aluraDatabase.getProvaDao();

                                List<Prova> provaList = provaDao.buscaPeloPeriodo(dataInicioCalendar, dataFimCalendar);

                                ListView fragmentLista = getView().findViewById(R.id.fragment_lista);
                                configuraAdapter(fragmentLista, provaList);


                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();

                break;
        }
        return true;
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
        configuraAdapter(fragmentLista, listProva);

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

    private void configuraAdapter(ListView fragmentLista, List<Prova> listProva) {
        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, listProva);
        fragmentLista.setAdapter(adapter);
    }


}
