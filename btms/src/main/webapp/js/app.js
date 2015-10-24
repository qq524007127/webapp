(function(win){
	var app = {};
	app.getCostTypeName = function(key){
		switch (key) {
		case 0:
			return '普通收费类型';
			break;
		case 1:
			return '会员费类型';
			break;
		case 2:
			return '福位管理费类型';
			break;
		case 3:
			return '福位租赁费类型';
			break;
		default:
			break;
		}
	};
	app.init = function(baseUrl){
		app.host = "http://192.168.1.179:8080" + baseUrl;
		//app.host = "http://120.27.45.23" + baseUrl;
		app.baseUrl = baseUrl;
		document.writeln('<link rel="stylesheet" type="text/css" href="'+baseUrl+'/easyui/themes/default/easyui.css" />');
		document.writeln('<link rel="stylesheet" type="text/css" href="'+baseUrl+'/easyui/themes/icon.css" />');
		document.writeln('<link rel="stylesheet" type="text/css" href="'+baseUrl+'/css/style.css" />');
		document.writeln('<link rel="stylesheet" type="text/css" href="'+baseUrl+'/css/icon.css" />');
		document.writeln('<script type="text/javascript" src="'+baseUrl+'/easyui/jquery.min.js" charset="UTF-8" ></script>');
		document.writeln('<script type="text/javascript" src="'+baseUrl+'/easyui/jquery.easyui.min.js" charset="UTF-8" ></script>');
		document.writeln('<script type="text/javascript" src="'+baseUrl+'/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8" ></script>');
		document.writeln('<link rel="shortcut icon" href="'+baseUrl+'/img/favicon.ico">');
		
	};
	app.addScript = function(script){
		if(!script){
			return;
		}
		document.writeln('<script type="text/javascript" src="'+app.baseUrl+'/js/'+script+'" charset="UTF-8" ></script>');
	};
	app.addStyle = function(style){
		if(!style){
			return;
		}
		document.writeln('<link rel="stylesheet" type="text/css" href="'+app.baseUrl+'/css/'+style+'" />');
	};
	
	win.app = app;
})(window);