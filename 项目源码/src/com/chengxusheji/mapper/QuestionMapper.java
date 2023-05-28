package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Question;

public interface QuestionMapper {
	/*添加问题信息*/
	public void addQuestion(Question question) throws Exception;

	/*按照查询条件分页查询问题记录*/
	public ArrayList<Question> queryQuestion(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有问题记录*/
	public ArrayList<Question> queryQuestionList(@Param("where") String where) throws Exception;

	/*按照查询条件的问题记录数*/
	public int queryQuestionCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条问题记录*/
	public Question getQuestion(int postInfoId) throws Exception;

	/*更新问题记录*/
	public void updateQuestion(Question question) throws Exception;

	/*删除问题记录*/
	public void deleteQuestion(int postInfoId) throws Exception;

}
