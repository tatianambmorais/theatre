    package com.example.threatre.exception;

    import com.example.threatre.dto.ErrorResponseDTO;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;

    import java.time.LocalDateTime;
    import java.util.HashMap;
    import java.util.Map;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
            // Aqui estamos criando o record com os campos necessários
            ErrorResponseDTO error = new ErrorResponseDTO(
                    HttpStatus.NOT_FOUND.value(),  // status: 404
                    "Recurso não encontrado",      // error: "Recurso não encontrado"
                    ex.getMessage()                // message: a mensagem da exceção
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
            ErrorResponseDTO error = new ErrorResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), // status: 400
                    "Parâmetro inválido",           // error: "Parâmetro inválido"
                    ex.getMessage()                 // message: a mensagem da exceção
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Object> handleGeneric(Exception ex) {
            // Para exceções genéricas, estamos criando uma resposta com Map
            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            body.put("error", "Erro interno");
            body.put("message", "Erro interno");

            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
