$(function() {
	initTabletGrid();
	initSearchbox();
});

/**
 * 初始化福位列表
 */
function initTabletGrid(){
	$('#tabletGrid').datagrid({
		url : 'api/tablet_grid.action',
		columns:[[{
			field:'tabletId',
			width:10,
			checkbox:true
		},{
			field : 'tabletName',
			title : '牌位名称',
			align: 'center',
			width: 30
		}, {
			field : 'tabletPrice',
			title : '价格',
			width : 10,
			align : 'center',
			sortable:true
			
		}, {
			field : 'editable',
			title : '年限是否可编辑',
			width : 20,
			align : 'center',
			sortable:true,
			formatter:function(value){
				if(value){
					return '可编辑';
				}
				return '不可编辑';
			}
		}, {
			field : 'permit',
			title : '是否有效',
			width : 20,
			align : 'center',
			sortable:true,
			formatter:function(value){
				if(value){
					return '有效';
				}
				return '<span style="color:red;">无效</span>';
			}
		}, {
			field : 'tabletRemark',
			title : '备注',
			width : 30,
			align : 'center'
		}]],
		toolbar:'#tabletGridTB',
		fit : true,
		title : '牌位列表',
		fitColumns : true,
		rownumbers : true,
		striped : true,
		pageSize:20,
		pagination : true
	});
}

function initSearchbox(){
	$('#searchbox').searchbox({
		width:300,
		prompt:'输入关键字搜索',
		menu:'#searchboxMenu',
		searcher:function(value,name){
			var param = {};
			if($.trim(value) && $.trim(name)){
				param.searchValue = value;
				param.searchName = name;
			}
			$('#tabletGrid').datagrid('load',param);
		}
	});
}

/**
 * 添加牌位
 */
function doAddTablet(){
	$('#addWindow').dialog({
		title:'添加牌位',
		width:450,
		height:300,
		modal:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				$('#addForm').form('submit',{
					success:function(data){
						data = $.parseJSON(data);
						$.messager.alert('',data.msg);
						if(data.success){
							$('#addWindow').dialog('close');
							$('#tabletGrid').datagrid('load');
						}
					}
				});
			}
		}]
	});
	$('#addForm').form('clear');
	$('#addForm input[name=permit]').prop('checked',true);
	$('#addForm input[name=editable]').prop('checked',true);
}

/**
 * 修改牌位
 */
function doEditTablet(){
	var rows = $('#tabletGrid').datagrid('getChecked');
	if(rows.length != 1){
		$.messager.alert('','只能对一条数据进行修改，请勿多选或少选！');
		return;
	}
	$('#editWindow').dialog({
		title:'修改牌位',
		width:450,
		height:300,
		modal:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				$('#editForm').form('submit',{
					success:function(data){
						data = $.parseJSON(data);
						$.messager.alert('',data.msg);
						if(data.success){
							$('#editWindow').dialog('close');
							$('#tabletGrid').datagrid('load');
						}
					}
				});
			}
		}]
	});
	$('#editForm').form('clear');
	$('#editForm').form('load',rows[0]);
	$('#editForm input[name=permit]').prop('checked',rows[0].permit);
	$('#addForm input[name=editable]').prop('checked',rows[0].editable);
}