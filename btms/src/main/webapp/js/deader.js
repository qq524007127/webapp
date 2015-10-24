(function(win){
	var BSGridInited = false;
	var advocaterCardGridInted = false;
	var deader = {
			deaderGridUrl:'api/deader_grid.action',
			blessSeatGridUrl:'api/deader_enableUseBlessSeatGrid.action',
			removeUrl:'api/deader_remove.action'
	};
	deader.init = function(){
		initDeaderGird();
		initBSCombo();
	};
	
	/**
	 * 打开添加使用者窗口
	 */
	deader.showAddWindow = function(){
		$('#addWindow').dialog({
			title:'添加使用者',
			iconCls:'icon-add',
			width:650,
			height:500,
			modal:true,
			buttons:[{
				text:'添加',
				iconCls:'icon-ok',
				handler:function(){
					$('#addForm').form('submit',{
						success:function(data){
							data = $.parseJSON(data);
							$.messager.alert('',data.msg);
							if(data.success){
								$('#addWindow').dialog('close');
								$('#deaderGrid').datagrid('load');
							}
						}
					});
				}
			}],
			onOpen:function(){
				/*$('#addAdvCardCode').click(function(){
					onpenAdvocaterCardGrid(function(adv){
						if(!adv){
							return;
						}
						$('#addAdvCardCode').val(adv.cardCode);
						$('#addAdvCardId').val(adv.cardCode);
					});
				});*/
			}
		});
		$('#addForm').form('reset');
	};
	
	/**
	 * 打开选择
	 */
	function onpenAdvocaterCardGrid(callback){
		
	}
	
	/**
	 * 打开修改使用者信息窗口
	 */
	deader.showEidtWindow = function(){
		var bsGrid = $('#deaderGrid');
		var rows = bsGrid.datagrid('getChecked');
		if(rows.length != 1){
			$.messager.alert('','只能修改一条数据，请勿多选或少选！');
			return;
		}
		$('#editWindow').dialog({
			title:'添加使用者',
			iconCls:'icon-add',
			width:650,
			height:500,
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
								$('#editWindow').dialog('close');
								bsGrid.datagrid('reload');
							}
						}
					});
				}
			}]
		});
		$('#editForm').form('reset');
		$('#editForm').form('load',rows[0]);
		var ele = $('#editBlessSeatId');
		var bs = rows[0].blessSeat;
		ele.combo('setValue',bs.bsId);
		ele.combo('setText',bs.bsCode);
	};
	
	/**
	 * 删除使用者，此功能请谨慎使用，数据一旦删除就不能找回
	 */
	deader.reremoveDeader = function(){
		$.messager.confirm('警告','数据一旦删除就不能找回，是否继续',function(flag){
			if(flag){
				var deaderGrid = $('#deaderGrid');
				var rows = deaderGrid.datagrid('getChecked');
				if(rows.length < 1){
					$.messager.alert('','请选择需呀删除的数据');
					return;
				}
				var ids = '';
				for(var i = 0; i < rows.length; i ++){
					ids = rows[i].deadId + ',';
				}
				$.ajax({
					url:deader.removeUrl,
					type:'POST',
					data:{
						ids:ids
					},
					success:function(data){
						data = $.parseJSON(data);
						$.messager.alert('',data.msg);
						if(data.success){
							deaderGrid.datagrid('load');
						}
					}
				});
			}
		});
	};
	
	deader.showBlessSeatGrid = function(callback){
		$('#checkBlessSeatWindow').dialog({
			title:'选择福位',
			iconCls:'icon-save',
			width:800,
			height:450,
			modal:true,
			onOpen:function(){
				initBlessSeatGrid(callback);
			},
			onClose:function(){
				if(callback){
					callback(null);
				}
			}
		});
	};
	
	function initDeaderGird(){
		var deadGrid = $('#deaderGrid');
		$('#deaderGridSearchbox').searchbox({
			searcher:function(value,name){
				var param = {};
				if(value && name){
					param.searchName = name;
					param.searchValue = value;
				}
				deadGrid.datagrid('load',param);
			}
		});
		deadGrid.datagrid({
			url:deader.deaderGridUrl,
			columns : [ [ {
				field : 'deadId',
				title : 'ID',
				width : 10,
				checkbox : true
			},{
				field : 'deadName',
				title : '姓名',
				width : 10,
				align : 'center'
			}, {
				field : 'identNum',
				title : '身份证号',
				width : 20,
				align : 'center'
			}, {
				field : 'desSex',
				title : '性别',
				width : 10,
				align : 'center'
			}, {
				field : 'deadNational',
				title : '名族',
				width : 15,
				align : 'center'
			}, {
				field : 'deadNatPlace',
				title : '籍贯',
				width : 15,
				align : 'center'
			}, {
				field : 'advCardCode',
				title : '昄依证编号',
				width : 10,
				sortable:true,
				align : 'center'
			}, {
				field : 'deadBirthday',
				title : '生日',
				width : 10,
				align : 'center'
			}, {
				field : 'deadedDate',
				title : '往生时间',
				width : 10,
				align : 'center'
			}, {
				field : 'inputDate',
				title : '入住时间',
				width : 10,
				align : 'center'
			}, {
				field : 'contactName',
				title : '接洽居士姓名',
				width : 10,
				align : 'center'
			}, {
				field : 'contactTell',
				title : '接洽居士电话',
				width : 15,
				align : 'center'
			}, {
				field : 'contactAdress',
				title : '联系地址',
				width : 15,
				align : 'center'
			}, {
				field : 'blessSeat',
				title : '所在福位',
				width : 15,
				align : 'center',
				formatter:function(value){
					if(value){
						return value.bsCode;
					}
				}
			}, {
				field : 'deadRemark',
				title : '备注',
				width : 15,
				align : 'center',
				formatter:function(value){
					if(value){
						return '<span title="'+value+'">'+value+'</span>';
					}
				}
			}] ],
			fit : true,
			title : '使用者列表',
			pageSize:20,
			fitColumns : true,
			rownumbers : true,
			striped : true,
			pagination : true
		});
	}
	
	function initBlessSeatGrid(callback){
		
		var bsgrid = $('#blessSeatGrid');
		
		/**
		 * 初始化选择按钮点击事件
		 */
		$('#checkBSButton').linkbutton({
			iconCls:'icon-ok',
			onClick:function(){
				var rows = bsgrid.datagrid('getChecked');
				if(rows < 1){
					$.messager.alert('警告','请选择数据');
					return;
				}
				if(callback){
					callback(rows[0]);
				}
				$('#checkBlessSeatWindow').dialog('close');
			}
		});
		
		$('#blessSeatGridSearchbox').searchbox({
			width:200,
			prompt:'输入关键字搜索',
			menu:'#bsgridSearchboxMenu',
			searcher:function(value,name){
				var param = {};
				if(value && name){
					param.searchName = name;
					param.searchValue = value;
				}
				$(bsgrid).datagrid('load',param);
			}
		});
		
		$('#blessSeatGridSearchbox').searchbox('clear');

		if(BSGridInited){
			bsgrid.datagrid('load',{});
		}
		else if(!BSGridInited){
			bsgrid.datagrid({
				url:deader.blessSeatGridUrl,
				columns : [ [ {
					field : 'bsId',
					title : 'ID',
					width : 10,
					checkbox : true
				},{
					field : 'bsCode',
					title : '福位编号',
					width : 20,
					align : 'center'
				}, {
					field : 'price',
					title : '福位价格',
					width : 20,
					align : 'center'
				}, {
					field : 'managExpense',
					title : '管理费',
					width : 20,
					align : 'center'
				}/*, {
					field : 'member',
					title : '对应会员(企业)',
					width : 30,
					align : 'center'
				}*/] ],
				loadFilter:function(data){
					for(var i = 0; i < data.rows.length; i ++){
						var lev = data.rows[i].lev;
						data.rows[i].price = lev.levPrice;
					}
					return data;
				},
				toolbar:'#blessSeatGridTB',
				fit : true,
				border:false,
				fitColumns : true,
				singleSelect:true,
				rownumbers : true,
				striped : true,
				pagination : true
			});
			BSGridInited = true;
		}
	}
	
	function initBSCombo(){
		var eles = $('input[name=blessSeatId]');
		eles.each(function(index,ele){
			ele = $(ele);
			ele.combo({
				editable:false,
				width:150,
				required:true,
				panelHeight:-1,
				onShowPanel:function(){
					ele.combo('hidePanel');
					deader.showBlessSeatGrid(function(bs){
						if(bs){
							ele.combo('setValue',bs.bsId);
							ele.combo('setText',bs.bsCode);
						}
					});
				}
			});
		});
	}
	
	win.deader = deader;
	$(function(){
		deader.init();
	});
})(window);

function showAddWindow(){
	deader.showAddWindow();
}

function showEidtWindow(){
	deader.showEidtWindow();
}

function removeDeader(){
	deader.reremoveDeader();
}