(function(win){
	var relation = {};
	relation.init = function(memberId){
		relation.memberId = memberId;
		initComponents();
	};
	
	/**
	 * 初始化社会关系列表
	 */
	function initComponents(){
		var url = 'api/relation_grid.action';
		if(relation.memberId){
			url += '?memberId=' + relation.memberId;
		}
		$('#relationGrid').datagrid({
			url:url,
			columns : [ [ {
				field : 'relId',
				title : 'ID',
				width : 10,
				checkbox : true
			},{
				field : 'relName',
				title : '姓名',
				width: 15,
				align:'center'
			}, {
				field : 'relation',
				title : '关系',
				width : 20,
				align : 'center'
			}, {
				field : 'relSex',
				title : '性别',
				width : 10,
				sortable : true,
				align : 'center'
			}, {
				field : 'relAddress',
				title : '地址',
				width : 30,
				align : 'center'
			}, {
				field : 'relTell',
				title : '电话',
				width : 20,
				align : 'center'
			}, {
				field : 'relAge',
				title : '年龄',
				width : 10,
				sortable : true,
				align : 'center'
			}, {
				field : 'relRemark',
				title : '备注',
				width : 30,
				align : 'center',
				formatter:function(value){
					if(value){
						return '<span title="'+value+'">'+ value +'</span>';
					}
				}
			}] ],
			toolbar : [ {
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					$('#addWindow').dialog({
						title : '添加社会关系',
						width : 600,
						height : 350,
						iconCls:'icon-add',
						modal:true,
						buttons : [ {
							text : '添加',
							iconCls : 'icon-ok',
							handler : function(){
								$('#addForm').form('submit',{
									success:function(data){
										data = $.parseJSON(data);
										$.messager.alert('',data.msg);
										if(data.success){
											$('#addWindow').dialog('close');
											$('#relationGrid').datagrid('load');
										}
									}
								});
							}
						} ]
					});
					$('#addForm').form('reset');
					$('#addForm input[name=memberId]').val(relation.memberId);
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler:function(){
					var rows = $('#relationGrid').datagrid('getChecked');
					if(rows.length != 1){
						$.messager.alert('','一次只能修改一行数据，请勿多选或少选');
						return;
					}
					showEditWin(rows[0]);
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler:function(){
					var rows = $('#relationGrid').datagrid('getChecked');
					if(rows.length < 1){
						$.messager.alert('','请选需要删除的数据');
						return;
					}
					$.messager.confirm('警告','确定要删除所选数据吗？',function(flag){
						if(flag){
							var ids = '';
							for(var i = 0; i < rows.length; i ++){
								ids += rows[i].relId + ',';
							}
							ids = ids.substring(0, ids.length - 1);
							$.ajax({
								url:'api/relation_delete.action',
								type:'POST',
								data:{
									ids:ids
								},
								success:function(data){
									data = $.parseJSON(data);
									$.messager.alert('',data.msg);
									if(data.success){
										$('#relationGrid').datagrid('load');
									}
								}
							});
						}
					});
				}
			}],
			fit : true,
			title : '用户列表',
			fitColumns : true,
			rownumbers : true,
			striped : true,
			pageSize:20,
			pagination : true
		});
	}
	
	/**
	 * 打开修改窗口
	 */
	function showEditWin(rl){
		$('#editWindow').dialog({
			title : '添加社会关系',
			width : 600,
			height : 350,
			iconCls:'icon-edit',
			modal:true,
			buttons : [ {
				text : '提交',
				iconCls : 'icon-ok',
				handler : function(){
					$('#editForm').form('submit',{
						success:function(data){
							data = $.parseJSON(data);
							$.messager.alert('',data.msg);
							if(data.success){
								$('#editWindow').dialog('close');
								$('#relationGrid').datagrid('load');
							}
						}
					});
				}
			} ]
		});
		$('#editForm').form('reset');
		$('#editForm input[name=memberId]').val(relation.memberId);
		$('#editForm').form('load',rl);
	}
	
	win.relation = relation;
})(window);