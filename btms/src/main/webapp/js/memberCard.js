(function(win){
	
	var memberGridInited = false;	//会员列表是否已初始化
	var enterpriseGridInited = false;	//企业列表是否已初始化
	
	var memberCard = {};
	memberCard.init = function(){
		memberCard.memberCardGridUrl = 'api/memberCard_grid.action';
		initMemberCardGrid();
		initComponents();
	};
	function initMemberCardGrid(){
		$('#memberCardGrid').datagrid({
			url:memberCard.memberCardGridUrl,
			columns : [ [ {
				field : 'cardId',
				title : 'ID',
				width : 10,
				checkbox : true
			},{
				field : 'cardCode',
				title : '会员证号',
				width: 15,
				sortable : true,
				align:'center'
			}, {
				field : 'createDate',
				title : '办理时间',
				width : 20,
				sortable : true,
				align : 'center'
			}, {
				field : 'mem',
				title : '对应会员',
				width : 20,
				align : 'center',
				formatter:function(value){
					if(value){
						return value.memberName;
					}
					return '/';
				}
			}, {
				field : 'enterprise',
				title : '对应企业',
				width : 20,
				align : 'center',
				formatter:function(value){
					if(value){
						return value.enterName;
					}
					return '/';
				}
			}, {
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
			fit : true,
			title : '会员证列表',
			fitColumns : true,
			singleSelect:true,
			rownumbers : true,
			striped : true,
			pageSize:20,
			pagination : true
		});
	}
	
	function initComponents(){
		/**
		 * 初始化办理会员证按钮
		 */
		$('#addMemberCardBtn').click(function(){
			$('#addMemberCardWindow').dialog({
				width:450,
				height:230,
				title:'办理会员证',
				iconCls:'icon-save',
				modal:true,
				onOpen:function(){
					$('#addFrom').form('reset');
					$('#meberOrEnterInput').click(function(){
						
						var idInput = $('#memberOrEnterId');
						
						initSelectWindow(function(row){
							if(!row){
								return;
							}
							if(row.memberId){
								$(idInput).attr('name','mem.memberId').val(row.memberId);
								$('#meberOrEnterInput').val(row.memberName);
							}
							else if(row.enterId){
								$(idInput).attr('name','enterprise.enterId').val(row.enterId);
								$('#meberOrEnterInput').val(row.enterName);
							}
						});
					});
				},
				buttons:[{
					text:'办理',
					iconCls:'icon-ok',
					handler:function(){
						$('#addFrom').form('submit',{
							success:function(data){
								data = $.parseJSON(data);
								$.messager.alert('',data.msg);
								if(data.success){
									$('#memberCardGrid').datagrid('load');
									$('#addMemberCardWindow').dialog('close');
								}
							}
						});
					}
				}]
			});
		});
		
		/**
		 * 初始化会员证补办按钮
		 */
		$('#reAddMemberCardBtn').click(function(){
			var rows = $('#memberCardGrid').datagrid('getChecked');
			if(rows.length < 1){
				$.messager.alert('','请选择数据');
				return;
			}
			$.messager.confirm('警告','补办之后以前的会员证将不可用，是否继续？',function(flag){
				if(flag){
					var card = rows[0];
					var member=null,enterprise=null;
					if(card.mem){
						member = card.mem.memberId;
					}
					else if(card.enterprise){
						enterprise = card.enterprise.enterId;
					}
					$.ajax({
						url:'api/memberCard_reHandle.action',
						type:'POST',
						data:{'cardId':card.cardId,'mem.memberId':member,'enterprise.enterId':enterprise},
						success:function(data){
							data = $.parseJSON(data);
							$.messager.alert('',data.msg);
							if(data.success){
								$('#memberCardGrid').datagrid('load');
							}
						}
					});
				}
			});
		});
		
		/**
		 * 初始化会员证挂失按钮
		 */
		$('#disableMemberCard').click(function(){
			var rows = $('#memberCardGrid').datagrid('getChecked');
			if(rows.length < 1){
				$.messager.alert('','请选择数据');
				return;
			}
			$.messager.confirm('警告','挂失后会员证将不可用，是否继续？',function(flag){
				if(flag){
					$.ajax({
						url:'api/memberCard_disable.action',
						type:'POST',
						data:{
							cardId:rows[0].cardId
						},
						success:function(data){
							data = $.parseJSON(data);
							$.messager.alert('',data.msg);
							if(data.success){
								 $('#memberCardGrid').datagrid('reload');
							}
						}
					});
				}
			});
		});
		
		/**
		 * 初始化会员证搜索框
		 */
		$('#memberCardSearchbox').searchbox({
			prompt:'输入关键字搜索',
			menu:'#memberCardSearchboxMenu',
			width:250,
			searcher:function(value,name){
				var param = {};
				if(value && name){
					param.searchValue = value;
					param.searchName = name;
				}
				$('#memberCardGrid').datagrid('load',param);
			}
		});
	}
	
	function initSelectWindow(callback){
		$('#selectkWindow').dialog({
			width:800,
			height:500,
			title:'会员（企业）列表',
			modal:true,
			onClose:function(){
				if(callback){
					callback(null);
				}
			}
		});
		
		/**
		 * 初始化个人会员里列表
		 */
		if(memberGridInited){
			$('#memberSearchbox').searchbox('clear');
			$('#memberGrid').datagrid('load',{});
		}
		else{
			
			$('#memberSearchbox').searchbox({
				prompt:'输入关键字搜索',
				width:250,
				menu:'#memberSearchboxMenu',
				searcher:function(value,name){
					var param = {};
					if(value && name){
						param.searchName = name;
						param.searchValue = value;
					}
					$('#memberGrid').datagrid('load',param);
				}
			});
			
			$('#checkMemberBtn').click(function(){
				var rows = $('#memberGrid').datagrid('getChecked');
				if(rows.length < 1){
					$.messager.alert('','请选择数据');
					return;
				}
				if(callback){
					callback(rows[0]);
				}
				$('#selectkWindow').dialog('close');
			});
			$('#memberGrid').datagrid({
				url : 'api/memberCard_memberGrid.action',
				columns:[[{
					field:'memberId',
					width:10,
					checkbox:true
				},{
					field : 'memberName',
					title : '会员姓名',
					align: 'center',
					sortable:true,
					width: 10
				}, {
					field : 'memberIdentNum',
					title : '身份证号',
					width : 20,
					align : 'center',
					sortable:true
				}, {
					field : 'memberSex',
					title : '性别',
					width : 10,
					align : 'center',
					sortable:true
				}, {
					field : 'memberTell',
					title : '联系电话',
					width : 15,
					align : 'center'
				}, {
					field : 'memberRemark',
					title : '备注',
					width : 30,
					align : 'center',
					formatter:function(value){
						return '<span title="'+value+'">'+value+'</span>';
					}
				}]],
				toolbar:'#memberGridTB',
				fit : true,
				border:false,
				singleSelect:true,
				fitColumns : true,
				rownumbers : true,
				striped : true,
				pagination : true
			});
			memberGridInited = true;
		}
		
		/**
		 * 初始化企业列表
		 */
		if(enterpriseGridInited){
			$('#enterpriseSearchbox').searchbox('clear');
			$('#enterpriseGrid').datagrid('load',{});
		}
		else{
			$('#enterpriseSearchbox').searchbox({
				prompt:'输入关键字搜索',
				width:250,
				menu:'#enterpriseSearchboxMenu',
				searcher:function(value,name){
					var param = {};
					if(value && name){
						param.searchName = name;
						param.searchValue = value;
					}
					$('#enterpriseGrid').datagrid('load',param);
				}
			});
			$('#checkEnterpriseBtn').click(function(){
				var rows = $('#enterpriseGrid').datagrid('getChecked');
				if(rows.length < 1){
					$.messager.alert('','请选择数据');
					return;
				}
				if(callback){
					callback(rows[0]);
				}
				$('#selectkWindow').dialog('close');
			});
			
			$('#enterpriseGrid').datagrid({
				url : 'api/memberCard_enterpriseGrid.action',
				columns : [ [ {
					field : 'enterId',
					title : 'ID',
					width : 10,
					checkbox : true
				},{
					field : 'enterName',
					title : '企业名称',
					width: 15,
					align:'center'
				}, {
					field : 'enterAddress',
					title : '企业地址',
					width : 20,
					align : 'center'
				}, {
					field : 'busLisCode',
					title : '营业执照代码',
					width : 20,
					align : 'center'
				}, {
					field : 'legalPersonName',
					title : '法定代表人姓名',
					width : 20,
					align : 'center'
				}, {
					field : 'enterTell',
					title : '联系电话',
					width : 20,
					align : 'center'
				}, {
					field : 'enterRemark',
					title : '备注',
					width : 30,
					align : 'center',
					formatter:function(value){
						if(value){
							return '<span title="'+value+'">'+value+'</span>';
						}
					}
				}] ],
				toolbar:'#enterpriseGridTB',
				fit : true,
				border:false,
				singleSelect:true,
				fitColumns : true,
				rownumbers : true,
				striped : true,
				pagination : true
			});
			enterpriseGridInited = true;
		}
	}
	
	win.memberCard = memberCard;
	$(function(){
		memberCard.init();
	});
})(window);