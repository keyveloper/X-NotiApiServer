package com.example.notiApiServer.enum

enum class MSAServerErrorCode(val code: Int) { // server별로 만들면 details도 다 따로 만들어야 함
    SUCCESS(1),
    UNKNOWN_ERROR(9999),

    // DB integrity
    DUPLICATED_ID(10000),
    INVALID_ID(10001),

    // Valid
    NOT_NULL(20000),
    NOT_BLANK(20001),
    NOT_PATTERN(20002);

    companion object {
        /**
         * 숫자 에러 코드로부터 MSAServerErrorCode를 반환합니다.
         * 매칭되지 않는 경우 null을 반환합니다.
         */
        fun fromCode(code: Int): MSAServerErrorCode? =
            entries.find { it.code == code }
    }
}