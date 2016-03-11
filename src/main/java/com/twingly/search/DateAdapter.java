package com.twingly.search;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Custom XMLAdapter that processes XML String date to java.util.Date in correct format.
 *
 * @see Constants#DATE_FORMAT
 */
public class DateAdapter extends XmlAdapter<String, Date> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

    /**
     * {@inheritDoc}
     */
    @Override
    public String marshal(Date v) throws Exception {
        if (v == null) {
            return "";
        }
        return dateFormat.format(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date unmarshal(String v) throws Exception {
        if (v == null || "".equals(v.trim())) {
            return null;
        }
        return dateFormat.parse(v);
    }

}
