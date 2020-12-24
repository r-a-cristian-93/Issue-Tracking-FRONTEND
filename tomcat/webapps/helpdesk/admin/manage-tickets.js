document.addEventListener("DOMContentLoaded", function(){	
    getDepartments(setOptions, document.filter.department);
    getStatus(setOptions, document.filter.status);
    filterManageTickets();
})

function filterManageTickets() {
	var request = new XMLHttpRequest;
	var department = document.filter.department.value;
	var status = document.filter.status.value;
	
	request.onreadystatechange = onreadyFilterTickets(3);
	request.open('GET', '/helpdesk/owner/superfilter?department='+department+'&status='+status);
	request.send();
}
