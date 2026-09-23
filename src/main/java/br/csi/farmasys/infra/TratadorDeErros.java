package br.csi.farmasys.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Tratamento de erros centralizado de toda a API (Aula 2).
 * Nenhuma outra classe trata excecao: Controllers e Services apenas lancam (throw).
 */
@RestControllerAdvice
public class TratadorDeErros {

    /**
     * Entidade nao encontrada -> 404 Not Found.
     * Sem este tratamento o padrao do Spring Data vazaria como 500.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    /**
     * Erros de validacao do @Valid (Bean Validation) -> 400 Bad Request
     * com os dados dos campos invalidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
        List<FieldError> erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(
            erros.stream().map(DadosErroValidacao::new).toList()
        );
    }

    /**
     * Requisicao invalida por regra de negocio (ex.: estoque insuficiente) -> 400 Bad Request.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DadosErro> tratarErro400(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new DadosErro(ex.getMessage()));
    }

    public record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    public record DadosErro(String mensagem) {
    }
}
