package com.yeyun.yeyunpush.repository;


import com.yeyun.yeyunpush.entity.ClientInfo;
import org.springframework.data.repository.CrudRepository;

public interface ClientInfoRepository extends CrudRepository<ClientInfo, String>{
    ClientInfo findClientByclientid(String clientId);
}