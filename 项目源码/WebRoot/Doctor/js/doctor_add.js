$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('doctor_doctorDesc');
	var doctor_doctorDesc_editor = UE.getEditor('doctor_doctorDesc'); //医生介绍编辑框
	$("#doctor_doctorNo").validatebox({
		required : true, 
		missingMessage : '请输入医生工号',
	});

	$("#doctor_password").validatebox({
		required : true, 
		missingMessage : '请输入登录密码',
	});

	$("#doctor_name").validatebox({
		required : true, 
		missingMessage : '请输入姓名',
	});

	$("#doctor_gender").validatebox({
		required : true, 
		missingMessage : '请输入性别',
	});

	$("#doctor_birthDate").datebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#doctor_zc").validatebox({
		required : true, 
		missingMessage : '请输入职称',
	});

	$("#doctor_telephone").validatebox({
		required : true, 
		missingMessage : '请输入联系电话',
	});

	$("#doctor_workYear").validatebox({
		required : true, 
		missingMessage : '请输入工作经验',
	});

	$("#doctor_regTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	//单击添加按钮
	$("#doctorAddButton").click(function () {
		//验证表单 
		if(!$("#doctorAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#doctorAddForm").form({
			    url:"Doctor/add",
			    onSubmit: function(){
					if($("#doctorAddForm").form("validate"))  { 
	                	$.messager.progress({
							text : "正在提交数据中...",
						}); 
	                	return true;
	                } else {
	                    return false;
	                }
			    },
			    success:function(data){
			    	$.messager.progress("close");
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#doctorAddForm").form("clear");
                        doctor_doctorDesc_editor.setContent("");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#doctorAddForm").submit();
		}
	});

	//单击清空按钮
	$("#doctorClearButton").click(function () { 
		$("#doctorAddForm").form("clear"); 
	});
});
