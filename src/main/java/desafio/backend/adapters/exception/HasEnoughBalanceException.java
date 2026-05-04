package desafio.backend.adapters.exception;

public class HasEnoughBalanceException extends RuntimeException{
    public HasEnoughBalanceException(String message) {
        super(message);
    }
}
