package app.concurrent.utils;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public interface IHttpProgressListener {

    String CONNECTING_MSG = "Connecting..";
    String FAILS_MSG = "Failed";
    String SUCCESSFUL_MSG = "Working";
    String RETRY_MSG = "Retrying..";

    void notifyListener(Object stateType, Object updateType, Object value);
    enum Status {
        FAILS, SUCCESS, RUNNING
    }

    enum UpdateType {
        DOWNLOAD, UPLOAD, STATUS, HEADER
    }
}