(function($){
	$(function(){
		$.extend({
			openExcelPreview:function(url,param){
				var ExcelSheet;
				var wb;
				var paperSize;
				if(param && param.paperName){
					paperSize = getPaperSize(param.paperName);
					if(!paperSize){
						return;
					}
				}
				try {
					ExcelSheet = new ActiveXObject("Excel.Application");
					wb = ExcelSheet.Workbooks.open(url);
					wb.Activate;
					ExcelSheet.DisplayAlerts=false;
					ExcelSheet.Save();
					// 使EXCEL窗口可见
					ExcelSheet.Visible = true;
					
					if(paperSize)
						ExcelSheet.ActiveSheet.PageSetup.PaperSize = paperSize;
					
					ExcelSheet.ActiveSheet.PrintPreview();
					
					ExcelSheet.WorkBooks.Close();
					ExcelSheet.Quit();
				}catch(e) {
					if (ExcelSheet){
						alert('错误 : ' + e);
						ExcelSheet.Quit();
						return;
					}
					alert("请启用ActiveX控件设置!");
					return;
				}
			}
		});
	});
	
	/*根据打印纸张名称获取打印纸张的PaperSize*/
	function getPaperSize(paperName){
		if(!paperName){
			return null;
		}
		var fileName = 'C://Windows//fwgl.txt';
		
	    var fso = new ActiveXObject("Scripting.FileSystemObject");
	    if(!fso.FileExists(fileName)){
	    	alert('请确保打印环境文件已配置');
	    	return null;
	    }
	    var ForReading = 1;
	    var f1 = fso.OpenTextFile(fileName, ForReading);
	    var text = f1.ReadAll();
	    f1.close();
	    var settings = $.parseJSON(text);
	    if(settings.length < 1){
	    	alert('打印纸张找不到，请联系管理员');
	    	return null;
	    }
	    for(var i = 0;i<settings.length;i++){
	    	var setting = settings[i];
	    	if(setting.name==paperName){
	    		return setting.PaperSize;
	    	}
	    }
	    alert('打印纸张找不到，请联系管理员');
	    return null;
	}
})(jQuery);