function form2json(form) {
	var txt = '';
	// input text
	$(form).find('input[type="text"]').each(
			function() {
				if ($(this).attr('name') != undefined) {
					txt += '"' + $(this).attr('name') + '":"'
							+ serialize($(this).val()) + '",';
				}
			});
	$(form).find('input[type="hidden"]').each(
			function() {
				if ($(this).attr('name') != undefined) {
					txt += '"' + $(this).attr('name') + '":"'
							+ serialize($(this).val()) + '",';
				}
			});
	// checkbox
	var chknames = '';
	$(form).find('input[type="checkbox"]').each(
			function() {
				if (this.name != undefined ) {
					if (this.checked) {
						if (chknames.toUpperCase().indexOf(
								this.name.toUpperCase()) >= 0) {
							var reg = new RegExp('"' + this.name + '":'
									+ '"(.+?)",', 'i');
							txt = txt.replace(reg, '"' + this.name + '":'
									+ '"$1,' + this.value + '",');
						} else {
							chknames += this.name + ',';
							txt += '"' + this.name + '":"' + this.value + '",';
						}
					}
				}
			});
	// radio
	var rdnames = '';
	$(form).find('input[type="radio"]').each(
			function() {
				if (this.name != undefined ) {
					if (this.checked) {
						if (rdnames.toUpperCase().indexOf(
								this.name.toUpperCase()) >= 0) {
							var reg = new RegExp('"' + this.name + '":'
									+ '"(.+?)",', 'i');
							txt = txt.replace(reg, '"' + this.name + '":' + '"'
									+ this.value + '",');
						} else {
							rdnames += this.name + ',';
							txt += '"' + this.name + '":"' + this.value + '",';
						}
					}
				}
			});

	// textArea
	$(form).find('textArea').each(
			function() {
				if ($(this).attr('name')!= undefined) {
					txt += '"' + $(this).attr('name') + '":"'
							+ serialize($(this).val()) + '",';
				}
			});
	// select
	$(form).find('select').each(
			function() {
				var name = $(this).attr('name');
				if (name != '' && name != undefined) {
					if ($(this).attr('name')!= undefined) {
						var value = '';
						if ($(this).attr("multiple") == "multiple") {
							var maxIndex = $(this).find("option:last").index();
							for ( var i = 0; i <= maxIndex; i++) {
								if (i == maxIndex) {
									value += this.options[i].value;
								} else {
									value += this.options[i].value + ",";
								}
							}
						}else {
							if ($(this).val().length >= 0) {
								if (this.options[this.selectedIndex].value) {
									value = this.options[this.selectedIndex].value;
								} else {
									value = this.options[this.selectedIndex].value;
								}
							} 
						}
						txt += '"' + $(this).attr('name') + '":"'
								+ serialize(value) + '",';
					}
				}
			});
	//file
	txt = txt.replace(/,$/, '');
	return "{" + txt + "}";
	// var xobj=null;
	// eval('xobj={'+txt+'}');
	// return xobj;
}
function serialize(text) {
	text = text.replace(/(")/g, "\\\"");
	return text;
}