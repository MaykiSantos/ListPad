package br.com.mayki.listpad.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.mayki.listpad.R;
import br.com.mayki.listpad.dataBase.RoomListPad;
import br.com.mayki.listpad.dataBase.dao.ListaDAO;
import br.com.mayki.listpad.dataBase.entity.Categoria;
import br.com.mayki.listpad.dataBase.entity.Lista;
import br.com.mayki.listpad.dataBase.entity.TipoUregencia;
import br.com.mayki.listpad.databinding.ActivityNovaListaBinding;

public class NovaListaActivity extends AppCompatActivity {

    ActivityNovaListaBinding activityNovaListaBinding;
    final String NOME_PAGINA = "Nova Lista";

    ArrayAdapter<Categoria> adapterListaCategorias;
    ArrayAdapter<TipoUregencia> adapterListaUrgencia;
    ListaDAO listaDAO;

    EditText titulo;
    EditText descricao;
    Spinner categoria;
    Spinner urgencia;
    Button butaoSalvar;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNovaListaBinding = ActivityNovaListaBinding.inflate(getLayoutInflater());
        setContentView(activityNovaListaBinding.getRoot());

        carregaCampos();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(NOME_PAGINA);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        defineValoresDoSpinnerDeCategorias();
        defineValoresDoSpinnerDeUrgencia();

        listaDAO = RoomListPad.getInstance(this).getListaDAO();

        butaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adicionar verificação de campos vazios


                //criar nova lista e salvar
                Lista novaLista = new Lista(
                        titulo.getText().toString(),
                        descricao.getText().toString(),
                        TipoUregencia.valueOf(adapterListaUrgencia.getItem(urgencia.getSelectedItemPosition()).toString()),
                        adapterListaCategorias.getItem(categoria.getSelectedItemPosition()).getId()
                );
                listaDAO.salvar(novaLista);
                finish();


            }
        });

    }

    private void defineValoresDoSpinnerDeUrgencia() {
        adapterListaUrgencia = new ArrayAdapter<TipoUregencia>(
                this,
                android.R.layout.simple_list_item_1,
                TipoUregencia.values()
        );
        urgencia.setAdapter(adapterListaUrgencia);
    }

    private void defineValoresDoSpinnerDeCategorias() {
        adapterListaCategorias = new ArrayAdapter<Categoria>(
                this,
                android.R.layout.simple_list_item_1,
                RoomListPad.getInstance(this).getCategoriaDAO().buscaCategorias()
        );
        categoria.setAdapter(adapterListaCategorias);
    }

    private void carregaCampos() {
        titulo = activityNovaListaBinding.activityNovaListaEtTitulo;
        descricao = activityNovaListaBinding.activityNovaListaEtDescricao;
        categoria = activityNovaListaBinding.activityNovaListaSpinnerCategoria;
        urgencia = activityNovaListaBinding.activityNovaListaSpinnerUrgencia;
        butaoSalvar = activityNovaListaBinding.activityNovaListaBtnSalvar;
        toolbar = activityNovaListaBinding.activityNovaListaToolbar;
    }
}