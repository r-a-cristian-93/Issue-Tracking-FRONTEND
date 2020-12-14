
function getDepartments(parse, select) {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if(this.readyState==4 && this.status==200){
			parse(JSON.parse(this.responseText), select);
		}		
	}
	//request.open('GET', '/helpdesk/user/listdepartments');
	request.open('GET', '/helpdesk-rest/options/getDepartments');
	request.send();
}

function getRoles(parse, select) {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if(this.readyState==4 && this.status==200){
			parse(JSON.parse(this.responseText), select);
		}		
	}
	//request.open('GET', '/helpdesk/user/listroles');
	request.open('GET', '/helpdesk-rest/options/getRoles');
	request.send();
}

function getStatus(parse, select) {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if(this.readyState==4 && this.status==200){
			parse(JSON.parse(this.responseText), select);
		}		
	}
	//request.open('GET', '/helpdesk/user/liststatus');
	request.open('GET', '/helpdesk-rest/options/getStatus');
	request.send();
}

function setOptions(list, select) {
	for(i=0; i<list.length; i++) {
		var option = document.createElement('option');
		option.appendChild(document.createTextNode(list[i].value));
		select.appendChild(option);
	}
}
