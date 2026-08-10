package org.yu.generate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yu.generate.core.handler.GenerateHandler;
import org.yu.generate.core.pojo.bo.StandardModelBO;
import org.yu.generate.service.DbService;

import java.io.IOException;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-08-20
 */
@RestController
public class GenController {

    @Autowired
    private DbService dbService;

    @GetMapping("/getTest")
    public Object getAllUrl() throws IOException {
       /* Map<String,String> map = new HashMap<>();
        map.put("package","org.yu.admin.modules");
        map.put("author","wangxd");
        map.put("date","2023-04-06");
        map.put("domain","Teacher");
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        Template template;
        String templatePath = "api/Controller.ftl";

        template = engine.getTemplate(templatePath);

        String str = template.render(map);*/

        return new ResponseEntity<>(dbService.getColumns("log_endpoint"), HttpStatus.OK);
    }

    @PostMapping("/getTest1")
    public Object getAllCode(@RequestBody StandardModelBO standardModelBO) {
        return GenerateHandler.preview(standardModelBO);
    }

    @GetMapping("/mysql/tables")
    public ResponseEntity<Object> getTableList() {
        return new ResponseEntity<>(dbService.getTables(), HttpStatus.OK);
    }

    @GetMapping("/mysql/columns")
    public ResponseEntity<Object> getColumns(@RequestParam String tableName) {
        return new ResponseEntity<>(dbService.getColumns(tableName), HttpStatus.OK);
    }
}
