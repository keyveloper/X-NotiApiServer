package com.example.frontServer.annotaion

import com.example.frontServer.validator.BirthdayConstraintValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [BirthdayConstraintValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidBirthday(
    val message: String = "Birth date is invalid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val minYear: Int = 2012
    //
)
