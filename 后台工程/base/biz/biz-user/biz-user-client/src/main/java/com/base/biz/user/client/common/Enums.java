package com.base.biz.user.client.common;

/**
 * @author:小M
 * @date:2020/4/3 1:32 AM
 */
public class Enums {

    /**
     * 民族
     */
    public enum NationEnum {
        HAN_ZU(1, "汉族"),
        ZHUANG_ZU(2, "壮族"),
        HUI_ZU(3, "回族"),
        MAN_ZU(4, "满族"),
        WEI_WU_ER_ZU(5, "维吾尔族"),
        MIAO_ZU(6, "苗族"),
        YI_ZU(7, "彝族"),
        TU_JIA_ZU(8, "土家族"),
        ZANG_ZU(9, "藏族"),
        MENG_GU_ZU(10, "蒙古族"),
        DONG_ZU(11, "侗族"),
        BU_YI_ZU(12, "布依族"),
        YAO_ZU(13, "瑶族"),
        BAI_ZU(14, "白族"),
        CHAO_XIAN_ZU(15, "朝鲜族"),
        HA_NI_ZU(16, "哈尼族"),
        LI_ZU(17, "黎族"),
        HA_SA_KE_ZU(18, "哈萨克族"),
        DAI_ZU(19, "傣族"),
        SHE_ZU(20, "畲族"),
        LI_SU_ZU(21, "傈僳族"),
        DONG_XIANG_ZU(22, "东乡族"),
        GE_LAO_ZU(23, "仡佬族"),
        LA_HU_ZU(24, "拉祜族"),
        WA_ZU(25, "佤族"),
        SHUI_ZU(26, "水族"),
        NA_XI_ZU(27, "纳西族 "),
        QIANG_ZU(28, "羌族"),
        TU_ZU(29, "土族"),
        MU_LAO_ZU(30, "仫佬族"),
        XI_BO_ZU(31, "锡伯族"),
        KE_ER_KE_ZI_ZU(32, "柯尔克孜族"),
        JING_PO_ZU(33, "景颇族"),
        DA_WO_ER_ZU(34, "达斡尔族"),
        SA_LA_ZU(35, "撒拉族"),
        BU_LANG_ZU(36, "布朗族"),
        MAP_NAN_ZU(37, "毛南族"),
        TA_JI_KE_ZU(38, "塔吉克族"),
        PU_MI_ZU(39, "普米族"),
        A_CHANG_ZU(40, "阿昌族"),
        NU_ZU(41, "怒族"),
        E_WEN_KE_ZUA(42, "鄂温克族"),
        JING_ZU(43, "京族"),
        JI_RUO_ZU(44, "基若族"),
        DE_ANG_ZU(45, "德昂族"),
        BAO_AN_ZU(46, "保安族"),
        E_LUO_SI_ZU(47, "俄罗斯族"),
        YU_GU_ZU(48, "裕固族"),
        WU_ZI_BIE_KE_ZU(49, "乌孜别克族"),
        MEN_BA_ZU(50, "门巴族"),
        E_LUN_CHUN_ZU(51, "鄂伦春族"),
        DU_LONG_ZU(52, "独龙族"),
        HE_ZHE_ZU(53, "赫哲族"),
        GAO_SHAN_ZU(54, "高山族"),
        LUO_BA_ZU(55, "珞巴族"),
        TA_TA_E_ZU(56, "塔塔尔族")
        ;

        private Integer code;
        private String name;

        NationEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 政治身份
     */
    public enum PoliticalLandscapeEnum {
        ZHONG_GONG_DANG_YUAN(1, "中共党员"),
        ZHONG_GONG_YU_BEI_DANG_YUAN(2, "中共预备党员"),
        QUN_ZHONG(3, "群众"),
        OTHER(4, "其他")
        ;

        private Integer code;
        private String name;

        PoliticalLandscapeEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 准驾车型
     */
    public enum DrivingTypeEnum {
        A1("A1"),
        A2("A2"),
        A3("A3"),
        B1("B1"),
        B2("B2"),
        C1("C1"),
        C2("C2"),
        C3("C3"),
        C4("C4"),
        C5("C5"),
        D("D"),
        E("E"),
        F("F"),
        M("M"),
        N("N"),
        P("P"),
        ;
        private String code;

        DrivingTypeEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    /**
     * 是否退役军人
     */
    public enum ExservicemanEnum{
        YES(1, "是"),
        NO(0, "否")
        ;

        private Integer code;
        private String name;

        ExservicemanEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    /**
     * 性别
     */
    public enum SexEnum {
        MAN(1 , "男"),
        MALE(2, "女")

        ;

        private Integer code;
        private String name;

        SexEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
