package com.example.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@TableName("setmeal_checks")
@Data
public class SetmealChecks {


    @TableId(value = "id")
    @JsonProperty("id")
    private Integer  id;

    @JsonProperty("tid")
    private Integer  tid;

    @JsonProperty("ch_Id")
    private Integer  chId;




}
