package br.com.mayki.listpad.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.mayki.listpad.dataBase.convertes.ConverterDate;
import br.com.mayki.listpad.dataBase.convertes.ConverterTipoUrgencia;
import br.com.mayki.listpad.dataBase.dao.CaregoriaDAO;
import br.com.mayki.listpad.dataBase.dao.ItemDAO;
import br.com.mayki.listpad.dataBase.dao.ListaDAO;
import br.com.mayki.listpad.dataBase.entity.Categoria;
import br.com.mayki.listpad.dataBase.entity.Item;
import br.com.mayki.listpad.dataBase.entity.Lista;

@Database(entities = {Lista.class, Item.class, Categoria.class}, version = 1, exportSchema = false)
@TypeConverters({ConverterTipoUrgencia.class, ConverterDate.class})
public abstract class RoomListPad extends RoomDatabase {

    public static final String NOME_DO_BANCO_DE_DADOS = "ListPad.db";

    public static RoomListPad getInstance(Context contexto){
        return Room.databaseBuilder(contexto, RoomListPad.class, NOME_DO_BANCO_DE_DADOS).fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    public abstract ListaDAO getListaDAO();

    public abstract ItemDAO getItemDAO();

    public abstract CaregoriaDAO getCategoriaDAO();
}
