package com.destiny.lobster.service.api;

import com.destiny.lobster.api.LobsterServiceApi;
import com.destiny.lobster.api.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@DubboService
public class LobsterServiceApiImpl implements LobsterServiceApi {
	
	
	@Override
	public List<UserDTO> queryUserList(UserDTO dto) {
		
		List<UserDTO> list = new ArrayList<>();
		UserDTO dto1 = new UserDTO();
		dto1.setId(3000_000L);
		dto1.setName("小明");
		list.add(dto1);
		return list;
	}
}
