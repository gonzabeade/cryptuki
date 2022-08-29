package ar.edu.itba.paw.exception;

public class NoSuchCryptocurrencyException extends RuntimeException {

    private String code;

    public NoSuchCryptocurrencyException(String code) {
        super("Cryptocurrency with code does not exist. Code: "+code);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
