package br.com.mayki.listpad.dataBase.convertes;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ConverterDate {
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @TypeConverter
    public Date paraDate(String valor) {
        try {
            return this.format.parse(valor);
        } catch (ParseException e) {
            return new Date();
        }
    }

    @TypeConverter
    public String paraString(Date valor){
        return this.format.format(valor);
    }

}
