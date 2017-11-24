package pro.siberian.siberianproapplication.utils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import pro.siberian.siberianproapplication.data.Weather;

/**
 * Created by Вараздат on 19.11.2017.
 */

public class WeatherDeserializer implements JsonDeserializer<Weather>
{
    @Override
    public Weather deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        JsonElement main = je.getAsJsonObject().get(JsonHelper.MAIN);
        return new Gson().fromJson(main, Weather.class);
    }
}