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

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bcbpm.activiti.model.editor.AppDefinitionRepresentation;
import com.bcbpm.activiti.model.editor.ResultListDataRepresentation;

/**
 * REST controller for managing the app definitions.
 */
@RestController
@RequestMapping(value = "/app")
public class AppDefinitionsResource{

    @RequestMapping(value = "/rest/runtime/app-definitions", method = RequestMethod.GET)
    public ResultListDataRepresentation getAppDefinitions(){
        AppDefinitionRepresentation kickstartAppDefinitionRepresentation = AppDefinitionRepresentation.createDefaultAppDefinitionRepresentation("kickstart");

        AppDefinitionRepresentation taskAppDefinitionRepresentation = AppDefinitionRepresentation.createDefaultAppDefinitionRepresentation("tasks");

        AppDefinitionRepresentation idmAppDefinitionRepresentation = AppDefinitionRepresentation.createDefaultAppDefinitionRepresentation("identity");
        List<AppDefinitionRepresentation> resultList = new ArrayList<AppDefinitionRepresentation>();

        // Default app: kickstart
        resultList.add(kickstartAppDefinitionRepresentation);

        // Default app: tasks and IDM (available for all)
        resultList.add(taskAppDefinitionRepresentation);
        resultList.add(idmAppDefinitionRepresentation);
        ResultListDataRepresentation result = new ResultListDataRepresentation(resultList);
        return result;
    }

}
