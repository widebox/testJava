define([ "jquery", "bootstrap-datetimepicker.min" ], function($) {
	return function() {
		$(".datepicker").datetimepicker({
			format : 'yyyy-mm-dd',
			weekStart : 0,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0
		});

		$(".timepicker").datetimepicker({
			format : 'yyyy-mm-dd hh:ii',
			language : 'zh-TW',
			autoclose : true,
			todayBtn : true,
			minView : 0,
			minuteStep : 1
		});

	}
});
