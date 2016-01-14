package com.sunjee.btms.test;

import freemarker.template.*;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/28.
 */
public class AppTest {

    @Test
    public void freeMarkerTest() throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File("D:/template/"));
        cfg.setDefaultEncoding("UTF-8");
        Template template = cfg.getTemplate("test.xml");
        Writer writer = new FileWriter(new File("d:/test.xls"));
        Map<String,Object> data = new HashMap<>();
        data.put("area","A区");
        data.put("code","A001");
        data.put("row",2);
        template.process(data,writer);
    }
}
