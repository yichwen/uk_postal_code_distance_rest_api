package io.yichwen.exception;

public class PostalCodeNotFoundException extends RuntimeException {

    private static final String message = "Postal code %s is not found";
    private final String postcode;

    public PostalCodeNotFoundException(String postcode) {
        super(String.format(message, postcode));
        this.postcode = postcode;
    }

    public PostalCodeNotFoundException(String postcode, String message) {
        super(message);
        this.postcode = postcode;
    }
}

