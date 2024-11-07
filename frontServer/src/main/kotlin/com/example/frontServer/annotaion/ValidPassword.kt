package com.example.frontServer.annotaion

import com.example.frontServer.validator.PasswordConstraintValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [PasswordConstraintValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidPassword(
    val message: String = "Invalid password",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
    ///
)