var REST_API = "http://localhost:8081"
var CLIENT_URL = "http://localhost"

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
	$.ajax({
		method: 'GET',
		dataType: 'json',
		xhrFields: { withCredentials: true },
		url: REST_API + '/options/status',
		success: function(response) {
			parse(response, select);
		}
	});
}

function setOptions(list, select) {
	for(i=0; i<list.length; i++) {
		var option = document.createElement('option');
		option.appendChild(document.createTextNode(list[i].value));
		select.appendChild(option);
	}
}

function formSubmitToJson(event) {
	const data = new FormData(event.target);
	const object = Object.fromEntries(data.entries());
	return object
};
