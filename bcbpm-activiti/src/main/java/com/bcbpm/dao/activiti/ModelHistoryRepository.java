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
package com.bcbpm.dao.activiti;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bcbpm.model.domain.editor.ModelHistory;

/**
 * Spring Data JPA repository for the ModelHistory entity.
 */
public interface ModelHistoryRepository{
    public final static String queryModel = " id,name,model_key as modelKey, description, model_comment as comment ,created, created_by as createdBy, "
            + " last_updated as lastUpdated ,last_updated_by as lastUpdatedBy, version, model_editor_json as modelEditorJson, model_type as modelType ";

    @Select(" select " + queryModel + ",model_id as modelId, removal_date as removalDate from ACT_DE_MODEL_HISTORY where created_by =#{createdBy} and model_type =#{modelType} ")
    List<ModelHistory> findByCreatedByAndModelTypeAndRemovalDateIsNull(String createdBy, Integer modelType);

    @Select(" select " + queryModel + ",model_id as modelId, removal_date as removalDate from ACT_DE_MODEL_HISTORY where model_id =#{modelId}  order by version desc")
    List<ModelHistory> findByModelIdAndRemovalDateIsNullOrderByVersionDesc(String modelId);

    List<ModelHistory> findByModelIdOrderByVersionDesc(Long modelId);

    @Select(" select " + queryModel + ",model_id as modelId, removal_date as removalDate from ACT_DE_MODEL_HISTORY where id =#{modelId}")
    ModelHistory findOne(String modelId);

    @Insert("insert into ACT_DE_MODEL_HISTORY(id,name,model_key, description, model_comment , created, created_by, last_updated ,last_updated_by, removal_date, version, model_editor_json, model_type)  "
            + "values( #{id}, #{name}, #{key}, #{description}, #{comment }, #{created}, #{createdBy}, #{lastUpdated}, #{lastUpdatedBy}, #{removalDate}, #{version}, #{modelEditorJson},  #{modelType} )")
    int insert(ModelHistory modelHistory);

    @Update("update ACT_DE_MODEL_HISTORY  set name=#{name}, model_key=#{key}, description=#{description}, model_comment=#{comment},   last_updated=#{lastUpdated}, last_updated_by=#{lastUpdatedBy}, removal_date=#{removalDate}, version=#{version}, model_editor_json=#{modelEditorJson},   "
            + " model_id=#{modelId}, model_type=#{modelType}   where id =#{id}")
    int save(ModelHistory modelHistory);

    @Delete(" delete from ACT_DE_MODEL where id =#{id} ")
    void delete(ModelHistory modelHistory);
}
