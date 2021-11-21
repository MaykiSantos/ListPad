package br.com.mayki.listpad.dataBase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.mayki.listpad.dataBase.entity.Categoria;

@Dao
public interface CaregoriaDAO {

    @Insert
    void salvar(Categoria categoria);

    @Query("SELECT descricao FROM categoria")
    List<String> buscaNomesDasCategorias();

    @Query("SELECT * FROM categoria")
    List<Categoria>buscaCategorias();
}
