package com.twingly.search.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents currently supported content types.
 */
@XmlType
@XmlEnum(String.class)
public enum ContentType {
    /**
     * Blog content type.
     */
    @XmlEnumValue("blog")
    BLOG;
}
