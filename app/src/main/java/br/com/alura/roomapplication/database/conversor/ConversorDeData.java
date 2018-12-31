package br.com.alura.roomapplication.database.conversor;

import android.arch.persistence.room.TypeConverter;
import android.content.Context;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConversorDeData {

    // é necessário colocar esta notação para o ROOM saber que ele deverá utilizar este método
    // para converter os dados do tipo Calendar para Long das entidades (ou seja, os dados do tipo
    // Calendar, ele pegará o seu timestamp, que é um Long - convertendo de Calendar para Long)
    @TypeConverter
    public static Long convert(Calendar dataASerConvertida){
        if (dataASerConvertida != null){
            Date date = dataASerConvertida.getTime();
            Long timestamp = date.getTime();
            return timestamp;
        }
        return null;
    }

    @TypeConverter
    public static Calendar convert(Long timestamp){ // tempo em milissegundos
        if (timestamp != null){
            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
//        Date date = new Date(timestamp); //ouu
            date.setTime(timestamp);
            calendar.setTime(date);
            return calendar;
        }
        return null;
    }

    public static Calendar converte(String stringData){
        if (stringData != null){
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = format.parse(stringData);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                return calendar;
            } catch (ParseException e) {
                e.printStackTrace();
//                return null;
            }
        }
        return null;
    }

    public static String converte(Calendar data){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if (data != null){
            String stringData = format.format(data.getTime());
            return stringData;
        }
        return null;
    }
}
