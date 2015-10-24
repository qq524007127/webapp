$(function() {
	$('#userGrid').datagrid({
		url : 'api/user_grid.action',
		columns : [ [ {
			field : 'userId',
			title : 'ID',
			width : 10,
			checkbox : true
		},{
			field : 'password',
			title : 'pass',
			hidden: true
		}, {
			field : 'userCode',
			title : '登陆账号',
			width : 20,
			align : 'center'
		}, {
			field : 'userName',
			title : '用户姓名',
			width : 30,
			align : 'center'
		}, {
			field : 'mobile',
			title : '手机号码',
			width : 20,
			align : 'center'
		}, {
			field : 'email',
			title : '电子邮箱',
			width : 20,
			align : 'center'
		}, {
			field : 'permit',
			title : '是否有效',
			width : 10,
			formatter:function(value,row,index){
				if(value){
					return '有效';
				}
				return '<span style="color:red">无效</span>';
			},
			align : 'center'
		}, {
			field : 'roleSet',
			title : '角色',
			width : 50,
			formatter:function(value, row ,index){
				if(value && value.length > 0){
					var retVal = '';
					for(var i = 0; i < value.length; i ++){
						retVal += value[i].roleName + '、';
					}
					return retVal;
				}
				return value;
			},
			align : 'center'
		}, {
			field : 'remark',
			title : '备注',
			width : 30,
			align : 'center',
			formatter:function(value){
				if(value){
					return '<span title="'+value+'">'+value+'</span>';
				}
			}
		}] ],
		toolbar : '#toolbarPanel',
		fit : true,
		title : '用户列表',
		fitColumns : true,
		rownumbers : true,
		striped : true,
		pageSize:20,
		pagination : true
	});
	
	$('#searchBox').searchbox({
		width:300,
		prompt:'输入关键字搜索',
		menu:'#searchboxMenu',
		searcher:function(value,name){
			var param = {};
			if($.trim(value) && $.trim(name)){
				param.searchName= name;
				param.searchValue = value;
			}
			$('#userGrid').datagrid('load',param);
		}
	});
});

function addUser(){
	$('#addWindow').dialog({
		title : '添加用户',
		width : 500,
		height : 300,
		iconCls:'icon-add',
		modal:true,
		buttons : [ {
			text : '添加',
			iconCls : 'icon-ok',
			handler : function(){
				executAddUserAction();
			}
		} ]
	});
	$('#addForm').form('reset');
}

function editUser(){
	var rows = $('#userGrid').datagrid('getChecked');
	if(rows.length != 1){
		$.messager.alert('','一次只能修改一行数据，请勿多选或少选');
		return;
	}
	showEditWin(rows[0]);
}

function resetPassword(){
	var rows = $('#userGrid').datagrid('getChecked');
	if(rows.length < 1){
		$.messager.alert('','请勾选需要操纵的数据');
		return;
	}
	var userIds = '';
	for(var i = 0; i < rows.length; i ++){
		userIds += rows[i].userId + ',';
	}
	userIds = $.trim(userIds).substring(0, userIds.length - 1);
	$.ajax({
		type : "post",
		url : "api/user_resetPassword.action",
		dataType : "text",
		data:{'userIds':userIds},
		success: function(data){
			data = $.parseJSON(data);
			$.messager.alert('',data.msg);
			if(data.success){
				$('#userGrid').datagrid('load');
			}
		}
	});
}

function executAddUserAction(){
	$('#addForm').form('submit',{
		success:function(data){
			data = $.parseJSON(data);
			$.messager.alert('',data.msg);
			if(data.success){
				$('#userGrid').datagrid('load');
				$('#addWindow').dialog('close');
			}
		}
	});
}

function showEditWin(user){
	$('#editWindow').dialog({
		title:'编辑用户',
		width:500,
		height:300,
		iconCls:'icon-edit',
		modal:true,
		buttons:[{
			text:'提交',
			iconCls:'icon-ok',
			handler:function(){
				$('#editForm').form('submit',{
					success:function(data){
						data = $.parseJSON(data);
						$.messager.alert('',data.msg);
						if(data.success){
							$('#userGrid').datagrid('load');
							$('#editWindow').dialog('close');
						}
					}
				});
			}
		}]
	});
	$('#editForm').form('clear');
	$('#editForm').form('load',user);
	$('#editPermit').prop('checked',user.permit);
	var roleIds = $('#editForm input[name="roleIds"]');
	var roles = user.roleSet;
	for(var j = 0; j < roles.length; j ++){
		for(var i=0; i< roleIds.length; i ++){
			if(roles[j].roleId == $(roleIds[i]).val()){
				$(roleIds[i]).prop('checked',true);
			}
		}
	}
}