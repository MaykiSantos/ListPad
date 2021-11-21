package br.com.mayki.listpad.dataBase.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(foreignKeys = {@ForeignKey(
        entity = Categoria.class,
        parentColumns = "id",
        childColumns = "idCategoria",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index("idCategoria")})
public class Lista implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String nome;
    private String descricao;
    private TipoUregencia urgencia;
    private Date dataCriacao;
    private Integer idCategoria;

    public Lista(){}

    @Ignore
    public Lista(String nome, String descricao, TipoUregencia urgencia, Integer idCategoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.urgencia = urgencia;
        this.dataCriacao = new Date();
        this.idCategoria = idCategoria;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoUregencia getUrgencia() {
        return urgencia;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUrgencia(TipoUregencia urgencia) {
        this.urgencia = urgencia;
    }
}
