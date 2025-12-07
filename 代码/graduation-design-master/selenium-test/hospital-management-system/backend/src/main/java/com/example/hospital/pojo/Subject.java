package com.example.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@TableName("subject")
@Data
public class Subject implements Serializable {

    @TableId(value = "kid")
    @JsonProperty("kid")
    private Integer  kid;
    @JsonProperty("name")
    private String  name;
    @JsonProperty("kname")
    private String  kname;
    @JsonProperty("sert")
    private Integer  sert;


}
