package com.destiny.lobster.api.api;


import com.destiny.lobster.api.dto.UserDTO;

import java.util.List;

public interface LobsterServiceApi {
	
	
	public List<UserDTO> queryUserList(UserDTO dto);
	
	
	
}
