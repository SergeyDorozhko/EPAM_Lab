package com.epam.lab.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SpringWebAppInitializer implements WebApplicationInitializer {

    private static final String SERVLET_NAME = "SpringDispatcher";
    private static final String URL_PATTERN = "/";
    private static final String ENCODING_FILTER = "encodingFilter";
    private static final String ENCODING = "encoding";
    private static final String FORSE_ENCODING = "forseEncoding";
    private static final String UTF_8 = "UTF-8";
    private static final String VALUE_BOOLEAN = "true";
    private static final String MAPPING_URL_PATTERN = "/*";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();

        applicationContext.register(ControllerConfig.class);

        ServletRegistration.Dynamic dispatcher = servletContext
                .addServlet(SERVLET_NAME, new DispatcherServlet(applicationContext));

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(URL_PATTERN);

        FilterRegistration.Dynamic filterRegistration = servletContext
                .addFilter(ENCODING_FILTER, CharacterEncodingFilter.class);

        filterRegistration.setInitParameter(ENCODING, UTF_8);
        filterRegistration.setInitParameter(FORSE_ENCODING, VALUE_BOOLEAN);
        filterRegistration.addMappingForUrlPatterns(null, true, MAPPING_URL_PATTERN);
    }
}
