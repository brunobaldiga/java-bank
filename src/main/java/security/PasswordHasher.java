package security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {
    private static final SecureRandom random = new SecureRandom();
    private static final int iterations = 600000;

    private PasswordHasher() {}

    public static String hash(String password) {
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        byte[] digest = derive(password, salt);

        return "pbkdf2-sha256" + ":" + iterations + ":"
                + Base64.getEncoder().encodeToString(salt)
                + ":"
                + Base64.getEncoder().encodeToString(digest);
    }

    public static boolean verify(String password, String hash) {
        if (password == null || hash == null) return false;

        try {
            String[] parts = hash.split(":");

            if (parts.length != 4
                || !parts[0].equals("pbkdf2-sha256")
                || !parts[1].equals(String.valueOf(iterations))) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] expected = Base64.getDecoder().decode(parts[3]);

            return MessageDigest.isEqual(expected, derive(password, salt));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] derive(String password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                iterations,
                256
        );

        try {
            return SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec)
                    .getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao processar senha", e);
        } finally {
            spec.clearPassword();
        }
    }
}
