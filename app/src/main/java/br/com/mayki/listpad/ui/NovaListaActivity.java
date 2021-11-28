package br.com.mayki.listpad.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.mayki.listpad.dataBase.RoomListPad;
import br.com.mayki.listpad.dataBase.dao.ListaDAO;
import br.com.mayki.listpad.dataBase.dao.relacionamentos.ListaCategoria;
import br.com.mayki.listpad.dataBase.entity.Categoria;
import br.com.mayki.listpad.dataBase.entity.Lista;
import br.com.mayki.listpad.dataBase.entity.TipoUregencia;
import br.com.mayki.listpad.databinding.ActivityNovaListaBinding;

public class NovaListaActivity extends AppCompatActivity {

    ActivityNovaListaBinding activityNovaListaBinding;

    ListaCategoria listaCategoriaEditar;

    final String NOME_PAGINA_NOVO = "Nova lista";
    final String NOME_PAGINA_EDITAR = "Editar lista";

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

        listaDAO = RoomListPad.getInstance(this).getListaDAO();

        listaCategoriaEditar = (ListaCategoria) getIntent().getSerializableExtra(Constantes.LISTA_CATEGORIA);



        carregaCampos();
        defineToolbar();
        defineValoresDoSpinnerDeCategorias();
        defineValoresDoSpinnerDeUrgencia();
        defineEventoDeSalvarLista();

        if(listaCategoriaEditar != null){
            prencheCampos();
        }

    }

    private void prencheCampos() {
        titulo.setText(listaCategoriaEditar.lista.getNome());
        descricao.setText(listaCategoriaEditar.lista.getDescricao());
        categoria.setSelection(adapterListaCategorias.getPosition(listaCategoriaEditar.categoria));
        urgencia.setSelection(adapterListaUrgencia.getPosition(listaCategoriaEditar.lista.getUrgencia()));
    }

    private void defineEventoDeSalvarLista() {
        butaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adicionar verificação de campos vazios
                if(titulo.getText().toString().isEmpty() || descricao.getText().toString().isEmpty()){
                    Toast.makeText(NovaListaActivity.this, "Prenha os campos", Toast.LENGTH_SHORT).show();
                    ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(titulo.getWindowToken(), 0); //oculta teclado
                }else{
                    //criar nova lista e salvar
                    if(listaCategoriaEditar == null){
                    persisteLista();
                    }else{
                        listaCategoriaEditar.lista.setNome(titulo.getText().toString());
                        listaCategoriaEditar.lista.setDescricao(descricao.getText().toString());
                        listaCategoriaEditar.lista.setIdCategoria(adapterListaCategorias.getItem(categoria.getSelectedItemPosition()).getId());
                        listaCategoriaEditar.lista.setUrgencia(TipoUregencia.valueOf(adapterListaUrgencia.getItem(urgencia.getSelectedItemPosition()).toString()));

                        listaDAO.atualiza(listaCategoriaEditar.lista);
                        setResult(RESULT_OK);
                    }
                    finish();
                }
            }
        });
    }

    private void persisteLista() {
        Lista novaLista = new Lista(
                titulo.getText().toString(),
                descricao.getText().toString(),
                TipoUregencia.valueOf(adapterListaUrgencia.getItem(urgencia.getSelectedItemPosition()).toString()),
                adapterListaCategorias.getItem(categoria.getSelectedItemPosition()).getId()
        );
        listaDAO.salvar(novaLista);
    }

    private void defineToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(listaCategoriaEditar == null){
        getSupportActionBar().setTitle(NOME_PAGINA_NOVO);
        }else{
            getSupportActionBar().setTitle(NOME_PAGINA_EDITAR);
        }
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