var REST_API = "http://localhost:8080"
var CLIENT_URL = "http://localhost/helpdesk"

function getDepartments(parse, select) {
	$.ajax({
		method: 'GET',
		dataType: 'json',
		xhrFields: { withCredentials: true },
		url: REST_API + '/options/departments',
		success: function(response) {
			parse(response, select);
		}		
	});
}

function getRoles(parse, select) {
	$.ajax({
		method: 'GET',
		dataType: 'json',
		xhrFields: { withCredentials: true },
		url: REST_API + '/options/roles',
		success: function(response) {
			parse(response, select);
		}		
	});
}

function getStatus(parse, select) {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if(this.readyState==4 && this.status==200){
			parse(JSON.parse(this.responseText), select);
		}		
	}
	//request.open('GET', '/helpdesk/user/liststatus');
	request.open('GET', '/helpdesk-rest/options/get/status');
	request.send();
}

function setOptions(list, select) {
	for(i=0; i<list.length; i++) {
		var option = document.createElement('option');
		option.appendChild(document.createTextNode(list[i].value));
		select.appendChild(option);
	}
}
