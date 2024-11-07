package com.example.frontServer.validator

import com.example.frontServer.annotaion.ValidPassword
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.passay.*

class PasswordConstraintValidator : ConstraintValidator<ValidPassword, String> {

    override fun isValid(password: String?, context: ConstraintValidatorContext): Boolean {
        if (password == null) return false

        val passwordValidator = PasswordValidator(
            listOf(
                LengthRule(8, 16),
                CharacterRule(EnglishCharacterData.UpperCase, 1),
                CharacterRule(EnglishCharacterData.LowerCase, 1),
                CharacterRule(EnglishCharacterData.Digit, 1),
                CharacterRule(EnglishCharacterData.Special, 1),
                WhitespaceRule()
            )
        )

        val result = passwordValidator.validate(PasswordData(password))
        if (result.isValid) {
            return true
        }

        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(
            result.details.joinToString(", ") { it.errorCode }
        ).addConstraintViolation()

        return false
    }//
}