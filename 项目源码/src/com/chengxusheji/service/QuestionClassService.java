package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.QuestionClass;

import com.chengxusheji.mapper.QuestionClassMapper;
@Service
public class QuestionClassService {

	@Resource QuestionClassMapper questionClassMapper;
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

    /*添加问题分类记录*/
    public void addQuestionClass(QuestionClass questionClass) throws Exception {
    	questionClassMapper.addQuestionClass(questionClass);
    }

    /*按照查询条件分页查询问题分类记录*/
    public ArrayList<QuestionClass> queryQuestionClass(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return questionClassMapper.queryQuestionClass(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<QuestionClass> queryQuestionClass() throws Exception  { 
     	String where = "where 1=1";
    	return questionClassMapper.queryQuestionClassList(where);
    }

    /*查询所有问题分类记录*/
    public ArrayList<QuestionClass> queryAllQuestionClass()  throws Exception {
        return questionClassMapper.queryQuestionClassList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = questionClassMapper.queryQuestionClassCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取问题分类记录*/
    public QuestionClass getQuestionClass(int classId) throws Exception  {
        QuestionClass questionClass = questionClassMapper.getQuestionClass(classId);
        return questionClass;
    }

    /*更新问题分类记录*/
    public void updateQuestionClass(QuestionClass questionClass) throws Exception {
        questionClassMapper.updateQuestionClass(questionClass);
    }

    /*删除一条问题分类记录*/
    public void deleteQuestionClass (int classId) throws Exception {
        questionClassMapper.deleteQuestionClass(classId);
    }

    /*删除多条问题分类信息*/
    public int deleteQuestionClasss (String classIds) throws Exception {
    	String _classIds[] = classIds.split(",");
    	for(String _classId: _classIds) {
    		questionClassMapper.deleteQuestionClass(Integer.parseInt(_classId));
    	}
    	return _classIds.length;
    }
}
