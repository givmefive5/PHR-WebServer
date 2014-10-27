package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.RestaurantDao;
import phr.dao.sqlimpl.RestaurantDaoSqlImpl;
import phr.exceptions.DataAccessException;
import phr.exceptions.ServiceException;
import phr.models.Food;
import phr.service.RestaurantService;

@Service("restaurantService")

public class RestaurantServiceImpl implements RestaurantService {

	//@Autowired 
	//RestaurantDao restaurantDao;
	
	RestaurantDao restaurantDao = new RestaurantDaoSqlImpl();
	

}
