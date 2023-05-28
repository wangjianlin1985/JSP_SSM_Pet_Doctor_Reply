<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/questionClass.css" />
<div id="questionClass_editDiv">
	<form id="questionClassEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">分类id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="questionClass_classId_edit" name="questionClass.classId" value="<%=request.getParameter("classId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">分类名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="questionClass_className_edit" name="questionClass.className" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">分类描述:</span>
			<span class="inputControl">
				<textarea id="questionClass_classDesc_edit" name="questionClass.classDesc" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="questionClassModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/QuestionClass/js/questionClass_modify.js"></script> 
