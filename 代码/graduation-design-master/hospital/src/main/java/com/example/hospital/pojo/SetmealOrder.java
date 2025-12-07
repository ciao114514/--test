package com.example.hospital.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
public class SetmealOrder {

    @JsonProperty("pId")
    private Integer pId;
    @JsonProperty("pName")
    private String pName;


}
