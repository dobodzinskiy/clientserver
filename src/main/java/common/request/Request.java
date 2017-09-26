package common.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import common.dto.CommandType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Request extends Object {

    private String id;

    private CommandType method;

    private String params;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CommandType getMethod() {
        return method;
    }

    public void setMethod(CommandType method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
