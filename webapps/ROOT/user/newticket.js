document.addEventListener("DOMContentLoaded", function(){	
    getDepartments(setOptions, document.newticket.concernedDepartment);
})

$(document).ready(function() {
	$(document.newticket).submit(function(event) {		
		event.preventDefault();
		var ticket = formSubmitToJson(event);
		ticket.concernedDepartment = {value: ticket.concernedDepartment};
		$.ajax({
			method: 'post',
			xhrFields: { withCredentials: true },
			contentType: "application/json",
			data : JSON.stringify(ticket),
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
