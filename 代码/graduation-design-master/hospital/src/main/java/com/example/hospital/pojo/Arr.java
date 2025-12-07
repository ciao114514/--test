package com.example.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@TableName("arr")
@Data
public class Arr {

    @TableId(value = "aid")
    @JsonProperty("aid")
    private String aid;
    @JsonProperty("aTime")
    private String aTime;
    @JsonProperty("tid")
    private int tid;
}