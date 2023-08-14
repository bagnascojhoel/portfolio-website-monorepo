package br.com.bagnascojhoel.portfolio_website_bff.model.exception;

public class UnrecoverableError extends RuntimeException{
    public UnrecoverableError(String message, Throwable cause) {
        super(message, cause);
    }

    public UnrecoverableError(Throwable cause) {
        super(cause);
    }
}
