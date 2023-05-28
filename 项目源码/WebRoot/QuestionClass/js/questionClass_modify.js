$(function () {
	$.ajax({
		url : "QuestionClass/" + $("#questionClass_classId_edit").val() + "/update",
		type : "get",
		data : {
			//classId : $("#questionClass_classId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (questionClass, response, status) {
			$.messager.progress("close");
			if (questionClass) { 
				$("#questionClass_classId_edit").val(questionClass.classId);
				$("#questionClass_classId_edit").validatebox({
					required : true,
					missingMessage : "请输入分类id",
					editable: false
				});
				$("#questionClass_className_edit").val(questionClass.className);
				$("#questionClass_className_edit").validatebox({
					required : true,
					missingMessage : "请输入分类名称",
				});
				$("#questionClass_classDesc_edit").val(questionClass.classDesc);
				$("#questionClass_classDesc_edit").validatebox({
					required : true,
					missingMessage : "请输入分类描述",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#questionClassModifyButton").click(function(){ 
		if ($("#questionClassEditForm").form("validate")) {
			$("#questionClassEditForm").form({
			    url:"QuestionClass/" +  $("#questionClass_classId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#questionClassEditForm").form("validate"))  {
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#questionClassEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
