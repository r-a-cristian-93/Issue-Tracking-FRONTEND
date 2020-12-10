document.addEventListener("DOMContentLoaded", function(){	
    getDepartments(setOptions, document.filter.department);
    getStatus(setOptions, document.filter.status);
    filterReviewTickets();
})

function filterReviewTickets() {
	var request = new XMLHttpRequest;
	var department = document.filter.department.value;
	var status = document.filter.status.value;
	
	request.onreadystatechange = onreadyFilterTickets(1);
	request.open('GET', '/helpdesk/admin/review-tickets?department='+department+'&status='+status);
	request.send();
}

