	var KA={};
	
	KA.createProgressBar = function() {
		$("#progressbar").remove();
		$("body").append("<div id=\"progressbar\" style=\"display: none;\"><div class=\"progress-label\"></div></div>");
		$.blockUI({
			message: $("#progressbar")
		});
	};
	
	KA.destroyProgressBar = function() {
		$.unblockUI();
		$("#progressbar").remove();
	};
	
	
	KA.createProgressBarWithPopup = function(ispop){
	    KA.createProgressBar();
		$('body').addClass('busy');
		$('.ka-popup').find('*').prop('disabled', true).find('a').click(function(e){e.preventDefault();});
	};

	KA.destroyProgressBarWithPopup = function (){
		KA.destroyProgressBar();
		$('body').removeClass('busy');
	    $('.ka-popup').find('*').prop('disabled', false).find('a').unbind("click");
	};
	