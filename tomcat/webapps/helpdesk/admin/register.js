document.addEventListener("DOMContentLoaded", function(){	
    getDepartments(setOptions, document.register.department);
    getRoles(setOptions, document.register.role);
})

$(document).ready(function(){
	$(document.register).submit(function(event){
		event.preventDefault();
		$.ajax({
			method: 'POST',
			xhrFields: { withCredentials:true },
			url: REST_API + '/usermanagement/register',
			data: {
				username: document.register.email.value,
				password: document.register.password.value,
				department: document.register.department.value,
				role: document.register.role.value				
			},
			success: function(response) {
				window.location = CLIENT_URL + '/admin/register-successful.html';
			},
			error: function(request, status, error) {
				window.location = CLIENT_URL + '/admin/register-failed.html';
			}			
		});		
	});	
});
