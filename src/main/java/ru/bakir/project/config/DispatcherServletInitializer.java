package ru.bakir.project.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

// it is a replacement of web.xml
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // here we point to class which replace applicationContextMVC.xml file
        // так же как и в web.xml мы указывали в applicationContext при регистраций сервлета, так же
        // тут мы должны указать на Java basec Configuration а точнее SpringConfig класс который мы используем
        return new Class[]{SpringConfig.class};
        // теперь этот класс, который исполняет роль web.xml знает, где находится Spring конфигурация
    }

    @Override
    protected String[] getServletMappings() {
        // mapping который мы указывали в web.xml
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }
}
