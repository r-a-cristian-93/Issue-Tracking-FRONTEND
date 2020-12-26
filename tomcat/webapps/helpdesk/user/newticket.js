document.addEventListener("DOMContentLoaded", function(){	
    getDepartments(setOptions, document.newticket.concernedDepartment);
})

$(document).ready(function() {
	$(document.newticket).submit(function(event) {		
		event.preventDefault();
		$.ajax({
			method: 'post',
			xhrFields: { withCredentials: true },
			data : {
				concernedDepartment: document.newticket.concernedDepartment.value,
				summary: document.newticket.summary.value,
				issue: document.newticket.issue.value			
			},
			url: REST_API + '/tickets/add',
			success: function(result) {
				window.location = CLIENT_URL + '/user/new-ticket-created.html';
			},
			error: function(request,status,error) {
				window.location = CLIENT_URL + '/user/new-ticket-failed.html';
			}
		});		
	});
});
