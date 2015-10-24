(function(win, $) {
	$(function() {
		init();
	});
	function init() {
		/*$('#editPasswordPanel').panel({
			title : '密码修改',
			iconCls : 'icon-edit',
			fit : false,
			width : 400,
			height : 220
		});*/
		$('#submitForm').click(function() {
			$('#changePSWForm').form('submit', {
				success : function(data) {
					data = $.parseJSON(data);
					$.messager.alert('', data.msg);
				}
			});
		});
		$.extend($.fn.validatebox.defaults.rules, {
			equals : {
				validator : function(value, param) {
					return value == $(param[0]).val();
				},
				message : '两次输入的密码不一致'
			}
		});
		$('#rePassword').validatebox({
		    required: true,
		    validType: 'equals["#newPassword"]'
		});
	}
})(window, $);
