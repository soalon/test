/**
 * 
 */
package com.soalon.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.soalon.db.service.IUitsettledetService;

/**
 * @author Soalon
 *
 */
public class MyBatisTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")  
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");    
        IUitsettledetService orderService = (IUitsettledetService)context.getBean("uitsettledetService");    
        int count = orderService.getUitsettledetCount();    
        System.out.println("count:" + count);
	}

}
