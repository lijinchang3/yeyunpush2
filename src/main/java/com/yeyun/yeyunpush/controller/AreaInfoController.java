package com.yeyun.yeyunpush.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeyun.yeyunpush.common.utils.R;
import com.yeyun.yeyunpush.entity.AppManage;
import com.yeyun.yeyunpush.entity.AreaInfo;
import com.yeyun.yeyunpush.service.AreaInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AreaInfoController {
    @Autowired
    private AreaInfoService areaInfoService;
    @RequestMapping("/AreaInfo")
    private  String getAreaInfo()
    {
        return  "AreaInfo/AreaInfo";
    }
    @RequestMapping(value = "/AreaInfo/json",method = RequestMethod.GET)
    @ResponseBody
    public String selectAllJson( HttpServletRequest request){
        R r=new R();
        List<AreaInfo> list=new ArrayList<>();
        Example example = new Example(AreaInfo.class);
        example.createCriteria().andLessThan("arealevel", "4");
        list=areaInfoService.queryByParams(example);
        List<treeNode> listTree=new ArrayList<>();
        for(AreaInfo areaInfo:list)
        {
            treeNode node=new treeNode();
            node.id=areaInfo.getId().toString();
            node.arealevel=areaInfo.getArealevel().toString();
            node.d_id=areaInfo.getCode();
            node.d_pid=areaInfo.getParentcode();
            node.name=areaInfo.getAreaname();
            listTree.add(node);
        }
        //PageInfo pi = new PageInfo(list);
        r.setMsg("ok");
        r.setData(listTree);
        r.setCount((int)list.size());
        return r.toJson();
    }


    @RequestMapping("/AreaInfo/add")
    public  String AreaAdd()
    {
        return  "AreaInfo/AreaInfoAdd";
    }
    ///AreaInfo/Add
    @RequestMapping(value = "AreaInfo/AddEntity", method = RequestMethod.POST)
    @ResponseBody
    public R addAppManage(@RequestBody AreaInfo areaInfo)
    {
        R r = new R();
        Example example = new Example(AreaInfo.class);
        example.createCriteria().andEqualTo("code", areaInfo.getParentcode());
        AreaInfo ai=areaInfoService.getMapper().selectOneByExample(example);
        areaInfo.setArealevel(ai.getArealevel()+1);
        areaInfo.setUpdatetime(new Date());
        areaInfo.setAddtime(new Date());
        try {
            if (areaInfoService.insert(areaInfo) > 0) {
                r.setMsg("添加成功");
                r.setCode(3);
            } else
                r.setMsg("添加失败");
        }catch (Exception e){
            r.setMsg("添加失败,请重试");
            return r;
        }
        return r;
    }
    @RequestMapping(value = "AreaInfo/update", method = RequestMethod.POST)
    @ResponseBody
    public R updateAppManage(@RequestBody AreaInfo areaInfo)
    {
        R r = new R();
        areaInfo.setUpdatetime(new Date());
        try {
            if (areaInfoService.UpdateAppManage(areaInfo) > 0) {
                r.setMsg("修改成功");
                r.setCode(3);
            } else
                r.setMsg("修改失败");
        }catch (Exception e){
            r.setMsg("修改失败,请重试");
            return r;
        }
        return r;
    }
    @RequestMapping(value = "AreaInfo/del/{license}", method = RequestMethod.POST)
    @ResponseBody
    public String del(@PathVariable("license") String license) {
        R r = new R();
        try {
            Example example = new Example(AreaInfo.class);
            example.createCriteria().andEqualTo("code", license);
            if (areaInfoService.getMapper().deleteByExample(example) > 0) {
                r.setMsg("删除成功");
                r.setCode(3);
            } else
                r.setMsg("删除失败");
        }catch (Exception e){
            r.setMsg("删除失败,请重试");
            return r.toJsonyMd();
        }
        return r.toJsonyMd();
    }
    //┝-
}

 class  treeNode
{
    public  String id;
    public  String d_id;
    public  String name;
    public  String d_pid;
    public  String arealevel;

}