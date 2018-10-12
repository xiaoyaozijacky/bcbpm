
package com.bcbpm.activiti.model.editor;

import java.util.Date;

import com.bcbpm.model.domain.editor.AbstractModel;
import com.bcbpm.model.domain.editor.Model;
import com.bcbpm.model.domain.editor.ModelHistory;

/**
 * <p>Title: ModelRepresentation</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月30日 下午4:15:47
 * @version :
 * @description:
 */
public class ModelRepresentation extends AbstractRepresentation{

    protected String id;
    protected String name;
    protected String key;
    protected String description;
    protected String createdBy;
    protected String lastUpdatedBy;
    protected Date lastUpdated;
    protected boolean latestVersion;
    protected int version;
    protected String comment;
    protected Integer modelType;

    public ModelRepresentation(AbstractModel model){
        initialize(model);
    }

    public ModelRepresentation(){

    }

    public void initialize(AbstractModel model){
        this.id = model.getId();
        this.name = model.getName();
        this.key = model.getKey();
        this.description = model.getDescription();
        this.createdBy = model.getCreatedBy();
        this.lastUpdated = model.getLastUpdated();
        this.version = model.getVersion();
        this.lastUpdatedBy = model.getLastUpdatedBy();
        this.comment = model.getComment();
        this.modelType = model.getModelType();

        // When based on a ProcessModel and not history, this is always the latest version
        if(model instanceof Model){
            this.setLatestVersion(true);
        }else if(model instanceof ModelHistory){
            this.setLatestVersion(false);
        }
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Date getLastUpdated(){
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated){
        this.lastUpdated = lastUpdated;
    }

    public String getCreatedBy(){
        return createdBy;
    }

    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    public void setLatestVersion(boolean latestVersion){
        this.latestVersion = latestVersion;
    }

    public boolean isLatestVersion(){
        return latestVersion;
    }

    public int getVersion(){
        return version;
    }

    public void setVersion(int version){
        this.version = version;
    }

    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getComment(){
        return comment;
    }

    public Integer getModelType(){
        return modelType;
    }

    public void setModelType(Integer modelType){
        this.modelType = modelType;
    }

    public Model toModel(){
        Model model = new Model();
        model.setName(name);
        model.setDescription(description);
        return model;
    }

    /**
     * Update all editable properties of the given {@link Model} based on the values in this instance.
     */
    public void updateModel(Model model){
        model.setDescription(this.description);
        model.setName(this.name);
    }
}
