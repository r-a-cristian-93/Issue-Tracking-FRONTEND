document.addEventListener("DOMContentLoaded", function(){	
    getDepartments(setOptions, document.filter.department);
    getStatus(setOptions, document.filter.status);
    filterManageTickets();
})

function filterManageTickets() {
	var d = $("#filter-department").val();
	var s = $('#filter-status').val();
	var params = {};
	
	if(d!='All') {
		params['department'] = d;
	}
	if(s!='All') {
		params['status'] = s;
	}		
	
	$.when(getMyInfo()).done(function(info) {
		var intRole;
		switch(info.role.value) {
			case 'Owner': intRole = 3; break;
			case 'Moderator': intRole = 2; break;
			case 'Admin': intRole = 1; break;
			default: intRole = 0;
		}
		$.ajax({
			method: 'GET',
			dataType: 'json',
			xhrFields: { withCredentials: true },
			data: params,
			url: REST_API + '/tickets/manage',
			success: function(response) {
				onreadyFilterTickets(response, intRole);
			}
		});				
	});	
}

function getMyInfo() {
	return $.ajax({
		method: 'GET',
		dataType: 'json',
		xhrFields: { withCredentials: true },
		url: REST_API + '/usermanagement/myinfo',
	});
}
