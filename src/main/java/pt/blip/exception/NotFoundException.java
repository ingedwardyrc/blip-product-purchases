package pt.blip.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value= NOT_FOUND, reason="No such Order")  // 404
public class NotFoundException extends RuntimeException {
}
