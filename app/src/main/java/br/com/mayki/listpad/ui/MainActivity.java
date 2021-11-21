package br.com.mayki.listpad.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.mayki.listpad.R;
import br.com.mayki.listpad.adapter.ListaAdapter;
import br.com.mayki.listpad.dataBase.RoomListPad;
import br.com.mayki.listpad.dataBase.dao.ListaDAO;
import br.com.mayki.listpad.dataBase.dao.relacionamentos.ListaCategoria;
import br.com.mayki.listpad.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    ListaAdapter adapterLista;
    ListaDAO listaDAO;
    ListView listView;
    FloatingActionButton floatingActionButton;
    final String NOME_PAGINA = "ListPad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        Toolbar barraSuperior = (Toolbar) activityMainBinding.activityMainToolbar;
        setSupportActionBar(barraSuperior);
        barraSuperior.setTitle(NOME_PAGINA);

        CarregaCampos();

        defineEventoParaChamarActivityNovaLista();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this, DetalheListaActivity.class);
                intent.putExtra(Constantes.LISTA_CATEGORIA, ((ListaAdapter)adapter.getAdapter()).getItem(i));

                startActivity(intent);
            }
        });


    }

    private void defineEventoParaChamarActivityNovaLista() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NovaListaActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaListaTelaprinipal();
    }

    private void atualizaListaTelaprinipal() {
        listaDAO = RoomListPad.getInstance(this).getListaDAO();
        adapterLista = new ListaAdapter(this, listaDAO.buscarTodos());
        listView.setAdapter(adapterLista);
        registerForContextMenu(listView);
    }

    private void CarregaCampos() {
        listView = activityMainBinding.activityMainListView;
        floatingActionButton = activityMainBinding.activityMainFbAdicionarLista;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //realiza a verificação de qual item do menu foi precionado e executa determinada ação
        switch (item.getItemId()){
            case R.id.menu_principal_Sobre:
                startActivity(new Intent(this, SobreActivity.class));
                return true;
            case R.id.menu_principal_novaCategoria:
                startActivity(new Intent(this, NovaCategoriaActivity.class));
            default:
                return false;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_de_contexto_editar:
                //feginir aqui a opção de editar
                Toast.makeText(this, "Editar Clicado", Toast.LENGTH_LONG).show();
                return false;
            case R.id.menu_de_contexto_excluir:
                //excluir lista
                int posicaoElemento = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
                ListaCategoria l = adapterLista.getItem(posicaoElemento);
                RoomListPad.getInstance(this).getListaDAO().deletar(l.lista);
                atualizaListaTelaprinipal();
                return  true;
            default:
                return false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //define o menu principal na toobar
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_de_contexto, menu);
    }
}