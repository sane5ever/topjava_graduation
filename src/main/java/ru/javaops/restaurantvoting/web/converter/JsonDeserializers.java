package ru.javaops.restaurantvoting.web.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.javaops.restaurantvoting.config.WebSecurityConfig;

import java.io.IOException;

public class JsonDeserializers {
    public static class PasswordDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ObjectCodec oc = jsonParser.getCodec();
            JsonNode node = oc.readTree(jsonParser);
            String rawPassword = node.asText();
            return WebSecurityConfig.PASSWORD_ENCODER.encode(rawPassword);

        }
    }
}
