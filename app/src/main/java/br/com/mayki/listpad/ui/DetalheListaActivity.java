package br.com.mayki.listpad.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Locale;

import br.com.mayki.listpad.adapter.ItemAdapter;
import br.com.mayki.listpad.dataBase.RoomListPad;
import br.com.mayki.listpad.dataBase.dao.relacionamentos.ListaCategoria;
import br.com.mayki.listpad.databinding.ActivityDetalheListaBinding;

public class DetalheListaActivity extends AppCompatActivity {

    ActivityDetalheListaBinding activityDetalheListaBinding;
    ListaCategoria listaCategoria;

    TextView nomeCategoria;
    FloatingActionButton buttonAdicionarItem;
    TextView dataCriacao;
    ListView lista;
    Toolbar toolbar;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetalheListaBinding = ActivityDetalheListaBinding.inflate(getLayoutInflater());
        setContentView(activityDetalheListaBinding.getRoot());

        listaCategoria = (ListaCategoria) getIntent().getSerializableExtra(Constantes.LISTA_CATEGORIA);

        carregaCampos();
        defineToolbar(listaCategoria.lista.getNome());
        defineBottombar(listaCategoria);
        //populaListaComAdapter();

        defineEventoParaChamarActivityNovoItem();
    }

    private void populaListaComAdapter() {
        Log.i("LOG-DETALHE_LISTA", "defineAdapter: no detalhe lista");
        itemAdapter = new ItemAdapter(this, RoomListPad.getInstance(this).getItemDAO().buscarPorLista(listaCategoria.lista.getId()));
        lista.setAdapter(itemAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        populaListaComAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populaListaComAdapter();
    }

    private void atualizaLista() {
        Log.i("LOG-DETALHE_LISTA", "atualizaLista: no detalhe lista");
        itemAdapter.clear();
        itemAdapter.addAll(RoomListPad.getInstance(this).getItemDAO().buscarPorLista(listaCategoria.lista.getId()));
        itemAdapter.notifyDataSetChanged();
        lista.refreshDrawableState();
    }

    private void defineEventoParaChamarActivityNovoItem() {
        buttonAdicionarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalheListaActivity.this, NovoItemActivity.class);
                intent.putExtra(Constantes.LISTA_CATEGORIA, listaCategoria);
                startActivity(intent);
            }
        });
    }

    private void defineBottombar(ListaCategoria listaCategoria) {
        nomeCategoria.setText(listaCategoria.categoria.getDescricao().toUpperCase(Locale.ROOT));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dataCriacao.setText("Criado em: " + simpleDateFormat.format(listaCategoria.lista.getDataCriacao()));
    }

    private void defineToolbar(String titulo) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(titulo.toUpperCase(Locale.ROOT));
    }

    private void carregaCampos() {
        nomeCategoria = activityDetalheListaBinding.activityDetalheListaTvCategoriaLista;
        buttonAdicionarItem = activityDetalheListaBinding.activityDetalheListaFbAdicionarItem;
        dataCriacao = activityDetalheListaBinding.activityDetalheListaTvDataCriacaoLista;
        lista = activityDetalheListaBinding.activityDetalheListaLista;
        toolbar = activityDetalheListaBinding.activityDetalheListaToolbar;
    }
}