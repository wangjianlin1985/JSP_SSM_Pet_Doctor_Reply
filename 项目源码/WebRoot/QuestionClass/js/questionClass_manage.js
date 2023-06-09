var questionClass_manage_tool = null; 
$(function () { 
	initQuestionClassManageTool(); //建立QuestionClass管理对象
	questionClass_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#questionClass_manage").datagrid({
		url : 'QuestionClass/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "classId",
		sortOrder : "desc",
		toolbar : "#questionClass_manage_tool",
		columns : [[
			{
				field : "classId",
				title : "分类id",
				width : 70,
			},
			{
				field : "className",
				title : "分类名称",
				width : 140,
			},
			{
				field : "classDesc",
				title : "分类描述",
				width : 140,
			},
		]],
	});

	$("#questionClassEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#questionClassEditForm").form("validate")) {
					//验证表单 
					if(!$("#questionClassEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#questionClassEditForm").form({
						    url:"QuestionClass/" + $("#questionClass_classId_edit").val() + "/update",
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#questionClassEditDiv").dialog("close");
			                        questionClass_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#questionClassEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#questionClassEditDiv").dialog("close");
				$("#questionClassEditForm").form("reset"); 
			},
		}],
	});
});

function initQuestionClassManageTool() {
	questionClass_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#questionClass_manage").datagrid("reload");
		},
		redo : function () {
			$("#questionClass_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#questionClass_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#questionClassQueryForm").form({
			    url:"QuestionClass/OutToExcel",
			});
			//提交表单
			$("#questionClassQueryForm").submit();
		},
		remove : function () {
			var rows = $("#questionClass_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var classIds = [];
						for (var i = 0; i < rows.length; i ++) {
							classIds.push(rows[i].classId);
						}
						$.ajax({
							type : "POST",
							url : "QuestionClass/deletes",
							data : {
								classIds : classIds.join(","),
							},
							beforeSend : function () {
								$("#questionClass_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#questionClass_manage").datagrid("loaded");
									$("#questionClass_manage").datagrid("load");
									$("#questionClass_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#questionClass_manage").datagrid("loaded");
									$("#questionClass_manage").datagrid("load");
									$("#questionClass_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#questionClass_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "QuestionClass/" + rows[0].classId +  "/update",
					type : "get",
					data : {
						//classId : rows[0].classId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (questionClass, response, status) {
						$.messager.progress("close");
						if (questionClass) { 
							$("#questionClassEditDiv").dialog("open");
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
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
