package com.twingly.search.exception;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * The type Operation result.
 */
@XmlRootElement(name = "operationResult")
public class OperationResult {
    @XmlAttribute(name = "resultType")
    private String resultType;
    @XmlValue
    private String message;

    /**
     * Gets result type.
     *
     * @return the result type
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * Sets result type.
     *
     * @param resultType the result type
     */
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
