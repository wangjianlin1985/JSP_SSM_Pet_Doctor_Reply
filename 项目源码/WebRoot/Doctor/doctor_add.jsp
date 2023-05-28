<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/doctor.css" />
<div id="doctorAddDiv">
	<form id="doctorAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">医生工号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="doctor_doctorNo" name="doctor.doctorNo" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">登录密码:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="doctor_password" name="doctor.password" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="doctor_name" name="doctor.name" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">性别:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="doctor_gender" name="doctor.gender" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">出生日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="doctor_birthDate" name="doctor.birthDate" />

			</span>

		</div>
		<div>
			<span class="label">医生照片:</span>
			<span class="inputControl">
				<input id="doctorPhotoFile" name="doctorPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">职称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="doctor_zc" name="doctor.zc" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="doctor_telephone" name="doctor.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">工作经验:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="doctor_workYear" name="doctor.workYear" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">医生介绍:</span>
			<span class="inputControl">
				<script name="doctor.doctorDesc" id="doctor_doctorDesc" type="text/plain"   style="width:750px;height:500px;"></script>
			</span>

		</div>
		<div>
			<span class="label">入职时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="doctor_regTime" name="doctor.regTime" />

			</span>

		</div>
		<div class="operation">
			<a id="doctorAddButton" class="easyui-linkbutton">添加</a>
			<a id="doctorClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Doctor/js/doctor_add.js"></script> 
