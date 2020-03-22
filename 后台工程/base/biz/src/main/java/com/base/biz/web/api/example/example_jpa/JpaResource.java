package com.base.biz.web.api.example.example_jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:小M
 * @date:2018/11/4 下午10:27
 */
@RestController
@RequestMapping("/api/resource")
public class JpaResource {

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "批量插入" , notes = "批量插入")
    @RequestMapping(value = "/insert/{name}" , method = RequestMethod.POST)
    public String insert(@PathVariable String name){
        UserDO userDO = new UserDO();
        userDO.setNickName(name);

        List<UserDO> list = new ArrayList<>();
        list.add(userDO);
        list = userRepository.saveAll(list);
        return "result = " + list.toString() ;
    }

    @ApiOperation(value = "删除一条数据" , notes = "删除一条数据")
    @RequestMapping(value = "/delete/{id}" , method = RequestMethod.POST)
    public String delete(@PathVariable Long id){
        userRepository.deleteById(id);
        return "success";
    }

    @ApiOperation(value = "根据id查" , notes = "根据id查")
    @RequestMapping(value = "/findById/{id}" , method = RequestMethod.POST)
    public String findById(@PathVariable Long id){
        Optional<UserDO> userDOOptional = userRepository.findById(id);
        if(userDOOptional.isPresent()){
            return userDOOptional.get().toString();
        }
        return "id=" + id + " user not found";
    }

    @ApiOperation(value = "根据条件查，排序，分页" , notes = "根据条件查，排序，分页")
    @RequestMapping(value = "/findByPage/{name}" , method = RequestMethod.POST)
    public String findByPage(@PathVariable String name) {

        UserDO userDO = new UserDO();
        userDO.setNickName(name);


        Example<UserDO> example = Example.of(userDO);
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(1,2,sort);

        Page<UserDO> result = userRepository.findAll(example , pageable);

        return "页数:" + result.getTotalPages() + ",数据量：" + result.getTotalElements() + ",数据:" + result.getContent().toString();
    }

    @ApiOperation(value = "批量修改" , notes = "批量修改")
    @RequestMapping(value = "/update/{id}/{name}" , method = RequestMethod.POST)
    public String update(@PathVariable Long id , @PathVariable String name) {

        List<UserDO> list = new ArrayList<>();

        List<Long> ids = new ArrayList<>();
        ids.add(id);
        list = userRepository.findAllById(ids);

        for(UserDO item : list) {
            item.setNickName(name);
        }

        list = userRepository.saveAll(list);

        return list.toString();

    }
}
