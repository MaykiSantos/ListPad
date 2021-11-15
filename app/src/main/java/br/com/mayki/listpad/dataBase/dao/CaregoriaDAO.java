package br.com.mayki.listpad.dataBase.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import br.com.mayki.listpad.dataBase.entity.Categoria;

@Dao
public interface CaregoriaDAO {

    @Insert
    void salvar(Categoria categoria);
}
