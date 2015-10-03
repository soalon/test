/**
 * 
 */
package com.soalon.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service; 

import com.soalon.db.dao.IUitsettledetDao;
import com.soalon.db.service.IUitsettledetService;

/**
 * @author Soalon
 *
 */
@Service
public class UitsettledetService implements IUitsettledetService {
  
    @Autowired  
    private IUitsettledetDao uitDao;

	/* (non-Javadoc)
	 * @see com.soalon.service.IOrderService#getOrderCount()
	 */
	@Override
	public int getUitsettledetCount() {
		// TODO Auto-generated method stub
		return uitDao.getUitsettledetCount();
	}

}
