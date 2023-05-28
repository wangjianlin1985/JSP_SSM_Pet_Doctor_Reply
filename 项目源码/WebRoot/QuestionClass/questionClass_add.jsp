<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/questionClass.css" />
<div id="questionClassAddDiv">
	<form id="questionClassAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">分类名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="questionClass_className" name="questionClass.className" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">分类描述:</span>
			<span class="inputControl">
				<textarea id="questionClass_classDesc" name="questionClass.classDesc" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="questionClassAddButton" class="easyui-linkbutton">添加</a>
			<a id="questionClassClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/QuestionClass/js/questionClass_add.js"></script> 
