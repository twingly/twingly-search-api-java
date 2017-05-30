package com.twingly.search.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * The enum Operation result type.
 *
 * @deprecated since 3.0.0
 */
@XmlType
@XmlEnum(String.class)
@Deprecated
public enum OperationResultType {
    /**
     * Failure operation result type.
     */
    @XmlEnumValue("failure")
    FAILURE
}
