package com.yeyun.yeyunpush.service;

import com.yeyun.yeyunpush.entity.AppManage;
import com.yeyun.yeyunpush.entity.AreaInfo;
import com.yeyun.yeyunpush.entity.clientinfos;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;
import java.util.List;

@Service
public class AreaInfoService extends AbstratService<AreaInfo> {

    //获取所有的数据列表
    public  List<AreaInfo> getAll()
    {
        return mapper.selectAll();
    }
    //增加
    public int InsertAppManage(AreaInfo appManage)
    {
        int result=mapper.insertSelective(appManage);
        return  result;
    }
    //修改
    public int UpdateAppManage(AreaInfo areaInfo)
    {
        Example example = new Example(AreaInfo.class);
        example.createCriteria().andEqualTo("code", areaInfo.getCode());
        int result=mapper.updateByExampleSelective(areaInfo, example);
        return  result;
    }
    //删除
    public int delAppManage(AreaInfo areaInfo)
    {
        int result=mapper.deleteByPrimaryKey(areaInfo);
        return  result;
    }
    //条件查询
    public  List<AreaInfo> findClientInfos(AreaInfo areaInfo)
    {
        Example e = new Example(AreaInfo.class);
        Criteria c = e.createCriteria();
        if(areaInfo.getId()!=0)
        {
            c.orEqualTo("id",areaInfo.getId()).orEqualTo("connected",1);
        }
        Criteria criteria = e.createCriteria();
        criteria.andLessThan("addtime", new Date());
        e.and(c);
        List<AreaInfo> lists=mapper.selectByExample(e);
        return lists;
    }

}
