package utwente.team2.settings;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class ApplicationSettings {
    private static final String SECRET = "LZ_FzX6IB-sSeEScwB0XjhQaetivpLf91QzsQAAYnVI";
    private static final byte[] SECRET_BYTES = SECRET.getBytes();
    public static final SecretKey APP_KEY = Keys.hmacShaKeyFor(SECRET_BYTES);
}
