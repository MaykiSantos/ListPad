package br.com.mayki.listpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;

import java.util.Date;

import br.com.mayki.listpad.dataBase.RoomListPad;
import br.com.mayki.listpad.dataBase.dao.CaregoriaDAO;
import br.com.mayki.listpad.dataBase.dao.ListaDAO;
import br.com.mayki.listpad.dataBase.entity.Categoria;
import br.com.mayki.listpad.dataBase.entity.Lista;
import br.com.mayki.listpad.dataBase.entity.TipoUregencia;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoomListPad db = RoomListPad.getInstance(this);
        CaregoriaDAO categoriaDao = db.getCategoriaDAO();
        categoriaDao.salvar(new Categoria("compras"));
        ListaDAO listaDao = db.getListaDAO();
        listaDao.salvar(new Lista("nomeLista", "descrição lista", TipoUregencia.MODERADO, new Date(), 1));
    }
}