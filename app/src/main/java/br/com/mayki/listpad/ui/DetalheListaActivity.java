package br.com.mayki.listpad.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import br.com.mayki.listpad.R;
import br.com.mayki.listpad.dataBase.dao.relacionamentos.ListaCategoria;
import br.com.mayki.listpad.databinding.ActivityDetalheListaBinding;

public class DetalheListaActivity extends AppCompatActivity {

    ActivityDetalheListaBinding activityDetalheListaBinding;

    TextView nomeCategoria;
    FloatingActionButton buttonAdicionarItem;
    TextView dataCriacao;
    ListView lista;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetalheListaBinding = ActivityDetalheListaBinding.inflate(getLayoutInflater());
        setContentView(activityDetalheListaBinding.getRoot());



        carregaCampos();

        ListaCategoria listaCategoria = (ListaCategoria) getIntent().getSerializableExtra(Constantes.LISTA_CATEGORIA);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(listaCategoria.lista.getNome().toUpperCase(Locale.ROOT));

        nomeCategoria.setText(listaCategoria.categoria.getDescricao().toUpperCase(Locale.ROOT));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dataCriacao.setText("Criado em: " + simpleDateFormat.format(listaCategoria.lista.getDataCriacao()));



    }

    private void carregaCampos() {
        nomeCategoria = activityDetalheListaBinding.activityDetalheListaTvCategoriaLista;
        buttonAdicionarItem = activityDetalheListaBinding.activityDetalheListaFbAdicionarItem;
        dataCriacao = activityDetalheListaBinding.activityDetalheListaTvDataCriacaoLista;
        lista = activityDetalheListaBinding.activityDetalheListaLista;
        toolbar = activityDetalheListaBinding.activityDetalheListaToolbar;
    }
}