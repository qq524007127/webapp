$(function() {
	$('#areaGrid').datagrid({
		url : 'api/area_grid.action',
		columns : [ [ {
			field : 'areaId',
			title : 'ID',
			width : 10,
			checkbox : true
		},{
			field : 'areaName',
			title : '区域名称',
			width: 15,
			align:'center'
		}, {
			field : 'areaRow',
			title : '行数',
			width : 20,
			sortable : true,
			align : 'center'
		}, {
			field : 'areaColumn',
			title : '列数',
			width : 20,
			sortable : true,
			align : 'center'
		}, {
			field : 'coords',
			title : '区域坐标',
			width : 30,
			align : 'center'
		}, {
			field : 'shelfRow',
			title : '福位架行数',
			width : 20,
			sortable : true,
			align : 'center'
		}, {
			field : 'shelfColumn',
			title : '福位架列数',
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
					title : '添加区域',
					width : 500,
					height : 300,
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
		},'-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler:function(){
				var rows = $('#areaGrid').datagrid('getChecked');
				if(rows.length != 1){
					$.messager.alert('','一次只能修改一行数据，请勿多选或少选');
					return;
				}
				showEditWin(rows[0]);
			}
		}],
		fit : true,
		title : '区域列表',
		fitColumns : true,
		pageSize:20,
		rownumbers : true,
		striped : true,
		pagination : true
	});
	
	/**
	 * 初始化添加表单
	 */
	$('#addForm').form({
		onSubmit:function(){
			$.messager.progress({
				text:'正在执行，请稍等...'
			});
			return true;
		},
		success:function(data){
			$.messager.progress('close');
			data = $.parseJSON(data);
			$.messager.alert('',data.msg);
			if(data.success){
				$('#areaGrid').datagrid('load');
				$('#addWindow').dialog('close');
			}
		}
	});
});

function executAddAction(){
	$('#addForm').form('submit');
}

function showInitWindow(){
	$('#initWindow').dialog({
		title:'初始化区域',
		width:450,
		height:350,
		iconCls:'icon-large-smartart',
		modal:true,
		buttons:[{
			text:'提交',
			iconCls:'icon-ok',
			handler:function(){
				$.messager.progress({
					text:'正在初始化中...'
				});
				$('#initForm').form('submit',{
					success:function(data){
						$.messager.progress('close');
						data = $.parseJSON(data);
						$.messager.alert('',data.msg);
						if(data.success){
							$('#initWindow').dialog('close');
							$('#areaGrid').datagrid('load');
						}
					}
				});
			}
		}]
	});
	$('#initForm').form('clear');
}

function showEditWin(area){
	$('#editWindow').dialog({
		title:'编辑区域',
		width:550,
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
							$('#areaGrid').datagrid('load');
							$('#editWindow').dialog('close');
						}
					}
				});
			}
		}]
	});
	$('#editForm').form('clear');
	$('#editForm').form('load',area);
}