package com.zhq.activiti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @program: activitispringboot
 * @description: 调出流程图绘制界面
 * @author: HQ Zheng
 * @create: 2019-08-28 09:45
 */
@RestController
public class ActivitiModelController {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ActivitiModelController.class);

    @Autowired
    private ProcessEngine processEngine;//定义流程引擎
    @Autowired
    private ObjectMapper objectMapper;
    private final static Integer MODEL_REVISION = 1;//模型的版本
    private final static String ACTIVITI_REDIRECT_MODELER_INDEX = "/activitiService/modeler.html?modelId=";// modeler.html页面地址
    private final static String ACTIVITI_NAMESPACE_VALUE = "http://b3mn.org/stencilset/bpmn2.0#";// 默认的空间值
    private final static String ACTIVITI_ID_VALUE = "canvas"; // 默认ID值

    /**
     * 新建一个模型
     * @param response
     * @throws IOException
     */
    @RequestMapping("/activiti-ui.html")
    public void newModel(HttpServletResponse response) throws IOException {
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 初始化空的流程资源模型，填充信息并持久化模型
        Model model = repositoryService.newModel();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, uuid);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, "");
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, MODEL_REVISION);
        model.setName(uuid);
        model.setKey(uuid);
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);

        // 将 editorNode  数据填充到模型中, 并做页面的重定向
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", ACTIVITI_ID_VALUE);
        editorNode.put("resourceId", ACTIVITI_ID_VALUE);
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",ACTIVITI_NAMESPACE_VALUE);
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(model.getId(),editorNode.toString().getBytes("utf-8"));
        response.sendRedirect(ACTIVITI_REDIRECT_MODELER_INDEX + model.getId());
    }
}
