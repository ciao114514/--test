package com.example.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@TableName("orders")
@Data
public class Orders {
    @TableId(value = "o_id")
    @JsonProperty("oId")
    private Integer oId;
    @JsonProperty("pId")
    private Integer pId;
    @JsonProperty("dId")
    private Integer dId;
    @JsonProperty("oRecord")
    private String oRecord;
    @JsonProperty("oStart")
    private String oStart;
    @JsonProperty("oEnd")
    private String oEnd;
    @JsonProperty("oState")
    private Integer oState;
    @JsonProperty("oDrug")
    private String oDrug;
    @JsonProperty("oCheck")
    private String oCheck;
    @JsonProperty("oTotalPrice")
    private Double oTotalPrice;
    @JsonProperty("oPriceState")
    private Integer oPriceState;
    @JsonProperty("countGender")
    @TableField(exist = false)
    private Integer countGender;
    @JsonProperty("oAdvice")
    private String oAdvice;
    //多表查询用
    @TableField(exist = false)//声明不是数据库里面的字段
    private Doctor doctor;
    @TableField(exist = false)//声明不是数据库里面的字段
    private Setmeal setmeal;
    //多表查询用
    @TableField(exist = false)//声明不是数据库里面的字段
    private Patient patient;
    @TableField(exist = false)
    private Integer countSection;
    @JsonProperty("dName")
    @TableField(exist = false)
    private String dName;
    @JsonProperty("pName")
    @TableField(exist = false)
    private String pName;
    @JsonProperty("tid")
    private Integer tid;
    @JsonProperty("tname")
    private String tname;
    @JsonProperty("state")
    private Integer  state;
    @JsonProperty("oTprice")
    private Integer oTprice;
    @JsonProperty("oPstate")
    private Integer oPstate;
    @JsonProperty("otype")
    private String otype;
    @JsonProperty("pOid")
    private Integer pOid;
    @JsonProperty("chid")
    private Integer chid;

    @JsonProperty("sg")
    private String    sg;
    @JsonProperty("tz")
    private String    tz;
    @JsonProperty("xy")
    private String    xy;
    @JsonProperty("xl")
    private String    xl;
    @JsonProperty("tl")
    private String    tl;
    @JsonProperty("zr")
    private String    zr;
    @JsonProperty("yr")
    private String    yr;
    @JsonProperty("xj")
    private String    xj;
    @JsonProperty("kc")
    private String    kc;
    @JsonProperty("zy")
    private String    zy;
    @JsonProperty("yy")
    private String   yy;
    @JsonProperty("jzy")
    private String    jzy;
    @JsonProperty("jyy")
    private String   jyy;
    @JsonProperty("sj")
    private String   sj;
    @JsonProperty("jzx")
    private String    jzx;
    @JsonProperty("lb")
    private String    lb;
    @JsonProperty("ggn")
    private String   ggn;
    @JsonProperty("ygn")
    private String    ygn;
    @JsonProperty("xdt")
    private String   xdt;
    @JsonProperty("xn")
    private String   xn;


}
