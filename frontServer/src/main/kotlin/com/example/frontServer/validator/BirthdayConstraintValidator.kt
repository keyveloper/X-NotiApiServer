package com.example.frontServer.validator

import com.example.frontServer.annotaion.ValidBirthday
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate

class BirthdayConstraintValidator: ConstraintValidator<ValidBirthday, LocalDate> {
    private var minYear: Int = 2012

    override fun initialize(constraintAnnotaion: ValidBirthday) {
        this.minYear = constraintAnnotaion.minYear
    }

    override fun isValid(birtDay: LocalDate?, context: ConstraintValidatorContext?): Boolean {
        if (birtDay == null) return false

        return birtDay.isBefore(LocalDate.of(minYear, 1, 1)) && birtDay.isBefore(LocalDate.now())
    }//
}