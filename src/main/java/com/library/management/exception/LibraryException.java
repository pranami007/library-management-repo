package com.library.management.exception;

import com.library.management.enums.LibraryExceptionType;
import com.library.management.enums.ModuleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@Component
@Data
@AllArgsConstructor
public class LibraryException extends RuntimeException {

    @Autowired
    private static PropertiesConfig propertiesConfig;
    public int code;
    public String error;
    public String timestamp;
    private String message;
    private Long httpStatusCode;

    @Autowired
    public LibraryException(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    public LibraryException(String message) {
        super(message);
        this.message = message;
    }

    public LibraryException(int code, String errorMessage) {
        this.code = code;
        this.message = errorMessage;
    }

    public LibraryException() {
    }

    public static void throwException(ModuleType entityType, LibraryExceptionType exceptionType, String... args)
            throws LibraryException {
        String messageTemplate = getMessageTemplate(entityType, exceptionType);
        int code = CodeResolver.resolve(exceptionType);
        throw prepareTmsException(exceptionType, messageTemplate, code, args);
    }

    private static String getMessageTemplate(ModuleType entityType, LibraryExceptionType exceptionType) {
        return entityType.name().concat(".").concat(exceptionType.getValue()).toLowerCase();
    }

    private static String format(String template, String... args) {
        Optional<String> templateContent = Optional.ofNullable(propertiesConfig.getPropertyValue(template));
        if (templateContent.isPresent()) {
            return MessageFormat.format(templateContent.get(), (Object[]) args);
        }
        return String.format(template, (Object[]) args);
    }

    private static LibraryException prepareTmsException(LibraryExceptionType exceptionType, String messageTemplate, int code,
                                                    String... args) {
        switch (exceptionType) {
            case BAD_REQUEST:
            case BOOK_PRESENT_BY_ID:
            case BOOK_PRESENT_BY_TITLE:
                return new DataValidationException(code, format(messageTemplate, args));

            case BOOK_NOT_FOUND:
                return new ResourceNotFoundException(code, format(messageTemplate, args));
        }

        return new LibraryException("Something went wrong ,please try again");
    }

    private static class CodeResolver {

        public static int resolve(LibraryExceptionType exceptionType) {

            switch (exceptionType) {

                case BAD_REQUEST:
                case BOOK_PRESENT_BY_ID:
                case BOOK_PRESENT_BY_TITLE:
                    return HttpStatus.BAD_REQUEST.value();
                case BOOK_NOT_FOUND:
                    return HttpStatus.NOT_FOUND.value();
            }
            return 0;
        }
    }
}
