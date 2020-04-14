package com.base.biz.user.client.common;

import java.util.List;

import com.base.biz.user.client.model.EnumVO;
import com.google.common.collect.Lists;

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

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (NationEnum e : NationEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (NationEnum e : NationEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (NationEnum e : NationEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (NationEnum e : NationEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }


        public static NationEnum get(Integer code) {
            for (NationEnum e : NationEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (NationEnum e : NationEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static NationEnum get(String name) {
            for (NationEnum e : NationEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
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

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (PoliticalLandscapeEnum e : PoliticalLandscapeEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (PoliticalLandscapeEnum e : PoliticalLandscapeEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (PoliticalLandscapeEnum e : PoliticalLandscapeEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static PoliticalLandscapeEnum get(Integer code) {
            for (PoliticalLandscapeEnum e : PoliticalLandscapeEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (PoliticalLandscapeEnum e : PoliticalLandscapeEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static PoliticalLandscapeEnum get(String name) {
            for (PoliticalLandscapeEnum e : PoliticalLandscapeEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (PoliticalLandscapeEnum e : PoliticalLandscapeEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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
        A1(1,"A1"),
        A2(2,"A2"),
        A3(3,"A3"),
        B1(4,"B1"),
        B2(5,"B2"),
        C1(6,"C1"),
        C2(7,"C2"),
        C3(8,"C3"),
        C4(9,"C4"),
        C5(10,"C5"),
        D(11,"D"),
        E(12,"E"),
        F(13,"F"),
        M(14,"M"),
        N(15,"N"),
        P(16,"P"),
        ;
        private Integer code;
        private String name;

        DrivingTypeEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (DrivingTypeEnum e : DrivingTypeEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (DrivingTypeEnum e : DrivingTypeEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (DrivingTypeEnum e : DrivingTypeEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static DrivingTypeEnum get(Integer code) {
            for (DrivingTypeEnum e : DrivingTypeEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (DrivingTypeEnum e : DrivingTypeEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static DrivingTypeEnum get(String name) {
            for (DrivingTypeEnum e : DrivingTypeEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (DrivingTypeEnum e : DrivingTypeEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (ExservicemanEnum e : ExservicemanEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (ExservicemanEnum e : ExservicemanEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (ExservicemanEnum e : ExservicemanEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static ExservicemanEnum get(Integer code) {
            for (ExservicemanEnum e : ExservicemanEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (ExservicemanEnum e : ExservicemanEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static ExservicemanEnum get(String name) {
            for (ExservicemanEnum e : ExservicemanEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (ExservicemanEnum e : ExservicemanEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (SexEnum e : SexEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (SexEnum e : SexEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (SexEnum e : SexEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static SexEnum get(Integer code) {
            for (SexEnum e : SexEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (SexEnum e : SexEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static SexEnum get(String name) {
            for (SexEnum e : SexEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (SexEnum e : SexEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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
     * 学历
     */
    public enum EducationEnum {

        GRADUATE_STUDENT(1, "研究生"),
        UNDERGRADUCATE(2, "本科"),
        JUNIOR_COLLEGE(3, "大专"),
        HIGH_SCHOOL(4, "高中"),
        SECONDARY_SPECIALIZED_SHCOOL(5, "中专"),
        JUNIOR_MIDDLE_SCHOOL(6, "初中")

        ;

        private Integer code;
        private String name;

        EducationEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (EducationEnum e : EducationEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (EducationEnum e : EducationEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (EducationEnum e : EducationEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static EducationEnum get(Integer code) {
            for (EducationEnum e : EducationEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (EducationEnum e : EducationEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static EducationEnum get(String name) {
            for (EducationEnum e : EducationEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (EducationEnum e : EducationEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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
     * 婚姻状况
     */
    public enum MaritalStatusEnum{

        UNMARRIED(1, "未婚"),
        MARRIED(2, "已婚"),
        DIVORCE(3, "离婚"),
        WIDOWED_SPOUSE(4, "丧偶")
        ;

        private Integer code;
        private String name;

        MaritalStatusEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (MaritalStatusEnum e : MaritalStatusEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (MaritalStatusEnum e : MaritalStatusEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (MaritalStatusEnum e : MaritalStatusEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static MaritalStatusEnum get(Integer code) {
            for (MaritalStatusEnum e : MaritalStatusEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (MaritalStatusEnum e : MaritalStatusEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static MaritalStatusEnum get(String name) {
            for (MaritalStatusEnum e : MaritalStatusEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (MaritalStatusEnum e : MaritalStatusEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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
     * 人员类型
     */
    public enum PersonnelTypeEnum {
        INCUMBENCY(1, "在职"),
        QUIT(2, "辞职"),
        RETIRE(3, "退休"),
        DISMISS(4, "辞退"),
        TRANSFER(5,"调离"),
        DEATH(6,"去世")

        ;

        private Integer code;
        private String name;

        PersonnelTypeEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (PersonnelTypeEnum e : PersonnelTypeEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (PersonnelTypeEnum e : PersonnelTypeEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (PersonnelTypeEnum e : PersonnelTypeEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static PersonnelTypeEnum get(Integer code) {
            for (PersonnelTypeEnum e : PersonnelTypeEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (PersonnelTypeEnum e : PersonnelTypeEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }


        public static PersonnelTypeEnum get(String name) {
            for (PersonnelTypeEnum e : PersonnelTypeEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (PersonnelTypeEnum e : PersonnelTypeEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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
     * 编制类型
     */
    public enum AuthorizedStrengthTypeEnum {
        PUBLIC(1, "公建辅警"),
        AREA(2, "区自建辅警")
        ;

        private Integer code;
        private String name;

        AuthorizedStrengthTypeEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (AuthorizedStrengthTypeEnum e : AuthorizedStrengthTypeEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (AuthorizedStrengthTypeEnum e : AuthorizedStrengthTypeEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (AuthorizedStrengthTypeEnum e : AuthorizedStrengthTypeEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static AuthorizedStrengthTypeEnum get(Integer code) {
            for (AuthorizedStrengthTypeEnum e : AuthorizedStrengthTypeEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (AuthorizedStrengthTypeEnum e : AuthorizedStrengthTypeEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static AuthorizedStrengthTypeEnum get(String name) {
            for (AuthorizedStrengthTypeEnum e : AuthorizedStrengthTypeEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (AuthorizedStrengthTypeEnum e : AuthorizedStrengthTypeEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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
     * 岗位
     */
    public enum PlaceOfWorkEnum {
        YI_WU(1, "医务"),
        XIN_LI_ZI_XUN(2, "心理咨询"),
        JIAN_YAN_JIAN_DING_ZHU_LI(3, "检验鉴定助理"),
        XIAN_CHANG_KAN_CHA_ZHU_LI(4, "现场勘察助理"),
        FAN_YI(5, "翻译"),
        YING_SHI_ZHI_ZYO(6, "影视制作"),
        BIAN_JI(7, "编辑"),
        XIN_XI_AN_QUAN_GUANL_LI(8, "信息安全助理"),
        XI_TONG_KAI_FA_YU_GUAN_LI(9, "系统开发与管理"),
        XI_TONG_YUN_WEI_GUAN_LI(10 , "系统运维管理"),
        WANG_LUO_GUAN_LI(11, "网络管理"),
        YOU_XIAN_TONG_XIN_YUN_WEI_GUAN_LI(12, "有线通信运维管理"),
        WU_XIAN_TONG_XIN_XI_TONG_GUAN_LI(13, "无限通信系统管理"),
        SHI_PIN_TU_XIANG_CHU_LI_FEN_XI(14, "视频图像处理分析"),
        QUAN_JING_JI_SHU_GUAN_LI(15, "犬警技术管理"),
        KUAI_JI(16, "会计"),
        CHU_NA(17, "出纳"),
        SHEN_JI(18, "审计"),
        CHUAN_TING_LUN_JI(19, "船艇轮机"),
        CHE_JIA_KAI_JIAN_ZHI_KAO_SHI_YUAN(20, "车驾考兼职考试员"),
        WANG_LUO_XUN_CHA(21, "网络巡查"),
        WANG_LUO_AN_QUAN_GUAN_LI(22, "网络安全管理"),
        WEN_SHU_GUAN_LI(23, "文书管理"),
        ZI_LIAO_ZHENG_LI(24, "资料整理"),
        DANG_AN_GUAN_LI(25, "档案管理"),
        CHAUNG_KOU_FU_WU(26, "窗口服务"),
        JIAO_YU_KANG_FU(27, "教育康复"),
        JING_WU_ZI_CHAN_GUAN_LI(28, "警务资产管理"),
        CANG_KU_GUAN_LI(29, "仓库管理"),
        GONG_CHE_GUAN_LI(30, "公车管理"),
        NEI_QIN_ZONG_HE(31, "内勤综合"),
        XUN_LUO_FANG_KONG_XIE_QIN(32,"巡逻防控协勤"),
        SHE_QU_XIE_GUAN(33, "社区协管"),
        ZHI_ANA_XIE_QIN(34, "治安协勤"),
        FAN_PA_XIE_QIN(35, "反扒协勤"),
        TE_QIN_CHU_TU(36, "特勤处突"),
        REN_MIN_TIAO_JIE_YUAN(37, "人民调解员"),
        LU_MIAN_JIAO_TONG_XIE_QIN(38, "路面交通协勤"),
        JIAO_TONG_SHI_GU_XIE_QIN(39 , "交通事故协勤"),
        JIAO_TONG_ZHENG_LI_XIE_QIN(40, "交通整理协勤"),
        CHE_LIANG_CHA_YAN_XIE_QIN(41, "车辆查验协勤"),
        JIAO_TONG_YE_WU_JIE_DAI_BAN_LI(42, "交通业务接待办理"),
        JIAO_TONG_WEI_FA_XIN_XI_LU_RU(43, "交通违法信息录入"),
        JIAO_TONG_NEI_QIN_BAN_LI(44, "交通内勤办理"),
        CHE_GUAN_YE_WU_XIE_QIN(45, "车管业务协勤"),
        SHOU_YA_TI_YA(46, "收押提押"),
        AN_QUAN_JING_JIE(47, "安全警戒"),
        JIAN_QU_FU_WU(48, "监区服务"),
        JING_QUAN_XUN_DAO(49, "警犬训导"),
        WAI_GUO_REN_XIE_GUAN(50, "外国人协管"),
        SHUI_SHANG_JIU_SHENG(51, "水上救生"),
        CHUAN_TING_JIA_SHI(52, "船艇驾驶"),
        JIE_CHU_JING_DIAO_DU(53, "接处警调度"),
        SHI_PIN_JIAN_KAN(54, "视频监看"),
        FA_YI_ZHU_JIAN_YUAN(55, "法医助员"),
        AN_JIAN_BAN_LI_XIE_QIN(56, "按键办理协勤")
        ;

        private Integer code;
        private String name;

        PlaceOfWorkEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (PlaceOfWorkEnum e : PlaceOfWorkEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (PlaceOfWorkEnum e : PlaceOfWorkEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (PlaceOfWorkEnum e : PlaceOfWorkEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static PlaceOfWorkEnum get(Integer code) {
            for (PlaceOfWorkEnum e : PlaceOfWorkEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (PlaceOfWorkEnum e : PlaceOfWorkEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static PlaceOfWorkEnum get(String name) {
            for (PlaceOfWorkEnum e : PlaceOfWorkEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (PlaceOfWorkEnum e : PlaceOfWorkEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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
     * 职级
     */
    public enum JobGradeEnum {
        FIRST(1, "一级辅警"),
        FIRST_THREE(2, "一级辅警(三级岗)"),
        SECOND(3, "二级辅警")
        ;

        private Integer code;
        private String name;

        JobGradeEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (JobGradeEnum e : JobGradeEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (JobGradeEnum e : JobGradeEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (JobGradeEnum e : JobGradeEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static JobGradeEnum get(Integer code) {
            for (JobGradeEnum e : JobGradeEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (JobGradeEnum e : JobGradeEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static JobGradeEnum get(String name) {
            for (JobGradeEnum e : JobGradeEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (JobGradeEnum e : JobGradeEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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
     * 待遇级别
     */
    public enum TreatmentGradeEnum {
        THREE(1, "三级"),
        FOUR(2, "四级"),
        FIRE(3, "五级")
        ;

        private Integer code;
        private String name;

        TreatmentGradeEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (TreatmentGradeEnum e : TreatmentGradeEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (TreatmentGradeEnum e : TreatmentGradeEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (TreatmentGradeEnum e : TreatmentGradeEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static TreatmentGradeEnum get(Integer code) {
            for (TreatmentGradeEnum e : TreatmentGradeEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (TreatmentGradeEnum e : TreatmentGradeEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static TreatmentGradeEnum get(String name) {
            for (TreatmentGradeEnum e : TreatmentGradeEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (TreatmentGradeEnum e : TreatmentGradeEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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
     * 招录方式
     */
    public enum EnrollWayEnum {

        PUBLIC(1, "公开招聘"),
        INNER(2, "划转过渡"),
        OTHER(3, "其他")
        ;

        private Integer code;
        private String name;

        EnrollWayEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (EnrollWayEnum e : EnrollWayEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (EnrollWayEnum e : EnrollWayEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (EnrollWayEnum e : EnrollWayEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static EnrollWayEnum get(Integer code) {
            for (EnrollWayEnum e : EnrollWayEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (EnrollWayEnum e : EnrollWayEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static EnrollWayEnum get(String name) {
            for (EnrollWayEnum e : EnrollWayEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (EnrollWayEnum e : EnrollWayEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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
     * 离职类别
     */
    public enum DimssionTypeEnum {
        RESUGNATION(1, "辞职"),
        RETIRE(2, "退休"),
        DISMISS(3, "辞退"),
        TRANSFER(4, "调离"),
        DEATH(5, "去世")
        ;

        private Integer code;
        private String name;

        DimssionTypeEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (DimssionTypeEnum e : DimssionTypeEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (DimssionTypeEnum e : DimssionTypeEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (DimssionTypeEnum e : DimssionTypeEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static DimssionTypeEnum get(Integer code) {
            for (DimssionTypeEnum e : DimssionTypeEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (DimssionTypeEnum e : DimssionTypeEnum.values()) {
                if (e.code.equals(code)) {
                    return e.getName();
                }
            }
            return null;
        }

        public static DimssionTypeEnum get(String name) {
            for (DimssionTypeEnum e : DimssionTypeEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (DimssionTypeEnum e : DimssionTypeEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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
     * 岗位类别
     */
    public enum JobCategoryEnum {
        CIVILIAN_POST(1, "文职辅警"),
        DUTY(2, "勤务辅警")
        ;

        private Integer code;
        private String name;

        JobCategoryEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (JobCategoryEnum e : JobCategoryEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (JobCategoryEnum e : JobCategoryEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (JobCategoryEnum e : JobCategoryEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static JobCategoryEnum get(Integer code) {
            for (JobCategoryEnum e : JobCategoryEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (JobCategoryEnum e : JobCategoryEnum.values()) {
                if (e.code.equals(code)) {
                    return e.name;
                }
            }
            return null;
        }

        public static JobCategoryEnum get(String name) {
            for (JobCategoryEnum e : JobCategoryEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (JobCategoryEnum e : JobCategoryEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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
     * 特殊人员
     */
    public enum SpecialPeopleEnum {
        one(1, "退役军人"),
        two(2, "A1驾照"),
        three(3, "摩托车牌"),
        four(4, "计算机等专业"),
        fire(5, "中文文秘等专业"),
        six(6, "会计等专业"),
        ;

        private Integer code;
        private String name;

        SpecialPeopleEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getAllCode(){
            StringBuilder sb = new StringBuilder();
            for (SpecialPeopleEnum e : SpecialPeopleEnum.values()) {
                sb.append(e.getCode()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static List<Integer> getAllCodeList(){
            List<Integer> list = Lists.newArrayList();
            for (SpecialPeopleEnum e : SpecialPeopleEnum.values()) {
                list.add(e.getCode());
            }
            return list;
        }

        public static String getAllName(){
            StringBuilder sb = new StringBuilder();
            for (SpecialPeopleEnum e : SpecialPeopleEnum.values()) {
                sb.append(e.getName()).append(",");
            }
            return sb.toString().substring(0,sb.toString().length()-1);
        }

        public static SpecialPeopleEnum get(Integer code) {
            for (SpecialPeopleEnum e : SpecialPeopleEnum.values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
            return null;
        }

        public static String getName(Integer code) {
            for (SpecialPeopleEnum e : SpecialPeopleEnum.values()) {
                if (e.code.equals(code)) {
                    return e.name;
                }
            }
            return null;
        }

        public static SpecialPeopleEnum get(String name) {
            for (SpecialPeopleEnum e : SpecialPeopleEnum.values()) {
                if (e.name.equals(name)) {
                    return e;
                }
            }
            return null;
        }

        public static List<EnumVO> getAll(){
            List<EnumVO> list = Lists.newArrayList();
            for (SpecialPeopleEnum e : SpecialPeopleEnum.values()) {
                list.add(new EnumVO(e.getCode(), e.getName()));
            }
            return list;
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

