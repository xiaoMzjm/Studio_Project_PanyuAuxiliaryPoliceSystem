package com.base.department.server.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import com.base.department.server.manager.impl.CompanyManagerImpl;
import com.base.department.server.model.CompanyDTO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2020/4/18 11:01 PM
 */
@Service
public class CompanyInitService {

    @Autowired
    private CompanyManagerImpl companyManager;

    @PostConstruct
    public void init(){
        initCompany();
    }

    private void initCompany(){
        //List<CompanyDTO> fList = companyManager.findByName("政工办公室");
        //CompanyDTO f = null;
        //if(CollectionUtils.isEmpty(fList)) {
        //    f = companyManager.add("政工办公室","","", "zhenggongbangongshi");
        //}
        //List<CompanyDTO> c = companyManager.findByName("人事组");
        //if(CollectionUtils.isEmpty(c)) {
        //    companyManager.add("人事组","",f.getCode(), "renshizu");
        //}
        //c = companyManager.findByName("教培组");
        //if(CollectionUtils.isEmpty(c)) {
        //    companyManager.add("教培组","",f.getCode(), "jiaopeizu");
        //}
        //c = companyManager.findByName("综合组");
        //if(CollectionUtils.isEmpty(c)) {
        //    companyManager.add("综合组","",f.getCode(), "zonghezu");
        //}
        //c = companyManager.findByName("内勤组");
        //if(CollectionUtils.isEmpty(c)) {
        //    companyManager.add("内勤组","",f.getCode(), "neiqinzu");
        //}
        //c = companyManager.findByName("辅警办");
        //if(CollectionUtils.isEmpty(c)) {
        //    companyManager.add("辅警办","",f.getCode(), "fujingban");
        //}
        //c = companyManager.findByName("训练大队");
        //if(CollectionUtils.isEmpty(c)) {
        //    companyManager.add("训练大队","",f.getCode(), "xunliandadui");
        //}
        //c = companyManager.findByName("老干组");
        //if(CollectionUtils.isEmpty(c)) {
        //    companyManager.add("老干组","",f.getCode(), "laoganzu");
        //}


        //f = companyManager.findByName("交警大队");
        //if(f == null) {
        //    f = companyManager.add("交警大队","","", "jiaojingdadui");
        //}
        //c = companyManager.findByName("交警大队车管中队");
        //if(c == null) {
        //    companyManager.add("交警大队车管中队","",f.getCode(), "jiaojingdaduicheguanzhongdui");
        //}
        //c = companyManager.findByName("交警大队事故中队");
        //if(c == null) {
        //    companyManager.add("交警大队事故中队","",f.getCode(), "jiaojingdaduishiguzhongdui");
        //}
        //c = companyManager.findByName("交警大队一中队");
        //if(c == null) {
        //    companyManager.add("交警大队一中队","",f.getCode(), "jiaojingdaduiyizhongdui");
        //}
        //
        //f = companyManager.findByName("市桥派出所");
        //if(f == null) {
        //    f = companyManager.add("市桥派出所","","", "shiqiaopaichusuo");
        //}
        //c = companyManager.findByName("市桥派出所治安组");
        //if(c == null) {
        //    companyManager.add("市桥派出所治安组","",f.getCode(), "shiqiaopaichusuozhjianzu");
        //}
        //c = companyManager.findByName("市桥派出所刑侦组");
        //if(c == null) {
        //    companyManager.add("市桥派出所刑侦组","",f.getCode(), "shiqiaopaichusuoxingzhenzhu");
        //}
        //c = companyManager.findByName("市桥派出所内勤组");
        //if(c == null) {
        //    companyManager.add("市桥派出所内勤组","",f.getCode(), "shiqiaopaichusuoyizhongdui");
        //}
        //
        //
        //f = companyManager.findByName("西丽派出所");
        //if(f == null) {
        //    f = companyManager.add("西丽派出所","","", "xilipaichusuo");
        //}
        //c = companyManager.findByName("西丽派出所治安组");
        //if(c == null) {
        //    companyManager.add("西丽派出所治安组","",f.getCode(), "xilipaichusuozhianzu");
        //}
        //c = companyManager.findByName("西丽派出所刑侦组");
        //if(c == null) {
        //    companyManager.add("西丽派出所刑侦组","",f.getCode(), "xilipaichusuoxingzhenzu");
        //}
        //c = companyManager.findByName("西丽派出所内勤组");
        //if(c == null) {
        //    companyManager.add("西丽派出所内勤组","",f.getCode(), "xilipaichusuoneiqinzu");
        //}

    }
}
