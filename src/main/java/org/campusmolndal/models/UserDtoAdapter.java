package org.campusmolndal.models;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.List;

public class UserDtoAdapter implements JsonSerializer<UserDto>, JsonDeserializer<UserDto> {

    @Override
    public JsonElement serialize(UserDto userDto, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", userDto.getId());
        jsonObject.addProperty("username", userDto.getUsername());
        jsonObject.add("authorities", context.serialize(userDto.getAuthorities()));
        return jsonObject;
    }

    @Override
    public UserDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        String username = jsonObject.get("username").getAsString();
        List<String> authorities = context.deserialize(jsonObject.get("authorities"), List.class);
        return new UserDto(id, username, authorities);
    }
}
