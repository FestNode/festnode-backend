package com.festnode.festnode.util;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordGenerator {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:',.<>?/";

    private static final SecureRandom RANDOM = new SecureRandom();

    public String generateStrongPassword() {
        StringBuilder password = new StringBuilder();

        // Add one random character from each category
        password.append(getRandomCharacter(UPPERCASE_LETTERS));
        password.append(getRandomCharacter(LOWERCASE_LETTERS));
        password.append(getRandomCharacter(NUMBERS));
        password.append(getRandomCharacter(SPECIAL_CHARACTERS));

        // Fill the remaining characters randomly from all categories
        String allCharacters = UPPERCASE_LETTERS + LOWERCASE_LETTERS + NUMBERS + SPECIAL_CHARACTERS;
        for (int i = 4; i < 8; i++) {
            password.append(getRandomCharacter(allCharacters));
        }

        // Shuffle the password to randomize the order
        return shuffleString(password.toString());
    }

    private String getRandomCharacter(String characters) {
        int index = RANDOM.nextInt(characters.length());
        return String.valueOf(characters.charAt(index));
    }

    private String shuffleString(String input) {
        char[] array = input.toCharArray();
        for (int i = array.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new String(array);
    }
}
