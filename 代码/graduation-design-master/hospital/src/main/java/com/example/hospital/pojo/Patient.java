package com.example.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@TableName("patient")
@Data
public class Patient {
    @TableId(value = "p_id")
    @JsonProperty("pId")
    private Integer pId;
    @JsonProperty("pPassword")
    //@TableField(select = false)
    private String pPassword;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pGender")
    private String pGender;
    @JsonProperty("pCard")
    private String pCard;
    @JsonProperty("pEmail")
    private String pEmail;
    @JsonProperty("pPhone")
    private String pPhone;
    @JsonProperty("pState")
    private Integer pState;
    @JsonProperty("pBirthday")
    private String pBirthday;
    @JsonProperty("pAge")
    private Integer pAge;
    @JsonProperty("pType")
    private String pType;
}
