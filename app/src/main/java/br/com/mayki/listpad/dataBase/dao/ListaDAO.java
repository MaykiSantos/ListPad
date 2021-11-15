package br.com.mayki.listpad.dataBase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import br.com.mayki.listpad.dataBase.entity.Lista;

@Dao
public interface ListaDAO {

    @Insert
    void salvar(Lista valor);

    @Delete
    void deletar(Lista valor);
}
