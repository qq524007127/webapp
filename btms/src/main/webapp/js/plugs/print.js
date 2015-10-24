/**
 * 
 * 打印控件,调用此控件时必须在页面中定义media=print的style
 * @author ShenYunjie
 * @param win	window
 * @param $	jquery
 */
(function(win, $) {

	var HKEY_Root, HKEY_Path, HKEY_Key;
	HKEY_Root = 'HKEY_CURRENT_USER';
	HKEY_Path = '\\Software\\Microsoft\\Internet Explorer\\PageSetup\\';

	var printer = {};

	/**
	 * 设置页眉、页脚为空
	 */
	function PageSetup_Setting(params) {
		try {
			var header = params.header ? params.header : '';
			var footer = params.footer ? params.footer : '';
			var Wsh = new ActiveXObject("WScript.Shell");
			HKEY_Key = "header";
			Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, header);
			HKEY_Key = "footer";
			Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, footer);
		} catch (e) {}
	}

	/**
	 * 恢复页眉、页脚默认设置
	 */
	function PageSetup_default() {
		try {
			var Wsh = new ActiveXObject("WScript.Shell");
			HKEY_Key = "header";
			Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "&w&b页码,&p/&P");
			HKEY_Key = "footer";
			Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "&u&b&d");
		} catch (e) {}
	}

	$(function() {
		init();
		//PageSetup_default();
	});

	function init() {
		var axtivX = '<object id="WebBrowser" classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height="0" width="0"></object>';
		if ($('#WebBrowser').length < 1) {
			$('body').append(axtivX);
		}
		printer.webBrowser = $('#WebBrowser')[0];
	}

	/**
	 * 直接打印
	 */
	printer.print = function print() {
		printer.webBrowser.ExecWB(6, 6);
	};

	/**
	 * 打印设置
	 */
	printer.setting = function printSetting() {
		printer.webBrowser.ExecWB(8, 1); //页面设置
	};

	/**
	 * 打印预览
	 */
	printer.preview = function printPreview(params) {
		PageSetup_Setting(params);
		printer.webBrowser.ExecWB(7, 1); //打印预览
	};
	win.printer = printer;
})(window, $);