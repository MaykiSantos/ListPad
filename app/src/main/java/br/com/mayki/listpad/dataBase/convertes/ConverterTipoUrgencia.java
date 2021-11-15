package br.com.mayki.listpad.dataBase.convertes;

import androidx.room.TypeConverter;

import br.com.mayki.listpad.dataBase.entity.TipoUregencia;

public class ConverterTipoUrgencia {

    @TypeConverter
    public TipoUregencia paraTipoUregencia(String tipo){
        if(tipo ==null){
            return TipoUregencia.TRANQUILO;
        }
        return TipoUregencia.valueOf(tipo);
    }

    @TypeConverter
    public String paraString(TipoUregencia tipo){
        return tipo.name();
    }
}
