package br.com.mayki.listpad.dataBase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.mayki.listpad.dataBase.dao.relacionamentos.ListaCategoria;
import br.com.mayki.listpad.dataBase.entity.Lista;

@Dao
public interface ListaDAO {

    @Insert
    void salvar(Lista valor);

    @Delete
    void deletar(Lista valor);

    @Query("SELECT * FROM lista ORDER BY id DESC")
    List<ListaCategoria> buscarTodos();
}
