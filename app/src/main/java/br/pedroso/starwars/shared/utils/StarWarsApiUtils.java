package br.pedroso.starwars.shared.utils;

import android.webkit.URLUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by felipe on 02/03/17.
 */
public class StarWarsApiUtils {
    // TODO: improve the regex to be able to get the last part of the URL (people id)
    private static final String STAR_WARS_API_REGEX = "https?://swapi\\.co/api/([A-Za-z]*)/?(\\d*)?/?";
    private static final Pattern STAR_WARS_API_PATTERN = Pattern.compile(STAR_WARS_API_REGEX);
    private static final String STAR_WARS_API_PATH_PEOPLE = "people";

    public static boolean isStarWarsPeopleApiUrl(String str) {
        if (URLUtil.isValidUrl(str)) {
            Matcher matcher = STAR_WARS_API_PATTERN.matcher(str);

            if (matcher.matches() && matcher.groupCount() > 0) {
                String path = matcher.group(1);

                return path.toLowerCase().equals(STAR_WARS_API_PATH_PEOPLE);
            }
        }

        return false;
    }
}
