package com.twingly.search.domain;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Error.
 *
 * @since 3.0.0
 */
@XmlRootElement(name = "error")
public class Error {
    @XmlAttribute(name = "code")
    private String code;
    @XmlElement(name = "message")
    private String message;

    public Error() {
    }

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets error code.
     *
     * @param code the error code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets error message.
     *
     * @param message the error message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
