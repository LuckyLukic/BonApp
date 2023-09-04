package BonApp.BonApp.exceptions;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorPayloadList handleValidationErrors(MethodArgumentNotValidException e) {
		List<String> errors = e.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage())
				.toList();

		return new ErrorPayloadList("Ci sono errori nel payload", new Date(), errors);
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorPayload handleBadRequest(BadRequestException e) {
		return new ErrorPayload(e.getMessage(), new Date());
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorPayload handleNotFound(NotFoundException e) {
		return new ErrorPayload(e.getMessage(), new Date());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorPayload handleForbidden(AccessDeniedException e) {
		return new ErrorPayload("Non hai accesso a questo endpoint", new Date());
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorPayload handleUnauthorized(UnauthorizedException e) {
		return new ErrorPayload(e.getMessage(), new Date());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorPayload handleGeneric(Exception e) {
		log.error(e.getMessage());
		e.printStackTrace();
		return new ErrorPayload("Errore generico", new Date());
	}
}
