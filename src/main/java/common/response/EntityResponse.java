package common.response;

public class EntityResponse<T> extends Response {

    private T result;

    public EntityResponse(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
