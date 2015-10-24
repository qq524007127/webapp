$(function() {
	initComponent();
});

function initComponent(){
	$('#shelfGrid').datagrid({
		url : 'api/shelf_grid.action',
		columns : [ [ {
			field : 'shelfId',
			title : 'ID',
			width : 10,
			checkbox : true
		},{
			field : 'shelfCode',
			title : '编号',
			align: 'center',
			width: 15,
			sortable:true
		}, {
			field : 'shelfArea',
			title : '所在区域',
			width : 15,
			align : 'center'
		}, {
			field : 'shelfRow',
			title : '总行数',
			width : 15,
			align : 'center'
		}, {
			field : 'shelfColumn',
			title : '总列数',
			width : 15,
			align : 'center'
		}, {
			field : 'postionRow',
			title : '所在区域行',
			width : 15,
			align : 'center',
			sortable:true
		}, {
			field : 'postionColumn',
			title : '所在区域列',
			width : 15,
			align : 'center',
			sortable:true
		}, {
			field : 'permit',
			title : '是否有效',
			width : 50,
			align : 'center',
			sortable:true,
			formatter:function(value){
				if(value){
					return '有效';
				}
				return '<span style="color:red;">无效</span>';
			}
		}, {
			field : 'remark',
			title : '备注',
			width : 50,
			align : 'center'
		}] ],
		toolbar : [/*{
			text : '修改',
			iconCls : 'icon-edit',
			handler:function(){
				var rows = $('#shelfGrid').datagrid('getChecked');
				if(rows.length != 1){
					$.messager.alert('','一次只能修改一行数据，请勿多选或少选');
					return;
				}
				showEditWin(rows[0]);
			}
		},'-',{
			text:'启用',
			iconCls:'icon-ok',
			handler:function(){
				var rows = $('#shelfGrid').datagrid('getChecked');
				if(rows.length < 1){
					$.messager.alert('','请选择需要禁用的数据');
					return;
				}
				var Ids = '';
				for(var i=0;i<rows.length;i++){
					Ids += rows[i].shelfId + ',';
				}
				Ids = Ids.substring(0, Ids.length-1);
				$.messager.confirm('警告','启用福位架之后,在此福位架上的全部福位也将启用，是否继续？(此功能请谨慎使用)',function(flag){
					if(flag){
						$.ajax({
							url:'api/shelf_enable.action',
							type:'POST',
							data:{shelfIds:Ids},
							success:function(data){
								data = $.parseJSON(data);
								$.messager.alert('提示',data.msg);
								if(data.success){
									$('#shelfGrid').datagrid('reload');
								}
							}
						});
					}
				});
			}
		},'-',{
			text:'禁用',
			iconCls:'icon-no',
			handler:function(){
				var rows = $('#shelfGrid').datagrid('getChecked');
				if(rows.length < 1){
					$.messager.alert('','请选择需要禁用的数据');
					return;
				}
				var Ids = '';
				for(var i=0;i<rows.length;i++){
					Ids += rows[i].shelfId + ',';
				}
				Ids = Ids.substring(0, Ids.length-1);
				$.messager.confirm('警告','禁用福位架之后,在此福位架上的全部福位也将不可用，是否继续？(此功能请谨慎使用)',function(flag){
					if(flag){
						$.ajax({
							url:'api/shelf_disable.action',
							type:'POST',
							data:{shelfIds:Ids},
							success:function(data){
								data = $.parseJSON(data);
								$.messager.alert('',data.msg);
								if(data.success){
									$('#shelfGrid').datagrid('reload');
								}
							}
						});
					}
				});
			}
		} */],
		fit : true,
		title : '福位架列表',
		fitColumns : true,
		rownumbers : true,
		striped : true,
		pagination : true,
		pageSize:20,
		loadFilter:function(data){
			var rows = [];
			for(var i = 0; i < data.rows.length; i ++){
				var row = data.rows[i];
				row.shelfArea = row.shelfArea.areaName;
				rows.push(row);
			}
			data = {'total':data.total,'rows':rows};
			return data;
		}
	});
	
	$('#editShelfArea').combobox({
		url:'api/getAreas.action',
		valueField:'areaId',
		textField:'areaName',
		width:100,
		panelHeight:100,
		editable:false
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
		title:'编辑信息',
		width:500,
		height:350,
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
}