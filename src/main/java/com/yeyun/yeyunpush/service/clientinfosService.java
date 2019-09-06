package com.yeyun.yeyunpush.service;

import com.yeyun.yeyunpush.entity.clientinfos;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class clientinfosService extends AbstratService<clientinfos> {

    public void updateClient(clientinfos clientinfos)
    {
        Example example = new Example(clientinfos.class);
        example.createCriteria().andEqualTo("clientid", clientinfos.getClientid());
        int result=mapper.updateByExampleSelective(clientinfos, example);
    }

    public  List<clientinfos> findClientInfos(clientinfos clientinfos)
    {

        Example e = new Example(clientinfos.class);
        Example.Criteria c = e.createCriteria();
        if(!StringUtils.isBlank(clientinfos.getClientid()))
        {
            c.orEqualTo("clientid",clientinfos.getClientid()).orEqualTo("connected",1);
        }
        Example.Criteria criteria = e.createCriteria();
        criteria.andLessThan("lastconnecteddate", new Date());
        e.and(c);
        List<clientinfos> lists=mapper.selectByExample(e);
        return lists;
    }

}
