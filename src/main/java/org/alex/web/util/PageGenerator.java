package org.alex.web.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static final String HTML_DIR = "src/main/resources/templates/";

//    src/main/resources/templates/

    public static PageGenerator pageGenerator;
    private final Configuration cfg;

    private PageGenerator() {

        cfg = new Configuration();

    }




    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }


    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
//            Template template = cfg.getTemplate(HTML_DIR + filename);
            Template template;
            cfg.setClassForTemplateLoading(this.getClass(), "/templates/");
            template = cfg.getTemplate(filename);
//            File.separator +
            template.process(data, stream);

        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }

}