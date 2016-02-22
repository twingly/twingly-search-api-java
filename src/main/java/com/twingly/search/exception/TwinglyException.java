package com.twingly.search.exception;

/**
 * Custom runtime Twingly Exception
 */
public class TwinglyException extends RuntimeException {
    /**
     * Instantiates a new Twingly exception.
     */
    public TwinglyException() {
        super();
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param blogStream the blog stream
     */
    public TwinglyException(BlogStream blogStream) {
        this(String.format("resultType:%s, message:%s", blogStream.getOperationResult().getResultType(), blogStream.getOperationResult().getMessage()));
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param message the message
     */
    public TwinglyException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TwinglyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param cause the cause
     */
    public TwinglyException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected TwinglyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
