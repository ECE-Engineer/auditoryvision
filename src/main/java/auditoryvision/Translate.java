package auditoryvision;

/**
 * Created by Kyle on 2/11/2017.
 * @author Kyle Zeller
 */

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.google.gson.*;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;


public class Translate {
    private LanguageTranslator service;
    private String username;
    private String password;

    Translate(String user, String pass) {
        username = user;
        password = pass;
        service = new LanguageTranslator();
        service.setUsernameAndPassword(username, password);
    }

    public String convert(String text, Language destinationLanguage) {  //input text and language
        TranslationResult result = service.translate(text,  Language.ENGLISH, destinationLanguage).execute();

        JsonParser parser = new JsonParser();
        JsonObject obj1 = parser.parse(result.toString()).getAsJsonObject();
        JsonArray array = obj1.getAsJsonArray("translations");

        if (array.size() != 0) {
            JsonElement temp = array.get(0);
            JsonObject obj2 = temp.getAsJsonObject();

            String originLanguage = "";
            if (!obj2.get("translation").isJsonNull()) {
                originLanguage = obj2.get("translation").toString();
            }

            return originLanguage;
        }
        else {
            return "";
        }
    }

    public static void main(String[] args) {
        Translate test = new Translate("6d3c8cc1-672c-4b51-95b7-5eb8a208b099", "S6EJ6BaJFoI6");
        System.out.println(test.convert("this is a test", Language.FRENCH));
    }
}
