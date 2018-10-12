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

import com.bcbpm.model.domain.editor.ModelInformation;
import com.bcbpm.model.domain.editor.ModelRelation;

/**
 * @author jbarrez
 */
public interface ModelRelationRepository{

    //	@Query("from ModelRelation mr where mr.parentModelId = :parentModelId")
    //	List<ModelRelation> findByParentModelId(String parentModelId);
    //	
    //	@Query("from ModelRelation mr where mr.parentModelId = :parentModelId and mr.type = :type")
    //	List<ModelRelation> findByParentModelIdAndType(String parentModelId, String type);
    //	
    //	@Query("from ModelRelation mr where mr.modelId = :modelId")
    //	List<ModelRelation> findByChildModelId(String modelId);
    //	
    //	@Query("from ModelRelation mr where mr.modelId = :modelId and mr.type = :type")
    //	List<ModelRelation> findByChildModelIdAndType(String modelId, String type);
    //	
    //	@Query("select m.id, m.name, m.modelType from ModelRelation mr inner join mr.model m where mr.parentModelId = :parentModelId")
    //	List<ModelInformation> findModelInformationByParentModelId(String parentModelId);
    //	
    //	@Query("select m.id, m.name, m.modelType from ModelRelation mr inner join mr.parentModel m where mr.modelId = :modelId")
    //	List<ModelInformation> findModelInformationByChildModelId(String modelId);
    //	
    //	@Modifying
    //	@Query("delete from ModelRelation mr where mr.parentModelId = :parentModelId")
    //	void deleteModelRelationsForParentModel(String parentModelId);

    List<ModelRelation> findByParentModelId(String parentModelId);

    @Select(" select id, parent_model_id as parentModelId, model_id as modelId, relation_type as type from ACT_DE_MODEL_RELATION where parent_model_id =#{parentModelId} and relation_type=#{type}  ")
    List<ModelRelation> findByParentModelIdAndType(String parentModelId, String type);

    @Select(" select id, parent_model_id as parentModelId, model_id as modelId, relation_type as type from ACT_DE_MODEL_RELATION where model_id =#{modelId}  ")
    List<ModelRelation> findByChildModelId(String modelId);

    @Select(" select id, parent_model_id as parentModelId, model_id as modelId, relation_type as type from ACT_DE_MODEL_RELATION where model_id =#{modelId} and relation_type=#{type}  ")
    List<ModelRelation> findByChildModelIdAndType(String modelId, String type);

    @Select(" select mr.id, mr.parent_model_id as parentModelId, mr.model_id as modelId, mr.relation_type as type from ACT_DE_MODEL_RELATION mr, ACT_DE_MODEL md where md.id=mr.model_id and mr.parent_model_id =#{parentModelId} ")
    List<ModelInformation> findModelInformationByParentModelId(String parentModelId);

    @Select(" select mr.id, mr.parent_model_id as parentModelId, mr.model_id as modelId, mr.relation_type as type from ACT_DE_MODEL_RELATION mr, ACT_DE_MODEL md where md.id=mr.model_id and mr.model_id =#{modelId} ")
    List<ModelInformation> findModelInformationByChildModelId(String modelId);

    @Delete(" delete from ACT_DE_MODEL_RELATION where parent_model_id =#{parentModelId} ")
    void deleteModelRelationsForParentModel(String parentModelId);

    @Delete(" delete from ACT_DE_MODEL_RELATION where id =#{id} ")
    void delete(String id);

    void delete(List<ModelRelation> modelRelations);

    @Insert("insert into ACT_DE_MODEL_RELATION(id,parent_model_id,model_id, relation_type)  values ( #{id}, #{parentModelId}, #{modelId}, #{type})")
    int save(ModelRelation model);
}
