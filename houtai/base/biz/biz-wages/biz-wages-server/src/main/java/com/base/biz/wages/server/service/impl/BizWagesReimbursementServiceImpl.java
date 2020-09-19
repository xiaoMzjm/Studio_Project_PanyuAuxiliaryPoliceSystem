package com.base.biz.wages.server.service.impl;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.base.biz.wages.server.service.BizWagesReimbursementService;
import com.base.common.util.DateUtil;
import com.base.common.util.ExcelUtil;
import com.base.common.util.UUIDUtil;
import com.google.common.collect.Lists;
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

        // 保存上传的文件到磁盘
        String url = diskStaticUrl + "files/" + UUIDUtil.get() + ".xlsx";
        webFile.transferTo(new File(url));
        File file = new File(url);
        System.out.println(url);

        // 查询指定日期内的三级工资明细

        // 查询指定日期内的四级工资明细

        // 需统计字段
        Double one = 1.0D;
        Double two = 2.0D;
        Double three = 0.0D;
        Double four = 0.0D;
        Double five = 0.0D;
        Double six = 0.0D;
        Double seven = 0.0D;
        Double eight = 0.0D;
        Double night = 0.0D;
        Double ten = 0.0D;
        Double eleven = 0.0D;
        Double twelve = 0.0D;

        // 三级工资统计

        // 四级工资统计

        // 创建excel报表
        Map<String,String> replaceMap = new HashMap<>();
        replaceMap.put("one" , new BigDecimal(one).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("two" , new BigDecimal(two).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("three" , new BigDecimal(three).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("four" , new BigDecimal(four).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("five" , new BigDecimal(five).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("six" , new BigDecimal(six).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("seven" , new BigDecimal(seven).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("eight" , new BigDecimal(eight).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("night" , new BigDecimal(night).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("ten" , new BigDecimal(ten).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("eleven" , new BigDecimal(eleven).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        replaceMap.put("twelve" , new BigDecimal(twelve).setScale(2,BigDecimal.ROUND_HALF_UP).toString());

        replaceMap.put("date" , DateUtil.getCurrentDateStr("2020年2月4日"));
        replaceMap.put("kemu" , DateUtil.getCurrentDateStr("2月辅警 工资 "));

        String savePath = diskStaticUrl + "files/";
        String excelName = ExcelUtil.insertExcelAndSave(targetExcel, 3, 0, savePath, Lists.newArrayList(), replaceMap);
        System.out.println(excelName);

        // 存储到expire表中

    }
}
