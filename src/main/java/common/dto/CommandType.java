package common.dto;

public enum CommandType {
    ADDBIRD("addbird"),
    ADDSIGHTING("addsighting"),
    REMOVE("remove"),
    QUIT("quit"),
    LISTBIRDS("listbirds"),
    LISTSIGHTINGS("listsightings");


    private String value;

    CommandType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CommandType getEnum(String value) {
        for (CommandType v : values())
            if (v.getValue().equalsIgnoreCase(value)) {
                return v;
            }

        throw new IllegalArgumentException(String.format("Cannot convert '%s' value to enum", value));
    }

}
