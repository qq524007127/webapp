(function(win, $) {
	
	function init() {
		initAvocaterCardGrid();
	}
	
	/**
	 * 初始化昄依证列表
	 */
	function initAvocaterCardGrid() {
		var datagrid = $('#advocaterCardGrid');
		datagrid.datagrid({
			url:'api/advocaterCard_grid.action',
			columns : [ [ {
				field : 'cardId',
				title : 'ID',
				width : 10,
				checkbox : true
			},{
				field : 'cardCode',
				title : '昄依证号',
				width: 15,
				sortable : true,
				align:'center'
			}, {
				field : 'advName',
				title : '办证人（持有人）姓名',
				width : 20,
				align : 'center'
			}, {
				field : 'advBirthday',
				title : '持有人出身日期',
				width : 20,
				align : 'center',
				sortable:true
			}, {
				field : 'createCardDate',
				title : '办证日期',
				width : 20,
				align : 'center',
				sortable:true
			}/*, {
				field : 'permit',
				title : '是否有效',
				width : 10,
				align : 'center',
				sortable : true,
				formatter:function(value){
					if(value){
						return '有效';
					}
					return '<span style="color:red;">无效</span>';
				}
			}*/, {
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
			toolbar:'#toolbarPanel',
			fit : true,
			title : '昄依证列表',
			fitColumns : true,
			pageSize:20,
			singleSelect:true,
			rownumbers : true,
			striped : true,
			pagination : true
		});
		initToolbarPanel();
	}
	
	/**
	 * 初始化列表工具栏
	 */
	function initToolbarPanel(){
		/**
		 * 初始化办理按钮
		 */
		$('#addCardBtn').click(function(){
			$('#addWindow').dialog({
				title:'办理昄依证',
				iconCls:'icon-save',
				width:400,
				heigth:400,
				modal:true,
				buttons:[{
					text:'办理',
					iconCls:'icon-ok',
					handler:function(){
						$('#addForm').form('submit',{
							success:function(data){
								data = $.parseJSON(data);
								$.messager.alert('',data.msg);
								if(data.success){
									$('#advocaterCardGrid').datagrid('load');
									$('#addWindow').dialog('close');
								}
							}
						});
					}
				}]
			});
			$('#addForm').form('reset');
		});
		
		/**
		 * 初始化编辑信息按钮
		 */
		$('#editCardBtn').click(function(){
			var rows = $('#advocaterCardGrid').datagrid('getChecked');
			if(rows.length < 1){
				$.messager.alert('','请选择需要操作的数据');
				return;
			}
			$('#editWindow').dialog({
				title:'修改昄依证信息',
				iconCls:'icon-edit',
				width:400,
				heigth:400,
				modal:true,
				buttons:[{
					text:'修改',
					iconCls:'icon-ok',
					handler:function(){
						$('#editForm').form('submit',{
							success:function(data){
								data = $.parseJSON(data);
								$.messager.alert('',data.msg);
								if(data.success){
									$('#advocaterCardGrid').datagrid('load');
									$('#editWindow').dialog('close');
								}
							}
						});
					}
				}]
			});
			$('#editForm').form('reset');
			$('#editForm').form('load',rows[0]);
		});
		
		$('#mainSearchbox').searchbox({
			promt:'输入关键字搜索',
			menu:'#mainSearchboxMenu',
			width:250,
			searcher:function(value,name){
				var param = {};
				if(value && name){
					param.searchName = name;
					param.searchValue = value;
				}
				$('#advocaterCardGrid').datagrid('load',param);
			}
		});
	}
	
	$(function() {
		init();
	});
})(window, $);