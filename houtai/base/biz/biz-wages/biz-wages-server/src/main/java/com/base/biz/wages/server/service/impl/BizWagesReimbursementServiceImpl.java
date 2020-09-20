package com.base.biz.wages.server.service.impl;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.base.biz.wages.client.enums.WageTypeEnum;
import com.base.biz.wages.client.enums.WagesReimbursementImportTypeEnum;
import com.base.biz.wages.server.manager.BizWagesManager;
import com.base.biz.wages.server.model.WagesDO;
import com.base.biz.wages.server.service.BizWagesReimbursementService;
import com.base.common.util.DateUtil;
import com.base.common.util.ExcelUtil;
import com.base.common.util.NumberUtil;
import com.base.common.util.UUIDUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:小M
 * @date:2020/9/20 12:56 AM
 */
@Service
public class BizWagesReimbursementServiceImpl implements BizWagesReimbursementService {

    @Value("${ResourceStaticUrl}")
    private String diskStaticUrl;

    @Autowired
    private BizWagesManager bizWagesManager;

    /**
     *
     * @param time
     * @param webFile
     * @param targetExcel
     * @param type
     */
    @Override
    public void importReimbursement(Date time, MultipartFile webFile, InputStream targetExcel, Integer type) throws Exception{
        Assert.notNull(time, "time is null");
        Assert.notNull(webFile, "file is null");
        Assert.notNull(targetExcel, "targetExcel is null");
        Assert.notNull(type, "type is null");

        if(WagesReimbursementImportTypeEnum.REIMBURSEMENT.getType().equals(type)) {
            this.importBaseReimbursement(time, webFile, targetExcel, type);
        }else {

        }

    }

    /**
     * 导入报销封面
     * @param time
     * @param webFile
     * @param targetExcel
     * @param type
     * @throws Exception
     */
    private void importBaseReimbursement(Date time, MultipartFile webFile, InputStream targetExcel, Integer type) throws Exception{
        // 保存上传的文件到磁盘
        String url = diskStaticUrl + "files/" + UUIDUtil.get() + ".xlsx";
        webFile.transferTo(new File(url));
        File file = new File(url);
        System.out.println(url);

        // 查询指定日期的工资明细
        List<WagesDO> wagesDOList = bizWagesManager.listByTime(time);
        wagesDOList = wagesDOList == null ? Lists.newArrayList() : wagesDOList;

        // 查询指定日期内的三级工资明细
        List<WagesDO> threeWagesDOList = wagesDOList.stream().filter(item -> {
            return WageTypeEnum.THREE.getType().equals(item.getType());
        }).collect(Collectors.toList());

        // 查询指定日期内的四级工资明细
        List<WagesDO> fourWagesDOList = wagesDOList.stream().filter(item -> {
            return WageTypeEnum.FOUR.getType().equals(item.getType());
        }).collect(Collectors.toList());

        // 需统计字段
        BigDecimal one = new BigDecimal(0.0);
        BigDecimal two = new BigDecimal(0.0);
        BigDecimal three = new BigDecimal(0.0);
        BigDecimal four = new BigDecimal(0.0);
        BigDecimal five = new BigDecimal(0.0);
        BigDecimal six = new BigDecimal(0.0);
        BigDecimal seven = new BigDecimal(0.0);
        BigDecimal eight = new BigDecimal(0.0);
        BigDecimal nine = new BigDecimal(0.0);
        BigDecimal ten = new BigDecimal(0.0);
        BigDecimal eleven = new BigDecimal(0.0);
        BigDecimal twelve = new BigDecimal(0.0);
        BigDecimal sum = new BigDecimal(0.0);

        // 四级工资统计
        for(WagesDO item : fourWagesDOList) {
            BigDecimal oneThousand = new BigDecimal(1000);
            one = one.add(new BigDecimal(item.getRealWages()).divide(oneThousand));
            two = two.add(new BigDecimal(item.getPersonalIncomeTax()).divide(oneThousand));
            three = three.add(new BigDecimal(item.getPersonalSocialSecurityMoney()).divide(oneThousand));
            four = four.add(new BigDecimal(item.getPersonalAccumulationFund()).divide(oneThousand));
            five = five.add(new BigDecimal(item.getDepartmentSocialSecurityMoney()).divide(oneThousand));
            six = six.add(new BigDecimal(item.getDepartmentAccumulat()).divide(oneThousand));
        }

        // 三级工资统计
        for(WagesDO item : threeWagesDOList) {
            BigDecimal oneThousand = new BigDecimal(1000);
            seven = seven.add(new BigDecimal(item.getRealWages()).divide(oneThousand));
            eight = eight.add(new BigDecimal(item.getPersonalIncomeTax()).divide(oneThousand));
            nine = nine.add(new BigDecimal(item.getPersonalSocialSecurityMoney()).divide(oneThousand));
            ten = ten.add(new BigDecimal(item.getPersonalAccumulationFund()).divide(oneThousand));
            eleven = eleven.add(new BigDecimal(item.getDepartmentSocialSecurityMoney()).divide(oneThousand));
            twelve = twelve.add(new BigDecimal(item.getDepartmentAccumulat()).divide(oneThousand));
        };

        // 总计
        sum = sum.add(one).add(two).add(three).add(four).add(five).
            add(six).add(seven).add(eight).add(nine).add(ten).
            add(eleven).add(twelve);

        // 创建excel报表
        Map<String,String> replaceMap = new HashMap<>();
        replaceMap.put("one" , one.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("two" , two.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("three" , three.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("four" , four.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("five" , five.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("six" , six.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("seven" , seven.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("eight" , eight.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("nine" , nine.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("ten" , ten.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("eleven" , eleven.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("twelve" , twelve.setScale(2,BigDecimal.ROUND_HALF_UP).toString());

        replaceMap.put("date" , DateUtil.getCurrentDateStr(DateUtil.convert2String(time, "yyyy年MM月")));
        replaceMap.put("kemu" , DateUtil.getCurrentDateStr(String.valueOf(DateUtil.getMonth(time)) + "月辅警工资"));
        replaceMap.put("sum2" , DateUtil.getCurrentDateStr(sum.toString()));
        replaceMap.put("sum1" , DateUtil.getCurrentDateStr(NumberUtil.number2CNMontrayUnit(sum)));

        String savePath = diskStaticUrl + "files/";
        String excelName = ExcelUtil.insertExcelAndSave(targetExcel, 3, 0, savePath, Lists.newArrayList(), replaceMap);
        System.out.println(excelName);

        // 如果expire表存在，则删除

        // 存储到expire表中
    }
}
