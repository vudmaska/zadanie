package sk.ness.academy.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class NoContentException extends RuntimeException {

    public NoContentException(String message) {
        super(message);
    }

}
