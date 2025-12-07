package com.example.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@TableName(value = "checks")
@Data
public class Checks {
    @TableId(value = "ch_id")
    @JsonProperty("chId")
    private Integer chId;
    @JsonProperty("chName")
    private String chName;
    @JsonProperty("chPrice")
    private Double chPrice;
    @JsonProperty("kid")
    private Integer kid;
    @JsonProperty("name")
    private String  name;
    @JsonProperty("kname")
    private String  kname;
    @JsonProperty("tid")
    private Integer tid;
    @JsonProperty("checks")
    List<Checks> checks;
    @JsonProperty("isActive")
    private String isActive;

}
