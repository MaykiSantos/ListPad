package br.com.mayki.listpad.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.mayki.listpad.dataBase.RoomListPad;
import br.com.mayki.listpad.dataBase.dao.ItemDAO;
import br.com.mayki.listpad.dataBase.dao.relacionamentos.ListaCategoria;
import br.com.mayki.listpad.dataBase.entity.Item;
import br.com.mayki.listpad.databinding.ActivityNovoItemBinding;

public class NovoItemActivity extends AppCompatActivity {

    ActivityNovoItemBinding activityNovoItemBinding;
    ItemDAO itemDAO;

    ListaCategoria listaCategoria;
    Item item;

    EditText descricao;
    Button btnSalvar;
    Toolbar toolbar;

    final String TITULO_PAGINA_NOVO = "Novo item";
    final String TITULO_PAGINA_EDITAR = "Editar item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNovoItemBinding = ActivityNovoItemBinding.inflate(getLayoutInflater());
        setContentView(activityNovoItemBinding.getRoot());
        itemDAO = RoomListPad.getInstance(NovoItemActivity.this).getItemDAO();

        listaCategoria = (ListaCategoria) getIntent().getSerializableExtra(Constantes.LISTA_CATEGORIA); //objeto enviado quando a pagina é utilizada para adicionar um novo Item
        item = (Item) getIntent().getSerializableExtra(Constantes.ITEM); //objeto enviado quando a pagina é utilizada para editar um Item

        carregaCompos();
        defineToolbar();
        defineEventoDeSalvarNovoItem();

        if(item != null){
            descricao.setText(item.getDescricao().toString());
        }

    }


    private void defineEventoDeSalvarNovoItem() {
        this.btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(descricao.getText().toString().isEmpty()){
                    Toast.makeText(NovoItemActivity.this, "Preencha o campo de descrição", Toast.LENGTH_LONG).show();
                }else{
                    if(item == null){
                        //bloco que dalva novo item
                        Item item  = new Item(descricao.getText().toString(), false, listaCategoria.lista.getId());
                        itemDAO.salvar(item);
                    }else{
                        //bloco edita item existente
                        item.setDescricao(descricao.getText().toString());
                        itemDAO.atualizar(item);
                        setResult(RESULT_OK);
                    }
                    finish();
                }
            }
        });
    }

    private void defineToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(TITULO_PAGINA_NOVO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void carregaCompos() {
        this.descricao = activityNovoItemBinding.activityNovoItemEtDescricao;
        this.btnSalvar = activityNovoItemBinding.activityNovoItemBtnSalvar;
        this.toolbar = activityNovoItemBinding.activityNovoItemToobar;
    }
}