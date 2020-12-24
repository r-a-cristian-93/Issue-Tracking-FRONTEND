$(document).ready(function(){
    $('#form-login-submit').click(function(){
		console.log("wassup?");
		$.ajax({
			method: 'post',
			xhrFields: { withCredentials: true },	//takes the cookie
			url: REST_API+'/login',
			data:{
				username: $('#username').val(),
				password: $('#password').val(),
			},
			success:function(response){
				window.location = CLIENT_URL+'/user/dashboard.html';				
			},
			error: function (request, status, error) {
				window.location = CLIENT_URL+'/login-failed.html';
			}
		});
    });
});
