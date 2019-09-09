package com.yeyun.yeyunpush.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.deploy.security.SelectableSecurityManager;
import com.yeyun.yeyunpush.common.utils.R;
import com.yeyun.yeyunpush.entity.AppManage;
import com.yeyun.yeyunpush.mapper.AppManageMapper;
import com.yeyun.yeyunpush.service.AppManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AppManageController {

    @Autowired
    private AppManageService appManageService;
    @GetMapping("/AppManage")
    public  String AppManage()
    {
        return  "AppManage";
    }
    @RequestMapping(value = "/AppManage/json",method = RequestMethod.GET)
    @ResponseBody
    public String selectAllJson(@RequestParam("page") int page , @RequestParam("limit") int limit, HttpServletRequest request){
        String appname=request.getParameter("appname");
        PageHelper.startPage(page,limit);
        R r=new R();
        List<AppManage> list=new ArrayList<>();
        if(StringUtils.isBlank(appname))
         list= appManageService.getAll();
        else
        {
            Example example = new Example(AppManage.class);
            example.createCriteria().orLike("appname", "%"+appname+"%");
            list=appManageService.queryByParams(example);
        }
        PageInfo pi = new PageInfo(list);
        r.setMsg("ok");
        r.setData(list);
        r.setCount((int)pi.getTotal());
        return r.toJson();
    }
    @RequestMapping(value = "AppManage/add", method = RequestMethod.POST)
    @ResponseBody
    public R addAppManage(@RequestBody AppManage appManage)
    {
        R r = new R();
        appManage.setUpdatetime(new Date());
        appManage.setAddtime(new Date());
        try {
            if (appManageService.insert(appManage) > 0) {
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
    @RequestMapping(value = "AppManage/update", method = RequestMethod.POST)
    @ResponseBody
    public R updateAppManage(@RequestBody AppManage appManage)
    {
        R r = new R();
        appManage.setUpdatetime(new Date());
        try {
            if (appManageService.UpdateAppManage(appManage) > 0) {
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
    @RequestMapping(value = "AppManage/del/{license}", method = RequestMethod.POST)
    @ResponseBody
    public String del(@PathVariable("license") int license) {
        R r = new R();
        try {
            if (appManageService.delById(license) > 0) {
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
    //批量删除方法
    @RequestMapping(value = "AppManage/batchDel", method = RequestMethod.POST)
    @ResponseBody
    public R batchDel(String ids){
        R r = new R();
        if (ids!=null&&!"".equals(ids)){
            try {
                //准备一个list
                ArrayList<Integer> longs = new ArrayList<Integer>();
                //将前台传的字符串转成数组
                String[] split = ids.split(",");
                //遍历数组
                for (String s : split) {
                    //将String转换成long,并添加到list中
                    longs.add(Integer.valueOf(s));
                }
                for (Integer id:longs) {
                    appManageService.delById(id);
                }
                r.setMsg("删除成功");
                r.setCode(3);
            } catch (Exception e) {
                r.setMsg("删除失败,请重试");
                return r;
            }
        }
        return r;
    }

}
