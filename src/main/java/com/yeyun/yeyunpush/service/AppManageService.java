package com.yeyun.yeyunpush.service;

import com.yeyun.yeyunpush.entity.AppManage;
import com.yeyun.yeyunpush.entity.clientinfos;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import java.util.Date;
import java.util.List;

@Service
public class AppManageService extends AbstratService<AppManage> {

    //获取所有的数据列表
    public  List<AppManage> getAll()
    {
        return mapper.selectAll();
    }
    //增加
    public int insertClient(AppManage appManage)
    {
        int result=mapper.insertSelective(appManage);
        return  result;
    }
    //修改
    public void updateClient(AppManage clientinfos)
    {
        Example example = new Example(clientinfos.class);
        example.createCriteria().andEqualTo("id", clientinfos.getId());
        int result=mapper.updateByExampleSelective(clientinfos, example);
    }
    //删除
    public int deleteClient(AppManage appManage)
    {
        int result=mapper.deleteByPrimaryKey(appManage);
        return  result;
    }
    //条件查询
    public  List<AppManage> findClientInfos(AppManage clientinfos)
    {
        Example e = new Example(clientinfos.class);
        Criteria c = e.createCriteria();
        if(clientinfos.getId()!=0)
        {
            c.orEqualTo("id",clientinfos.getId()).orEqualTo("connected",1);
        }
        Criteria criteria = e.createCriteria();
        criteria.andLessThan("addtime", new Date());
        e.and(c);
        List<AppManage> lists=mapper.selectByExample(e);
        return lists;
    }

}
