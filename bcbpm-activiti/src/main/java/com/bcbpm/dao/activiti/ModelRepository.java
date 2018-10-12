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
import org.springframework.stereotype.Repository;

import com.bcbpm.model.domain.editor.Model;

/**
 * mybatis repository for the Model entity.
 */
@Repository
public interface ModelRepository{
    // 查询模型主sql
    public final static String queryModel = " id,name,model_key as modelKey, description, model_comment as comment ,created, created_by as createdBy, "
            + " last_updated as lastUpdated ,last_updated_by as lastUpdatedBy, version, model_editor_json as modelEditorJson, thumbnail, model_type as modelType ";

    //    @Query("from Model as model where model.createdBy = :user and model.modelType = :modelType")
    //    List<Model> findModelsCreatedBy(String createdBy,Integer modelType, Sort sort);
    //
    //    @Query("from Model as model where model.createdBy = :user and " + "(lower(model.name) like :filter or lower(model.description) like :filter) and model.modelType = :modelType")
    //    List<Model> findModelsCreatedBy(String createdBy,Integer modelType, String filter, Sort sort);
    //
    //    @Query("from Model as model where model.key = :key and model.modelType = :modelType")
    //    List<Model> findModelsByKeyAndType(String key,Integer modelType);
    //
    //    @Query("from Model as model where (lower(model.name) like :filter or lower(model.description) like :filter) " + "and model.modelType = :modelType")
    //    List<Model> findModelsByModelType(Integer modelType, String filter);
    //
    //    @Query("from Model as model where model.modelType = :modelType")
    //    List<Model> findModelsByModelType(Integer modelType);
    //
    //    @Query("select count(m.id) from Model m where m.createdBy = :user and m.modelType = :modelType")
    //    Long countByModelTypeAndUser(int modelType, String user);
    //
    //    @Query("select m from ModelRelation mr inner join mr.model m where mr.parentModelId = :parentModelId")
    //    List<Model> findModelsByParentModelId(String parentModelId);
    //
    //    @Query("select m from ModelRelation mr inner join mr.model m where mr.parentModelId = :parentModelId and m.modelType = :modelType")
    //    List<Model> findModelsByParentModelIdAndType(String parentModelId,Integer modelType);
    //
    //    @Query("select m.id, m.name, m.modelType from ModelRelation mr inner join mr.parentModel m where mr.modelId = :modelId")
    //    List<Model> findModelsByChildModelId(String modelId);
    //
    //    @Query("select model.key from Model as model where model.id = :modelId and model.createdBy = :user")
    //    String appDefinitionIdByModelAndUser(String modelId, String user);

    @Select(" select " + queryModel + " from ACT_DE_MODEL where created_by =#{createdBy} and model_type =#{modelType} ")
    List<Model> findModelsCreatedBy(String createdBy, Integer modelType);

    //    List<Model> findModelsCreatedBy(String createdBy, Integer modelType, String filter);

    @Select(" select " + queryModel + " from ACT_DE_MODEL where model_key =#{key} and model_type =#{modelType} ")
    List<Model> findModelsByKeyAndType(String key, Integer modelType);

    List<Model> findModelsByModelType(Integer modelType, String filter);

    @Select(" select " + queryModel + " from ACT_DE_MODEL where model_type =#{modelType} ")
    List<Model> findModelsByModelType(Integer modelType);

    Long countByModelTypeAndUser(int modelType, String user);

    List<Model> findModelsByParentModelId(String parentModelId);

    List<Model> findModelsByParentModelIdAndType(String parentModelId, Integer modelType);

    List<Model> findModelsByChildModelId(String modelId);

    String appDefinitionIdByModelAndUser(String modelId, String user);

    @Select(" select " + queryModel + " from ACT_DE_MODEL where id =#{modelId} ")
    Model findOne(String modelId);

    @Insert("insert into ACT_DE_MODEL(id,name,model_key, description, model_comment , created, created_by, last_updated ,last_updated_by, version, model_editor_json, thumbnail, model_type)  "
            + "values( #{id}, #{name}, #{key}, #{description}, #{comment }, #{created}, #{createdBy}, #{lastUpdated}, #{lastUpdatedBy}, #{version}, #{modelEditorJson}, #{thumbnail}, #{modelType} )")
    int insert(Model model);

    //    @Update("update ACT_DE_MODEL  set name=#{name}, model_key=#{key}, description=#{description}, model_comment=#{comment},   last_updated=#{lastUpdated, jdbcType=TIMESTAMP}, last_updated_by=#{lastUpdatedBy},  version=#{version}, model_editor_json=#{modelEditorJson},   "
    @Update("update ACT_DE_MODEL  set name=#{name}, model_key=#{key}, description=#{description}, model_comment=#{comment},   last_updated=#{lastUpdated}, last_updated_by=#{lastUpdatedBy},  version=#{version}, model_editor_json=#{modelEditorJson},   "
            + " thumbnail=#{thumbnail}, model_type=#{modelType}   where id =#{id}")
    int save(Model model);

    @Delete(" delete from ACT_DE_MODEL where id =#{id} ")
    int delete(Model model);
}
