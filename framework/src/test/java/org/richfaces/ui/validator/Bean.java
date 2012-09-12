package org.richfaces.ui.validator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

public class Bean {
    public static final String FOO_VALUE = "fooValue";
    private String value = FOO_VALUE;

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
