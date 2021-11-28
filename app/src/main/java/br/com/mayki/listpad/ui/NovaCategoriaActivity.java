package br.com.mayki.listpad.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.mayki.listpad.dataBase.RoomListPad;
import br.com.mayki.listpad.dataBase.dao.CaregoriaDAO;
import br.com.mayki.listpad.dataBase.entity.Categoria;
import br.com.mayki.listpad.databinding.ActivityNovaCategoriaBinding;

public class NovaCategoriaActivity extends AppCompatActivity {

    final String NOME_PAGINA = "Nova Categoria";
    ActivityNovaCategoriaBinding activityNovaCategoriaBinding;
    Button botaoSalvar;
    EditText campoDescricaoCategoria;
    CaregoriaDAO categoriaDao;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityNovaCategoriaBinding = ActivityNovaCategoriaBinding.inflate(getLayoutInflater());
        setContentView(activityNovaCategoriaBinding.getRoot());

        categoriaDao = RoomListPad.getInstance(this).getCategoriaDAO();

        carregaCampos();
        defineToolbar();
        defineEventoDeSalvarCategoria();

    }

    private void defineEventoDeSalvarCategoria() {
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campoDescricaoCategoria.getText().toString().isEmpty()){
                    Toast.makeText(NovaCategoriaActivity.this, "Preencha o campo de descrição", Toast.LENGTH_LONG).show();
                }else{
                    categoriaDao.salvar(new Categoria(campoDescricaoCategoria.getText().toString()));
                    campoDescricaoCategoria.getText().clear();
                    Toast.makeText(NovaCategoriaActivity.this, "Categoria cadastrada", Toast.LENGTH_LONG).show();
                }
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(campoDescricaoCategoria.getWindowToken(), 0); //oculta teclado
                campoDescricaoCategoria.clearFocus();

            }
        });
    }

    private void defineToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(NOME_PAGINA);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void carregaCampos() {
        botaoSalvar = activityNovaCategoriaBinding.activityNovaCategoriaBtnSalvar;
        campoDescricaoCategoria = activityNovaCategoriaBinding.activityNovaCategoriaEtDescricao;
        toolbar = activityNovaCategoriaBinding.activityNovaCategoriaToobar;
    }
}