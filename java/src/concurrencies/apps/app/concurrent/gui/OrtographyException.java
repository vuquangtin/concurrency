package app.concurrent.gui;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class OrtographyException extends Exception {

    private List<String> mistakes;

    public OrtographyException(List<String> mistake) {
        this.mistakes = new ArrayList<String>(mistake);
    }

    public List<String> getMistake() {
        return mistakes;
    }
}