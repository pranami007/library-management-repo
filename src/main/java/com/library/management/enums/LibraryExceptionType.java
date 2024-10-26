package com.library.management.enums;

public enum LibraryExceptionType {

    BAD_REQUEST("bad.request"),

    BOOK_NOT_FOUND("book.not.found"),

    BOOK_PRESENT_BY_ID("book.already.present.by.id"),

    BOOK_PRESENT_BY_TITLE("book.already.present.by.title");

    String value;

    LibraryExceptionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
