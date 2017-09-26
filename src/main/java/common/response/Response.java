package common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import common.dto.CommandType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    private String id;

    private CommandType commandType;

    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
