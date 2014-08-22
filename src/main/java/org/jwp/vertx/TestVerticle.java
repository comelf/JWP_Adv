package org.jwp.vertx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.deploy.Verticle;

public class TestVerticle extends Verticle{
	private static final Logger log = LoggerFactory
			.getLogger(TestVerticle.class);
	
	@Override
	public void start() throws Exception {
		log.debug("Verticle Start!");
		vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
			
			@Override
			public void handle(HttpServerRequest req) {
				 String file = req.path.equals("/") ? "index.html" : req.path;
	                req.response.sendFile("webroot/" + file);
			}
		}).listen(9999);
		
		
	}
}
