package com.demo.keycloak.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.Map;

@Path("/keycloak-demo")
public class PersonAPI {

    private static Gson gson = new GsonBuilder().create();

    @POST
    @Path("person/{name}")
    public Response start(@PathParam("name") String personName, String requestBody,
                          @HeaderParam("Authorization") String authorization, @Context UriInfo uriInfo) {

        int httpResponseCode = 500;
        Map<String, Object> reponseMap = new HashMap<>();

        final String path = uriInfo.getAbsolutePath().getPath();
        final String appName = path.substring(1, path.indexOf("-"));
        logger.info("\n\n Application Name : " + appName);

        reponseMap.put("CLIENT_ID", appName);
        reponseMap.put("SUCESS", true);
        reponseMap.put("PERSON_NAME", personName);

        return Response.status(httpResponseCode).entity(convertMapToJSONString(reponseMap)).header("Content-Type", "application/json")
                .build();
    }

    public String convertMapToJSONString(Map<String, Object> responseMap) {
        logger.debug("\n\n convertMapToJSONString :: responseMap :" + responseMap.toString());
        return gson.toJson(responseMap);
    }


    private static final Logger logger = LoggerFactory.getLogger(PersonAPI.class);

}
