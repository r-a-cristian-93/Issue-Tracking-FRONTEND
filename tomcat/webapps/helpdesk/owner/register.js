document.addEventListener("DOMContentLoaded", function(){	
    getDepartments(setOptions, document.register.department);
    getRoles(setOptions, document.register.role);
})


function registerUser() {
	var form = document.forms['register'];
	var email = form['email'].value;
	var department = form['department'].value;
	var role = form['role'].value;
	if(email) {
		var request = new XMLHttpRequest();
		request.onreadystatechange = function() {
			if(this.readyState==4 && this.status==200) {
				var response = JSON.parse(request.responseText);
				document.getElementById('response').innerHTML = response;
				form['email'].value = '';
			}
		}
		request.open('GET', 'register?email='+email+'&department='+department+'&role='+role);
		request.send();
	}		
	else {
		document.getElementById('response').innerHTML = 'Email address can not be empty.';
	}
}
