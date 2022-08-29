package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Cases {
    @JsonProperty("new")
    public String mynew;
    public int active;
    public int critical;
    public int recovered;
    @JsonProperty("1M_pop")
    public String _1M_pop;
    public int total;
}
