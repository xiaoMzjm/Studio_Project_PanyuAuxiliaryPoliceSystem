package com.base.biz.epidemic.client.common;

import java.util.List;

import com.base.biz.epidemic.client.model.EnumVO;
import com.google.common.collect.Lists;

/**
 * @author:小M
 * @date:2020/8/2 3:45 PM
 */
public class EpidemicEnums {

    public enum EpidemicTypeEnum {
        GeLiWeiShangBan(1, "处于隔离观察期未上班"),
        YinGongWaiChu(2, "因公外出"),
        YinSiWaiChu(3, "因私外出")
        ;
        private Integer type;
        private String desc;

        EpidemicTypeEnum(Integer type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public static String getDesc(Integer type){
            for(EpidemicTypeEnum e : EpidemicTypeEnum.values()) {
                if(e.getType().equals(type)) {
                    return e.getDesc();
                }
            }
            return "";
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (EpidemicTypeEnum e : EpidemicTypeEnum.values()) {
                list.add(new EnumVO(e.getType(), e.getDesc()));
            }
            return list;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum EpidemicLocationEnum {
        ZiXingGeLi(1, "自行隔离"),
        DanWeiJiZhong(2, "单位集中"),
        WeiJianBuMen(3, "卫健部门"),
        BeiJing(4, "北京"),
        HuBei(5, "湖北"),
        JingNeiZhongGaoFengXianDiQu(6, "境内中高风险地区"),
        GuangDongShenNei(7, "广东省内"),
        JingNeiQiTaDiQu(8, "境内其他地区"),
        JingWai(9, "境外")
        ;

        private Integer location;
        private String desc;

        EpidemicLocationEnum(Integer location, String desc) {
            this.location = location;
            this.desc = desc;
        }

        public static String getDesc(Integer type){
            for(EpidemicLocationEnum e : EpidemicLocationEnum.values()) {
                if(e.getLocation().equals(type)) {
                    return e.getDesc();
                }
            }
            return "";
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (EpidemicLocationEnum e : EpidemicLocationEnum.values()) {
                list.add(new EnumVO(e.getLocation(), e.getDesc()));
            }
            return list;
        }

        public Integer getLocation() {
            return location;
        }

        public void setLocation(Integer location) {
            this.location = location;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum EpidemicStatusEnum{
        Edit(1, "编辑中"),
        Confirm(2, "已确认"),
        Commit(3, "已提交"),
        ;
        private Integer status;
        private String desc;

        EpidemicStatusEnum(Integer status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (EpidemicStatusEnum e : EpidemicStatusEnum.values()) {
                list.add(new EnumVO(e.getStatus(), e.getDesc()));
            }
            return list;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
