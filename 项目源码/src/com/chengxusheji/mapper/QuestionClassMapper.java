package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.QuestionClass;

public interface QuestionClassMapper {
	/*添加问题分类信息*/
	public void addQuestionClass(QuestionClass questionClass) throws Exception;

	/*按照查询条件分页查询问题分类记录*/
	public ArrayList<QuestionClass> queryQuestionClass(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有问题分类记录*/
	public ArrayList<QuestionClass> queryQuestionClassList(@Param("where") String where) throws Exception;

	/*按照查询条件的问题分类记录数*/
	public int queryQuestionClassCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条问题分类记录*/
	public QuestionClass getQuestionClass(int classId) throws Exception;

	/*更新问题分类记录*/
	public void updateQuestionClass(QuestionClass questionClass) throws Exception;

	/*删除问题分类记录*/
	public void deleteQuestionClass(int classId) throws Exception;

}
