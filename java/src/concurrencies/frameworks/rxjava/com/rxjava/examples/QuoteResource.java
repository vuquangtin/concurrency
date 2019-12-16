package com.rxjava.examples;

/**
 * Created by achaub001c on 7/12/2016.
 */

/**
 * quote wrapper model entity
 */
public class QuoteResource {

    String type;
    Quote value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Quote getValue() {
        return value;
    }

    public void setValue(Quote value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "QuoteResource{\n" +
                "\ttype='" + type + '\'' +
                ", \n\tvalue=" + value +
                "}\n";
    }
}