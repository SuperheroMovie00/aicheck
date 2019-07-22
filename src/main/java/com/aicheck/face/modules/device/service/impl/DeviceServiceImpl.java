/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.device.service.impl;

import com.aicheck.face.modules.device.entity.Device;
import com.aicheck.face.modules.device.repository.DeviceRepository;
import com.aicheck.face.modules.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:51 PM 2019/1/25
 */
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Override
    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public List<Device> save(List<Device> t) {
        return deviceRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        deviceRepository.deleteById(integer);
    }

    @Override
    public Device update(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public List<Device> find(Device device) {
        return null;
    }

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device findById(Integer integer) {
        Optional<Device> optional = deviceRepository.findById(integer);
        return optional.isPresent()?optional.get():null;
    }

    @Override
    public Device findByMacAddress(String macAddress) {
        return deviceRepository.findByMacAddress(macAddress);
    }

    @Override
    public Device findByMacAddresser(String macAddress) {
        return deviceRepository.findByMacAddresser(macAddress);
    }


    @Override
    public List<Device> findByPlatform(String platform) {
        return deviceRepository.findByPlatform(platform);
    }

    @Override
    public Page<Device> findAll(Integer currentPage, Integer pageSize,String platform) {
        Pageable pageable = PageRequest.of(currentPage - 1,pageSize,Sort.Direction.DESC,"createTime");
        @SuppressWarnings("rawtypes")
		Specification specification = new Specification() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(platform)) {
                    predicates.add(criteriaBuilder.equal(root.get("platform"),platform));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        @SuppressWarnings("unchecked")
		Page<Device> page = deviceRepository.findAll(specification,pageable);
        return page;
    }

    @Override
    public List<Device> findAllByRegisterType() {
        return deviceRepository.findAllByRegisterType();
    }

    @Override
    public List<Device> findAllByRegisterTypeonlybox() {
        return deviceRepository.findAllByRegisterTypeonlybox();
    }


    @Override
    public List<Device> findByNotGroupDevice() {

        return deviceRepository.findByNotGroupDevice();
    }

	@Override
	public List<Device> findAllByforplatform(String platform) {
		// TODO Auto-generated method stub
		return deviceRepository.findAllByforplatform(platform);
	}

	@Override
	public Device findAllByforipaddress(String ip_address) {
		// TODO Auto-generated method stub
		return deviceRepository.findAllByforipaddress(ip_address);
	}

	//根据平台和父id查询设备
	@Override
	public List<Device> querydevicefrobox(String deviceip) {
		// TODO Auto-generated method stub
		return deviceRepository.deviceRepository(deviceip);
	}

	@Override
	public List<Device> queryrelevancefordeviceid(Integer id) {
		// TODO Auto-generated method stub
		return deviceRepository.queryrelevancefordeviceid(id);
	}
	
	//新修改的查询方法=》根据吗，mac地址以及条件平台为传入的平台
	@Override
	public Device findByMacAddressandpirfrm(String macAddress, String platform) {
		// TODO Auto-generated method stub
		return deviceRepository.findByMacAddressandpirfrm(macAddress,platform);
	}



	@Override
	public Device finddeviceforplatformandmacaddress(String platform, String macaddress) {
		// TODO Auto-generated method stub
		return deviceRepository.finddeviceforplatformandmacaddress(platform,macaddress);
	}

    @Override
    public Device findAllByformacaddress(String mac_address) {
        return deviceRepository.findAllByformacaddress(mac_address);
    }


}
