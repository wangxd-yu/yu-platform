package org.yu.generate.core.handler;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import org.yu.generate.constant.GeneratorConst;
import org.yu.generate.core.pojo.bo.StandardModelBO;
import org.yu.generate.core.pojo.vo.MediumModelVO;
import org.yu.generate.core.pojo.vo.SimpleModelVO;

import java.time.LocalDate;
import java.util.*;

/**
 * @author wangxd
 * @date 2023-04-16 0:05
 */
public class GenerateHandler {

    /**
     * 后端模板 文件名（不带后缀）
     */
    private static final List<String> API_TEMPLATE_NAMES = Arrays.asList(
            "Controller",
            "Service",
            "ServiceImpl",
            "Repository",
            "DO"
            /*
            "Query",
            "DTO",*/
    );

    /**
     * 代码预览
     *
     * @return
     */
    public static List<Map<String, Object>> preview(StandardModelBO standardModelBO) {
        List<Map<String, Object>> genList = new ArrayList<>();
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));

        // import 引入 列表，set排重
        Set<String> importTypes = new HashSet<>();
        standardModelBO.getGenApiModuleDO().getGenApiDomainDO().getDomainFields().forEach(item -> {
            item.setJavaType(GeneratorConst.javaBoxTypeMap.getOrDefault(item.getColumnType(), "UNKNOWN"));
            item.setImportType(GeneratorConst.importsTypeMap.get(item.getColumnType()));
            if (GeneratorConst.importsTypeMap.get(item.getJavaType()) != null) {
                importTypes.add(GeneratorConst.importsTypeMap.get(item.getJavaType()));
            }
        });


        for (String templateName : API_TEMPLATE_NAMES) {
            Map<String, Object> map = new HashMap<>(1);
            Template template = engine.getTemplate("generator/api/" + templateName + ".ftl");
            map.put("content", template.render(new HashMap<String, Object>(1) {
                {
                    put("genApiModuleDO", standardModelBO.getGenApiModuleDO());
                    put("date", LocalDate.now());
                    put("importTypes", importTypes);
                }
            }));
            map.put("name", templateName);
            genList.add(map);
        }

        return genList;
    }

    /**
     * 简单模型处理
     */
    public void SimpleModelHandler() {
        // 简单模型转换为标准模型
    }

    /**
     * 简单模型转换为 标准模型
     *
     * @param simpleModelVo
     * @return
     */
    private StandardModelBO convertToStandardModel(SimpleModelVO simpleModelVo) {
        return null;
    }

    /**
     * 中级模型转换为 标准模型
     *
     * @param mediumModelVo
     * @return
     */
    private StandardModelBO convertToStandardModel(MediumModelVO mediumModelVo) {
        return null;
    }

    /**
     * 基础的代码生成引擎逻辑
     */
    private void basicEngine() {

    }
}
