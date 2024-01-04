package ru.sber.backend.constants;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Генератор паролей
 */
public class GeneratePassword {
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%?";

    /**
     * Генерирует случайный пароль заданной длины.
     *
     * @param length длина пароля
     * @return сгенерированный пароль
     */
    public static String generateRandomPassword(int length) {
        List<Character> passwordChars = new ArrayList<>();
        Random random = new SecureRandom();

        passwordChars.add(LOWERCASE_CHARACTERS.charAt(random.nextInt(LOWERCASE_CHARACTERS.length())));
        passwordChars.add(UPPERCASE_CHARACTERS.charAt(random.nextInt(UPPERCASE_CHARACTERS.length())));
        passwordChars.add(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        passwordChars.add(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        for (int i = 4; i < length; i++) {
            String charGroup = selectRandomCharGroup(random);
            passwordChars.add(charGroup.charAt(random.nextInt(charGroup.length())));
        }

        StringBuilder password = new StringBuilder();
        while (!passwordChars.isEmpty()) {
            int randomIndex = random.nextInt(passwordChars.size());
            password.append(passwordChars.remove(randomIndex));
        }

        return password.toString();
    }

    /**
     * Выбирает случайную группу символов из доступных.
     *
     * @param random экземпляр генератора случайных чисел
     * @return строка с выбранной группой символов
     */
    private static String selectRandomCharGroup(Random random) {
        int randomIndex = random.nextInt(4);
        switch (randomIndex) {
            case 0:
                return LOWERCASE_CHARACTERS;
            case 1:
                return UPPERCASE_CHARACTERS;
            case 2:
                return NUMBERS;
            case 3:
                return SPECIAL_CHARACTERS;
            default:
                return LOWERCASE_CHARACTERS;
        }
    }
}
