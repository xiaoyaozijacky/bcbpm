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
package com.bcbpm.activiti.service.editor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcbpm.activiti.model.editor.AppDefinitionServiceRepresentation;
import com.bcbpm.activiti.service.editor.AppDefinitionService;
import com.bcbpm.dao.activiti.ModelHistoryRepository;
import com.bcbpm.dao.activiti.ModelRepository;
import com.bcbpm.framework.data.access.BusinessException;
import com.bcbpm.framework.session.SessionDeal;
import com.bcbpm.model.domain.editor.AbstractModel;
import com.bcbpm.model.domain.editor.AppDefinition;
import com.bcbpm.model.domain.editor.AppModelDefinition;
import com.bcbpm.model.domain.editor.Model;
import com.bcbpm.model.domain.editor.ModelHistory;
import com.bcbpm.model.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class AppDefinitionServiceImpl implements AppDefinitionService{

    private final Logger logger = LoggerFactory.getLogger(AppDefinitionServiceImpl.class);

    @Autowired
    protected ModelRepository modelRepository;

    @Autowired
    protected ModelHistoryRepository modelHistoryRepository;

    @Autowired
    protected ObjectMapper objectMapper;
    //    private SessionDeal sessionDeal = (SessionDeal) SpringContextUtil.getBean("sessionDeal");
    @Autowired
    private SessionDeal sessionDeal;

    @Override
    public List<AppDefinitionServiceRepresentation> getAppDefinitions(){
        Map<String, AbstractModel> modelMap = new HashMap<String, AbstractModel>();
        List<AppDefinitionServiceRepresentation> resultList = new ArrayList<AppDefinitionServiceRepresentation>();
        User user = (User) sessionDeal.getNowUserFront();
        List<Model> createdByModels = modelRepository.findModelsCreatedBy(user.getUserId(), AbstractModel.MODEL_TYPE_APP);
        for(Model model : createdByModels){
            modelMap.put(model.getId(), model);
        }

        for(AbstractModel model : modelMap.values()){
            resultList.add(createAppDefinition(model));
        }

        return resultList;
    }

    /**
     * Gathers all 'deployable' app definitions for the current user.
     * 
     * To find these: - All historical app models are fetched. Only the highest version of each app model is retained. - All historical app models shared with the groups the current user is part of are
     * fetched. Only the highest version of each app model is retained.
     */
    @Override
    public List<AppDefinitionServiceRepresentation> getDeployableAppDefinitions(User user){
        Map<String, ModelHistory> modelMap = new HashMap<String, ModelHistory>();
        List<AppDefinitionServiceRepresentation> resultList = new ArrayList<AppDefinitionServiceRepresentation>();

        List<ModelHistory> createdByModels = modelHistoryRepository.findByCreatedByAndModelTypeAndRemovalDateIsNull(user.getUserId(), AbstractModel.MODEL_TYPE_APP);
        for(ModelHistory modelHistory : createdByModels){
            if(modelMap.containsKey(modelHistory.getModelId())){
                if(modelHistory.getVersion() > modelMap.get(modelHistory.getModelId()).getVersion()){
                    modelMap.put(modelHistory.getModelId(), modelHistory);
                }
            }else{
                modelMap.put(modelHistory.getModelId(), modelHistory);
            }
        }

        for(ModelHistory model : modelMap.values()){
            Model latestModel = modelRepository.findOne(model.getModelId());
            if(latestModel != null){
                resultList.add(createAppDefinition(model));
            }
        }

        return resultList;
    }

    protected AppDefinitionServiceRepresentation createAppDefinition(AbstractModel model){
        AppDefinitionServiceRepresentation resultInfo = new AppDefinitionServiceRepresentation();
        if(model instanceof ModelHistory){
            resultInfo.setId(((ModelHistory) model).getModelId());
        }else{
            resultInfo.setId(model.getId());
        }
        resultInfo.setName(model.getName());
        resultInfo.setDescription(model.getDescription());
        resultInfo.setVersion(model.getVersion());
        resultInfo.setDefinition(model.getModelEditorJson());

        AppDefinition appDefinition = null;
        try{
            appDefinition = objectMapper.readValue(model.getModelEditorJson(), AppDefinition.class);
        }catch(Exception e){
            logger.error("Error deserializing app " + model.getId(), e);
            throw new BusinessException("Could not deserialize app definition");
        }

        if(appDefinition != null){
            resultInfo.setTheme(appDefinition.getTheme());
            resultInfo.setIcon(appDefinition.getIcon());
            List<AppModelDefinition> models = appDefinition.getModels();
            if(CollectionUtils.isNotEmpty(models)){
                List<String> modelIds = new ArrayList<String>();
                for(AppModelDefinition appModelDef : models){
                    modelIds.add(appModelDef.getId());
                }
                resultInfo.setModels(modelIds);
            }
        }
        return resultInfo;
    }

    @Override
    public String getDefinitionIdForModelAndUser(String modelId, User user){
        String appDefinitionId = this.modelRepository.appDefinitionIdByModelAndUser(modelId, user.getUserId());
        return appDefinitionId;
    }

}
