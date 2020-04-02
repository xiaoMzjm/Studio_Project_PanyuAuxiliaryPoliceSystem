package com.base.biz.user.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author:小M
 * @date:2020/3/30 12:10 AM
 */
@Entity
@Table(name = "biz_user",
    indexes = {@Index(name = "idx_code",  columnList="code", unique = true),
        @Index(name = "idx_ide_pa",  columnList="identityCard,password", unique = true)})
public class BizUserDO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date gmtCreate;

    @Column(nullable = false)
    private Date gmtModified;

    @Column(length = 64 , nullable = false)
    private String code;

    @Column(length = 64 , nullable = false)
    private String password;

    @Column(length = 64)
    private String name; // 姓名

    @Column
    private Date birthdate; // 生日

    @Column
    private Integer nation; // 名族 @see NationEnum

    @Column
    private Integer politicalLandscape; // 政治面貌 @see PoliticalLandscapeEnum

    @Column(length = 128)
    private String graduateSchool; // 毕业院校

    @Column(length = 64)
    private String policeCode; // 警号

    @Column(length = 64)
    private String drivingType; // 准驾车型 @see DrivingTypeEnum

    @Column(length = 4096)
    private String speciality; // 特长

    @Column
    private Integer exserviceman; // 是否退役军人 @see ExservicemanEnum

    @Column(length = 512)
    private String permanentResidenceAddress; // 户籍地址

    @Column(length = 512)
    private String familyAddress; //家庭住址

    @Column
    private Integer sex; // 性别 @see SexEnum

    @Column
    private Integer age; // 年龄

    @Column(length = 512)
    private String nativePlace; // 籍贯

    @Column(length = 64 , nullable = false)
    private String identityCard;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
