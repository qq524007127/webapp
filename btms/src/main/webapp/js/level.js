$(function() {
	$('#levelGrid').datagrid({
		url : 'api/level_grid.action',
		columns : [ [ {
			field : 'levId',
			title : 'ID',
			width : 10,
			checkbox : true
		},{
			field : 'levName',
			title : '级别名称',
			width: 15,
			align:'center'
		}, {
			field : 'levPrice',
			title : '价格',
			width : 20,
			sortable : true,
			align : 'center'
		}, {
			field : 'mngPrice',
			title : '管理价格',
			width : 20,
			sortable : true,
			align : 'center'
		}, {
			field : 'remark',
			title : '备注',
			width : 30,
			align : 'center'
		}] ],
		toolbar : [ {
			text : '添加',
			iconCls : 'icon-add',
			handler : function() {
				$('#addWindow').dialog({
					title : '添加级别',
					width : 450,
					height : 250,
					iconCls:'icon-add',
					modal:true,
					buttons : [ {
						text : '添加',
						iconCls : 'icon-ok',
						handler : function(){
							executAddAction();
						}
					} ]
				});
				$('#addForm').form('clear');
			}
		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler:function(){
				var rows = $('#levelGrid').datagrid('getChecked');
				if(rows.length != 1){
					$.messager.alert('','一次只能修改一行数据，请勿多选或少选');
					return;
				}
				showEditWin(rows[0]);
			}
		}],
		fit : true,
		title : '用户列表',
		fitColumns : true,
		rownumbers : true,
		pageSize:20,
		striped : true,
		pagination : true
	});
});

function executAddAction(){
	$('#addForm').form('submit',{
		success:function(data){
			data = $.parseJSON(data);
			$.messager.alert('',data.msg);
			if(data.success){
				$('#levelGrid').datagrid('load');
				$('#addWindow').dialog('close');
			}
		}
	});
}

function showEditWin(level){
	$('#editWindow').dialog({
		title:'编辑级别',
		width:450,
		height:250,
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
							$('#levelGrid').datagrid('load');
							$('#editWindow').dialog('close');
						}
					}
				});
			}
		}]
	});
	$('#editForm').form('clear');
	$('#editForm').form('load',level);
}