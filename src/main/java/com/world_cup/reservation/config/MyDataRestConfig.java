//package com.world_cup.reservation.config;
//
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
//import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//
//@Configurable
//public class MyDataRestConfig implements RepositoryRestConfigurer {
//
//    private String the_allowed_origins = "*";
//    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
//                                                     CorsRegistry cors) {
//
//        System.out.println("configureRepositoryRestConfiguration");
//        HttpMethod[] the_unsupported_actions = {};
////        config.exposeIdsFor(User.class);
////        disableHttpMethods(User.class , config, the_unsupported_actions);
//
//        /*Configure CORS Mapping*/
//        cors.addMapping(config.getBasePath()  + "/**")
//                .allowedOrigins(the_allowed_origins);
//
//
//
//    }
//    private void disableHttpMethods(Class the_class , RepositoryRestConfiguration config, HttpMethod [] the_unsupported_actions ){
//        config.getExposureConfiguration()
//                .forDomainType(the_class)
//                .withItemExposure((metdata, httpMethods) ->
//                        httpMethods.disable(the_unsupported_actions))
//                .withCollectionExposure((metdata, httpMethods) ->
//                        httpMethods.disable(the_unsupported_actions));
//    }
//}
