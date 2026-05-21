package ifpb.app_sistema_gestao_eventos.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<String> handleNaoEncontrado(
            EntidadeNaoEncontradaException e
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("O recurso solicitado não foi encontrado.");
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<String> handleRegraDeNegocio(
            RegraDeNegocioException e
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("A operação não pôde ser realizada devido a uma regra de negócio.");
    }

    @ExceptionHandler(EntidadeJaCadastradaException.class)
    public ResponseEntity<String> handleJaCadastrado(
            EntidadeJaCadastradaException e
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("O recurso informado já está cadastrado.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleIntegridade(
            DataIntegrityViolationException e
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Violação de integridade dos dados.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidacao(
            MethodArgumentNotValidException e
    ) {
        Map<String, String> erros = new HashMap<>();

        e.getBindingResult()
                .getFieldErrors()
                .forEach(err ->
                        erros.put(
                                err.getField(),
                                err.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erros);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleCredenciaisInvalidas(
            BadCredentialsException e
    ) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Email ou senha inválidos.");
    }
}