package pro.siberian.siberianproapplication.utils;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Вараздат on 23.11.2017.
 */

public class StringArrayConverter {

    public final static String SEPARATOR = "__,__";


    @TypeConverter
    public static String convertStringArrToString(List<String> months) {
        String str = "";
        int length = months.size();
        for(int i = 0; i < length-2 ;i++){
            if(i == length-1){
                str = str + months.get(length-1);
               continue;
            }
            str = str + months.get(i) + SEPARATOR ;
        }
        return str;
    }


    @TypeConverter
    public static List<String> convertStringToStringArr(String months) {
        String[] mMonths = months.split(SEPARATOR);
        List<String> monthsList = new ArrayList<>();
        for(String month : mMonths){
            monthsList.add(month);
        }
        return monthsList;
    }

}
