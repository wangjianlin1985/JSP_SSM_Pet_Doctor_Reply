package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionClass {
    /*分类id*/
    private Integer classId;
    public Integer getClassId(){
        return classId;
    }
    public void setClassId(Integer classId){
        this.classId = classId;
    }

    /*分类名称*/
    @NotEmpty(message="分类名称不能为空")
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*分类描述*/
    @NotEmpty(message="分类描述不能为空")
    private String classDesc;
    public String getClassDesc() {
        return classDesc;
    }
    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonQuestionClass=new JSONObject(); 
		jsonQuestionClass.accumulate("classId", this.getClassId());
		jsonQuestionClass.accumulate("className", this.getClassName());
		jsonQuestionClass.accumulate("classDesc", this.getClassDesc());
		return jsonQuestionClass;
    }}