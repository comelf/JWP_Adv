package org.jwp.vertx;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.deploy.Verticle;


public class Vertx {
	private static final Logger log = LoggerFactory.getLogger(Vertx.class);
	
	@PostConstruct
	public void initialize(){
		//Verticle verticle = org.vertx.java.core.Vertx.newVertx();
		
		
		try {
			//verticle.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	

}
