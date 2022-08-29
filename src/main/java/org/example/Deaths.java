package org.example;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Deaths {
    @JsonProperty("new")
    public String mynew;
    @JsonProperty("1M_pop")
    public String _1M_pop;
    public int total;
}
