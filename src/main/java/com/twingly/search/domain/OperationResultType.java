package com.twingly.search.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * The enum Operation result type.
 */
@XmlType
@XmlEnum(String.class)
public enum OperationResultType {
    /**
     * Failure operation result type.
     */
    @XmlEnumValue("failure")
    FAILURE;
}
