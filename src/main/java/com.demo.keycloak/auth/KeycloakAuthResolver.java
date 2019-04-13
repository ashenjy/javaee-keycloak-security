package com.demo.keycloak.auth;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class KeycloakAuthResolver implements KeycloakConfigResolver {
	
    private static final Logger logger = LoggerFactory.getLogger(KeycloakAuthResolver.class);

    private final Map<String, KeycloakDeployment> cache = new ConcurrentHashMap<String, KeycloakDeployment>();
    
    @Override
    public KeycloakDeployment resolve(HttpFacade.Request request) {
    	
    	String applicationId = "keycloak-demo";
       
    	KeycloakDeployment deployment = cache.get(applicationId);
        
        if (null == deployment) {
        	InputStream is= null;
            try {
            	if(System.getProperty("load.external.keycloak.json").equals("Y")){
            		//Check getProperty() return null
            		try {
            			is = new FileInputStream(System.getProperty("keycloak.realm.config.file.path")+"keycloak.json");
            		}catch(NullPointerException e) {
            			logger.error("Exception return Null file properties ");
            		}
            	} else {
            		//check getClassLoader() return null
            		try {
            			is = this.getClass().getClassLoader().getResourceAsStream("/keycloak.json");
            		}catch(NullPointerException e) {
            			logger.error("Exception return Null class loader ");
            		}
            	}
            	
                deployment = KeycloakDeploymentBuilder.build(is);
                cache.put(applicationId, deployment);
            } catch (Exception e) {
            	logger.error("Exception while get the file name--",e.getMessage());
                throw new IllegalStateException("Not able to find the file keycloak.json");
			}finally {
				 safeClose(is);				
			}
        }

        return deployment;
    }
    
    private static void safeClose(InputStream fis) {
		if (fis != null) {
    	    try {
    	    		fis.close();
    	    } catch (Exception e) {
        		logger.error("exception while get the file name--",e);
    	    } 
    }
}
}

