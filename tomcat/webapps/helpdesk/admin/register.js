document.addEventListener("DOMContentLoaded", function(){	
    getDepartments(setOptions, document.register.department);
    getRoles(setOptions, document.register.role);
})

$(document).ready(function(){
	$('#form-register-submit').click(function(){
		$.ajax({
			method: 'POST',
			xhrFields: { withCredentials:true },
			url: REST_API + '/usermanagement/register',
			data: {
				username: $('#email').val(),
				password: $('#password').val(),
				department: $('#department').val(),
				role: $('#role').val()				
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
