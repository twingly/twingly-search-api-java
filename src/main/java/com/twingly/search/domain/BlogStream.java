package com.twingly.search.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Blog stream.
 *
 * @deprecated since 1.1.0
 */
@Deprecated
@XmlRootElement(name = "blogstream", namespace = "http://www.twingly.com")
public class BlogStream {
    @XmlElement(name = "operationResult")
    private OperationResult operationResult;

    /**
     * Gets operation result.
     *
     * @return the operation result
     */
    public OperationResult getOperationResult() {
        return operationResult;
    }

    /**
     * Sets operation result.
     *
     * @param operationResult the operation result
     */
    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

}
