document.addEventListener("DOMContentLoaded", function(){	
    getDepartments(setOptions, document.newticket.concernedDepartment);
})

function openNewTicket() {
	var issue = document.newticket.issue.value;
	var summary = document.newticket.summary.value;
	var concernedDepartment = document.newticket.concernedDepartment.value;
	
	if(issue && summary) {		
		var request = new XMLHttpRequest();
		request.onreadystatechange = function() {
			if(this.readyState==4 && this.status==200) {
				var response = JSON.parse(request.responseText);
				document.getElementById('response').innerHTML = response;
			}
		}
		request.open('POST', 'newticket?concernedDepartment='+concernedDepartment+'&summary='+summary);
		request.send(issue);		
	}
	else {
		document.getElementById('response').innerHTML = 'The message can not be empty.';
	}	
}
