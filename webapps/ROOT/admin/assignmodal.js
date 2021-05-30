function showAssignModal(ticketId) {	
	listAdmins();
	document.assignTicketForm.ticketId.value = ticketId;
	document.getElementById("assignId").innerHTML = ticketId;
	document.getElementById("assignModal").style.display="block";
}

function hideAssignModal() {
	document.getElementById("assignModal").style.display="none";
}

function listAdmins() {
	$.ajax({
		method: 'GET',
		dataType: 'json',
		xhrFields: { withCredentials: true },
		url: REST_API + '/options/admins',
		success: function(jsonOptions) {	
			var assignOptions = document.assignTicketForm.assignTo.children;
			var length = assignOptions.length;
			for(i=0; i<length; i++) {
				assignOptions[0].remove();
			}		
			for(i=0; i<jsonOptions.length; i++) {
				var text = document.createTextNode(jsonOptions[i].email);
				var option = document.createElement('option');
				option.value=jsonOptions[i].id;
				option.appendChild(text);
				document.assignTicketForm.assignTo.appendChild(option);
			}
		}
	});
}

function assignTicket() {
	var ticketId = document.assignTicketForm.ticketId.value;
	var assignTo = document.assignTicketForm.assignTo.value;
	
	var request = new XMLHttpRequest();
	request.withCredentials = true;
	request.onreadystatechange = function() {
		if(this.readyState==4 && this.status==200) {
			hideAssignModal();
			var jsonTicket = JSON.parse(request.responseText);
			refreshTicket(jsonTicket);		
		}		
	}	
	request.open('PUT', REST_API+'/tickets/'+ticketId+'/update?assignTo=' +assignTo);
	request.send();
}


function makeAssignOption(jsonTicket) {
	var img = document.createElement('img');
	img.className='option-img';
	img.setAttribute('src', '../icons/assign.png');
	var div = document.createElement('div');
	div.className='option assign-option'; //assign-option required for refreshTicket
	div.setAttribute('onclick', 'showAssignModal(' + jsonTicket.id + ');');
	div.appendChild(img);
	return div;
}
