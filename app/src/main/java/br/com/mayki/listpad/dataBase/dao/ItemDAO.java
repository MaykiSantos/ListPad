package br.com.mayki.listpad.dataBase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.mayki.listpad.dataBase.entity.Item;

@Dao
public interface ItemDAO {

    @Insert
    void salvar(Item velor);

    @Delete
    void deletar(Item valor);

    @Update
    void atualizar(Item valor);

    @Query("SELECT * FROM item WHERE idLista == :idLista")
    List<Item> buscarPorLista(int idLista);
}
