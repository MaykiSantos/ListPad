package br.com.mayki.listpad.dataBase.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        foreignKeys = {@ForeignKey(
                entity = Lista.class,
                parentColumns = "id",
                childColumns = "idLista",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index("idLista")})
public class Item implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String descricao;
    private Boolean feito;
    private Integer idLista;

    public Item(){}

    @Ignore
    public Item(String descricao, Boolean feito, Integer idLista) {
        this.descricao = descricao;
        this.feito = feito;
        this.idLista = idLista;
    }

    public Integer getIdLista() {
        return idLista;
    }

    public void setIdLista(Integer idLista) {
        this.idLista = idLista;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getFeito() {
        return feito;
    }

    public void setFeito(Boolean feito) {
        this.feito = feito;
    }
}
