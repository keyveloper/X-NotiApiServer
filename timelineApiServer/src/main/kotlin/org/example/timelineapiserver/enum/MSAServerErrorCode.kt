package org.example.timelineapiserver.enum

enum class MSAServerErrorCode {
    SUCCESS,
    DUPLICATED_ID,
    INVALID_ID,
    UNKNOWN_ERROR,

    // Valid
    NOT_NULL,
    NOT_BLANK,
    NOT_PATTERN,
}