package response;

import com.sun.org.apache.regexp.internal.RESyntaxException;

public class EntityResponse<T> extends Response {

    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
