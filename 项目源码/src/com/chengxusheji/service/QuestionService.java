package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.QuestionClass;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.Question;

import com.chengxusheji.mapper.QuestionMapper;
@Service
public class QuestionService {

	@Resource QuestionMapper questionMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加问题记录*/
    public void addQuestion(Question question) throws Exception {
    	questionMapper.addQuestion(question);
    }

    /*按照查询条件分页查询问题记录*/
    public ArrayList<Question> queryQuestion(String title,QuestionClass questionClassObj,UserInfo userObj,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_question.title like '%" + title + "%'";
    	if(null != questionClassObj && questionClassObj.getClassId()!= null && questionClassObj.getClassId()!= 0)  where += " and t_question.questionClassObj=" + questionClassObj.getClassId();
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_question.userObj='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) where = where + " and t_question.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return questionMapper.queryQuestion(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Question> queryQuestion(String title,QuestionClass questionClassObj,UserInfo userObj,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_question.title like '%" + title + "%'";
    	if(null != questionClassObj && questionClassObj.getClassId()!= null && questionClassObj.getClassId()!= 0)  where += " and t_question.questionClassObj=" + questionClassObj.getClassId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_question.userObj='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) where = where + " and t_question.addTime like '%" + addTime + "%'";
    	return questionMapper.queryQuestionList(where);
    }

    /*查询所有问题记录*/
    public ArrayList<Question> queryAllQuestion()  throws Exception {
        return questionMapper.queryQuestionList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String title,QuestionClass questionClassObj,UserInfo userObj,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_question.title like '%" + title + "%'";
    	if(null != questionClassObj && questionClassObj.getClassId()!= null && questionClassObj.getClassId()!= 0)  where += " and t_question.questionClassObj=" + questionClassObj.getClassId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_question.userObj='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) where = where + " and t_question.addTime like '%" + addTime + "%'";
        recordNumber = questionMapper.queryQuestionCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取问题记录*/
    public Question getQuestion(int postInfoId) throws Exception  {
        Question question = questionMapper.getQuestion(postInfoId);
        return question;
    }

    /*更新问题记录*/
    public void updateQuestion(Question question) throws Exception {
        questionMapper.updateQuestion(question);
    }

    /*删除一条问题记录*/
    public void deleteQuestion (int postInfoId) throws Exception {
        questionMapper.deleteQuestion(postInfoId);
    }

    /*删除多条问题信息*/
    public int deleteQuestions (String postInfoIds) throws Exception {
    	String _postInfoIds[] = postInfoIds.split(",");
    	for(String _postInfoId: _postInfoIds) {
    		questionMapper.deleteQuestion(Integer.parseInt(_postInfoId));
    	}
    	return _postInfoIds.length;
    }
}
