/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bcbpm.activiti.controller;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.bcbpm.activiti.model.editor.AppDefinitionListModelRepresentation;
import com.bcbpm.activiti.model.editor.ModelRepresentation;
import com.bcbpm.activiti.model.editor.ResultListDataRepresentation;
import com.bcbpm.activiti.service.editor.ModelService;
import com.bcbpm.dao.activiti.ModelRepository;
import com.bcbpm.framework.data.access.BusinessException;
import com.bcbpm.framework.session.SessionDeal;
import com.bcbpm.model.domain.editor.AbstractModel;
import com.bcbpm.model.domain.editor.AppDefinition;
import com.bcbpm.model.domain.editor.Model;
import com.bcbpm.model.user.User;
import com.bcbpm.util.XmlUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AbstractModelsResource{

    protected static final String FILTER_SHARED_WITH_ME = "sharedWithMe";
    protected static final String FILTER_SHARED_WITH_OTHERS = "sharedWithOthers";
    protected static final String FILTER_FAVORITE = "favorite";

    protected static final String SORT_NAME_ASC = "nameAsc";
    protected static final String SORT_NAME_DESC = "nameDesc";
    protected static final String SORT_MODIFIED_ASC = "modifiedAsc";
    protected static final int MIN_FILTER_LENGTH = 1;

    private final Logger logger = LoggerFactory.getLogger(AbstractModelsResource.class);

    @Autowired
    protected ModelRepository modelRepository;

    @Autowired
    protected ModelService modelService;

    @Autowired
    protected ObjectMapper objectMapper;

    //    private SessionDeal sessionDeal = (SessionDeal) SpringContextUtil.getBean("sessionDeal");
    @Autowired
    private SessionDeal sessionDeal;

    protected BpmnXMLConverter bpmnXmlConverter = new BpmnXMLConverter();
    protected BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    public ResultListDataRepresentation getModels(String filter, String sort, Integer modelType, HttpServletRequest request){

        // need to parse the filterText parameter ourselves, due to encoding issues with the default parsing.
        String filterText = null;
        List<NameValuePair> params = URLEncodedUtils.parse(request.getQueryString(), Charset.forName("UTF-8"));
        if(params != null){
            for(NameValuePair nameValuePair : params){
                if("filterText".equalsIgnoreCase(nameValuePair.getName())){
                    filterText = nameValuePair.getValue();
                }
            }
        }

        List<ModelRepresentation> resultList = new ArrayList<ModelRepresentation>();
        List<Model> models = null;

        //        String validFilter = makeValidFilterText(filterText);

        User user = (User) sessionDeal.getNowUserFront();

        //        if(validFilter != null){
        //            models = modelRepository.findModelsCreatedBy(user.getUserId(), modelType, validFilter);
        //
        //        }else{
        models = modelRepository.findModelsCreatedBy(user.getUserId(), modelType);
        //        }

        if(CollectionUtils.isNotEmpty(models)){
            List<String> addedModelIds = new ArrayList<String>();
            for(Model model : models){
                if(addedModelIds.contains(model.getId()) == false){
                    addedModelIds.add(model.getId());
                    ModelRepresentation representation = createModelRepresentation(model);
                    resultList.add(representation);
                }
            }
        }

        ResultListDataRepresentation result = new ResultListDataRepresentation(resultList);
        return result;
    }

    public ResultListDataRepresentation getModelsToIncludeInAppDefinition(){

        List<ModelRepresentation> resultList = new ArrayList<ModelRepresentation>();

        User user = (User) sessionDeal.getNowUserFront();
        List<String> addedModelIds = new ArrayList<String>();

        List<Model> models = modelRepository.findModelsCreatedBy(user.getUserId(), 0);

        if(CollectionUtils.isNotEmpty(models)){
            for(Model model : models){
                if(addedModelIds.contains(model.getId()) == false){
                    addedModelIds.add(model.getId());
                    ModelRepresentation representation = createModelRepresentation(model);
                    resultList.add(representation);
                }
            }
        }

        ResultListDataRepresentation result = new ResultListDataRepresentation(resultList);
        return result;
    }

    public ModelRepresentation importProcessModel(HttpServletRequest request, MultipartFile file){

        String fileName = file.getOriginalFilename();
        if(fileName != null && (fileName.endsWith(".bpmn") || fileName.endsWith(".bpmn20.xml"))){
            try{
                XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
                InputStreamReader xmlIn = new InputStreamReader(file.getInputStream(), "UTF-8");
                XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
                BpmnModel bpmnModel = bpmnXmlConverter.convertToBpmnModel(xtr);
                if(CollectionUtils.isEmpty(bpmnModel.getProcesses())){
                    throw new BusinessException("No process found in definition " + fileName);
                }

                if(bpmnModel.getLocationMap().size() == 0){
                    BpmnAutoLayout bpmnLayout = new BpmnAutoLayout(bpmnModel);
                    bpmnLayout.execute();
                }

                ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);

                org.activiti.bpmn.model.Process process = bpmnModel.getMainProcess();
                String name = process.getId();
                if(StringUtils.isNotEmpty(process.getName())){
                    name = process.getName();
                }
                String description = process.getDocumentation();

                ModelRepresentation model = new ModelRepresentation();
                model.setKey(process.getId());
                model.setName(name);
                model.setDescription(description);
                model.setModelType(AbstractModel.MODEL_TYPE_BPMN);
                Model newModel = modelService.createModel(model, modelNode.toString(), (User) sessionDeal.getNowUserFront());
                return new ModelRepresentation(newModel);

            }catch(BusinessException e){
                throw e;

            }catch(Exception e){
                logger.error("Import failed for " + fileName, e);
                throw new BusinessException("Import failed for " + fileName + ", error message " + e.getMessage());
            }
        }else{
            throw new BusinessException("Invalid file name, only .bpmn and .bpmn20.xml files are supported not " + fileName);
        }
    }

    protected ModelRepresentation createModelRepresentation(AbstractModel model){
        ModelRepresentation representation = null;
        if(model.getModelType() != null && model.getModelType() == 3){
            representation = new AppDefinitionListModelRepresentation(model);

            AppDefinition appDefinition = null;
            try{
                appDefinition = objectMapper.readValue(model.getModelEditorJson(), AppDefinition.class);
            }catch(Exception e){
                logger.error("Error deserializing app " + model.getId(), e);
                throw new BusinessException("Could not deserialize app definition");
            }
            ((AppDefinitionListModelRepresentation) representation).setAppDefinition(appDefinition);

        }else{
            representation = new ModelRepresentation(model);
        }
        return representation;
    }

    protected String makeValidFilterText(String filterText){
        String validFilter = null;

        if(filterText != null){
            String trimmed = StringUtils.trim(filterText);
            if(trimmed.length() >= MIN_FILTER_LENGTH){
                validFilter = "%" + trimmed.toLowerCase() + "%";
            }
        }
        return validFilter;
    }

    //    protected Sort getSort(String sort, boolean prefixWithProcessModel){
    //        String propName;
    //        Direction direction;
    //        if(SORT_NAME_ASC.equals(sort)){
    //            if(prefixWithProcessModel){
    //                propName = "model.name";
    //            }else{
    //                propName = "name";
    //            }
    //            direction = Direction.ASC;
    //        }else if(SORT_NAME_DESC.equals(sort)){
    //            if(prefixWithProcessModel){
    //                propName = "model.name";
    //            }else{
    //                propName = "name";
    //            }
    //            direction = Direction.DESC;
    //        }else if(SORT_MODIFIED_ASC.equals(sort)){
    //            if(prefixWithProcessModel){
    //                propName = "model.lastUpdated";
    //            }else{
    //                propName = "lastUpdated";
    //            }
    //            direction = Direction.ASC;
    //        }else{
    //            // Default sorting
    //            if(prefixWithProcessModel){
    //                propName = "model.lastUpdated";
    //            }else{
    //                propName = "lastUpdated";
    //            }
    //            direction = Direction.DESC;
    //        }
    //        return new Sort(direction, propName);
    //    }

}
