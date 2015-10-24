$(function() {
	$('#salerGrid').datagrid({
		url : 'api/saler_grid.action',
		columns : [ [ {
			field : 'salerId',
			title : 'ID',
			width : 10,
			checkbox : true
		},{
			field : 'salerName',
			title : '姓名',
			width: 15,
			align:'center'
		}, {
			field : 'salerPhone',
			title : '联系电话',
			width : 20,
			sortable : true,
			align : 'center'
		}, {
			field : 'permit',
			title : '状态',
			width : 20,
			sortable : true,
			align : 'center',
			formatter:function(value){
				if(value){
					return '已启用';
				}
				return '<span style="color:red;">已禁用</span>';
			}
		}] ],
		toolbar : [ {
			text : '添加',
			iconCls : 'icon-add',
			handler : function() {
				$('#addWindow').dialog({
					title : '添加销售员工',
					width : 350,
					height : 260,
					iconCls:'icon-add',
					modal:true,
					buttons : [ {
						text : '添加',
						iconCls : 'icon-ok',
						handler : function(){
							$('#addForm').form('submit');
						}
					} ]
				});
				$('#addForm').form('reset');
			}
		},'-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler:function(){
				var rows = $('#salerGrid').datagrid('getChecked');
				if(rows.length != 1){
					$.messager.alert('','请选择需要修改的数据');
					return;
				}
				showEditWindow(rows[0]);
			}
		}],
		fit : true,
		title : '销售人员列表',
		fitColumns : true,
		pageSize:20,
		rownumbers : true,
		striped : true,
		singleSelect:true,
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
				$('#salerGrid').datagrid('load');
				$('#addWindow').dialog('close');
			}
		}
	});
});

function showEditWindow(saler){
	$('#editWindow').dialog({
		title:'编辑销售员信息',
		width:350,
		height:260,
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
							$('#salerGrid').datagrid('load');
							$('#editWindow').dialog('close');
						}
					}
				});
			}
		}]
	});
	$('#editForm').form('clear');
	$('#editForm').form('load',saler);
}