package com.example.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@TableName("setmeal")
@Data
public class Setmeal {

    @TableId(value = "tid")
    @JsonProperty("tid")
    private Integer  tid;

    @JsonProperty("tname")
    private String  tname;

    @JsonProperty("sert")
    private Integer sert;

    @JsonProperty("price")
    private Double price;

    private List<Checks> checks;


}
