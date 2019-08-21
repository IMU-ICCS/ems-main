package eu.melodic.upperware.guibackend.service.user;

import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class PassayStrongPasswordGenerator implements StrongPasswordGenerator {

    private PasswordGenerator generator;
    private CharacterRule[] characterRules;
    private int passwordLength;

    public PassayStrongPasswordGenerator() {

        generator = new PasswordGenerator();

        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        characterRules = new CharacterRule[]{lowerCaseRule, upperCaseRule, digitRule};

        Random random = new Random();
        passwordLength = random.nextInt(8) + 8; //rand.nextInt((max - min) + 1) + min; max=15, min=8
    }

    @Override
    public String generatePassword() {
        return generator.generatePassword(passwordLength, characterRules);
    }
}
