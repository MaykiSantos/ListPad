package br.com.mayki.listpad.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
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

        defineToolbar();
        carregaCampos();
        populaListaComAdapter();
        defineEventoParaChamarActivityNovaLista();
        defieEventoParaChamarActivityDetalheLista();
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaLista();
    }

    private void defieEventoParaChamarActivityDetalheLista() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetalheListaActivity.class);
                intent.putExtra(Constantes.LISTA_CATEGORIA, ((ListaAdapter)adapter.getAdapter()).getItem(i));

                startActivity(intent);
            }
        });
    }

    private void defineToolbar() {
        Toolbar barraSuperior = (Toolbar) activityMainBinding.activityMainToolbar;
        setSupportActionBar(barraSuperior);
        barraSuperior.setTitle(NOME_PAGINA);

    }

    private void defineEventoParaChamarActivityNovaLista() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NovaListaActivity.class));
            }
        });
    }



    private void populaListaComAdapter() {
        listaDAO = RoomListPad.getInstance(this).getListaDAO();
        adapterLista = new ListaAdapter(this, listaDAO.buscarTodos());
        listView.setAdapter(adapterLista);
        registerForContextMenu(listView);
    }

    private void carregaCampos() {
        listView = activityMainBinding.activityMainListView;
        floatingActionButton = activityMainBinding.activityMainFbAdicionarLista;
    }

    private void atualizaLista(){
        adapterLista.clear();
        adapterLista.addAll(listaDAO.buscarTodos());
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
                int posicaoElemento = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                Intent intent = new Intent(this, NovaListaActivity.class);
                intent.putExtra(Constantes.LISTA_CATEGORIA, adapterLista.getItem(posicaoElemento));
                startActivity(intent);

                Toast.makeText(this, "Editar Clicado", Toast.LENGTH_LONG).show();
                return false;
            case R.id.menu_de_contexto_excluir:
                //excluir lista
                exiteDialog(item);
                return true;

            default:
                return false;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populaListaComAdapter();
    }

    private void exiteDialog(@NonNull MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Removendo lista")
                .setMessage("Tem certeza que quer remover esta lista?")
                .setPositiveButton("Sim", (dialogInterface, i) -> excluiListaSelecionada(item))
                .setNegativeButton("não", null).show();
    }

    private void excluiListaSelecionada(@NonNull MenuItem item) {
        int posicaoElemento = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
        ListaCategoria l = adapterLista.getItem(posicaoElemento);
        RoomListPad.getInstance(this).getListaDAO().deletar(l.lista);
        populaListaComAdapter();
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