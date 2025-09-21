// 代码生成时间: 2025-09-21 21:42:35
package com.example.hash;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Main class for the hash calculation tool.
 */
@QuarkusMain
public class HashCalculationTool implements QuarkusApplication {

    private static final String HASH_ALGORITHM = "SHA-256";

    @Override
    public int run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the text to calculate its hash value: ");
        String inputText = scanner.nextLine();
        scanner.close();

        try {
            String hashValue = calculateHash(inputText);
            System.out.println("The hash value is: " + hashValue);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Hash algorithm not found: " + e.getMessage());
            return 1; // Non-zero exit code for error
        }
        return 0; // Zero exit code for success
    }

    /**
     * Calculates the hash value of the given text using the specified algorithm.
     * 
     * @param text The text to calculate the hash for.
     * @return The calculated hash value as a hexadecimal string.
     * @throws NoSuchAlgorithmException If the specified hash algorithm is not available.
     */
    private String calculateHash(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        byte[] hashBytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String... args) {
        new HashCalculationTool().run(args);
    }
}
