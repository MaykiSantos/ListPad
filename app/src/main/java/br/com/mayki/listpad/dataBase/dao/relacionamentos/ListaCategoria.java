package br.com.mayki.listpad.dataBase.dao.relacionamentos;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;

import br.com.mayki.listpad.dataBase.entity.Categoria;
import br.com.mayki.listpad.dataBase.entity.Lista;

public class ListaCategoria implements Serializable {
    @Embedded
    public Lista lista;
    @Relation(parentColumn = "idCategoria",
    entityColumn = "id")
    public Categoria categoria;
}
