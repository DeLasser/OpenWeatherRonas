package ru.mininn.openweather.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class WeatherDeserializer: JsonDeserializer<Weather> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Weather {
        var weather = Weather()
        if (json != null) {
            val jsonObject = json.asJsonObject
            weather = Weather(
                    jsonObject.get("name").asString,
                    jsonObject.get("weather").asJsonArray.get(0).asJsonObject.get("main").asString,
                    jsonObject.get("weather").asJsonArray.get(0).asJsonObject.get("description").asString,
                    jsonObject.get("weather").asJsonArray.get(0).asJsonObject.get("icon").asString,
                    jsonObject.get("main").asJsonObject.get("temp").asDouble,
                    jsonObject.get("main").asJsonObject.get("pressure").asInt,
                    jsonObject.get("main").asJsonObject.get("humidity").asInt,
                    if (jsonObject.get("rain") == null) {
                        0
                    } else {
                        jsonObject.get("rain").asJsonObject.get("3h").asInt
                    },
                    jsonObject.get("wind").asJsonObject.get("speed").asDouble,
                    jsonObject.get("wind").asJsonObject.get("deg").asInt
            )
        }
        return weather
    }
}