package com.destiny.wolf.service;

import com.destiny.wolf.entity.PersonProfile;

import java.io.IOException;
import java.util.List;

public interface PersonProfileService {
	
	/**
	 * 返回查询结果
	 */
	List<PersonProfile> queryPersonProfile(PersonProfile entity) throws IOException;
}
