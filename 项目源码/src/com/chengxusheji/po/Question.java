package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Question {
    /*帖子id*/
    private Integer postInfoId;
    public Integer getPostInfoId(){
        return postInfoId;
    }
    public void setPostInfoId(Integer postInfoId){
        this.postInfoId = postInfoId;
    }

    /*问题标题*/
    @NotEmpty(message="问题标题不能为空")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*问题分类*/
    private QuestionClass questionClassObj;
    public QuestionClass getQuestionClassObj() {
        return questionClassObj;
    }
    public void setQuestionClassObj(QuestionClass questionClassObj) {
        this.questionClassObj = questionClassObj;
    }

    /*问题内容*/
    @NotEmpty(message="问题内容不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*浏览量*/
    @NotNull(message="必须输入浏览量")
    private Integer hitNum;
    public Integer getHitNum() {
        return hitNum;
    }
    public void setHitNum(Integer hitNum) {
        this.hitNum = hitNum;
    }

    /*提问人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*提问时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonQuestion=new JSONObject(); 
		jsonQuestion.accumulate("postInfoId", this.getPostInfoId());
		jsonQuestion.accumulate("title", this.getTitle());
		jsonQuestion.accumulate("questionClassObj", this.getQuestionClassObj().getClassName());
		jsonQuestion.accumulate("questionClassObjPri", this.getQuestionClassObj().getClassId());
		jsonQuestion.accumulate("content", this.getContent());
		jsonQuestion.accumulate("hitNum", this.getHitNum());
		jsonQuestion.accumulate("userObj", this.getUserObj().getName());
		jsonQuestion.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonQuestion.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonQuestion;
    }}