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
	var assignOptions = document.assignTicketForm.assignTo.children;
	var length = assignOptions.length;
	for(i=0; i<length; i++) {
		assignOptions[0].remove();
	}
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if(this.readyState==4 && this.status==200){
			var jsonOptions = JSON.parse(this.responseText);
			for(i=0; i<jsonOptions.length; i++) {
				var text = document.createTextNode(jsonOptions[i].email);
				var option = document.createElement('option');
				option.value=jsonOptions[i].id;
				option.appendChild(text);
				document.assignTicketForm.assignTo.appendChild(option);
			}
		}
	}
	request.open('GET', '/helpdesk/moderator/listadmins');
	request.send();
}

function assignTicket() {
	var ticketId = document.assignTicketForm.ticketId.value;
	var assignTo = document.assignTicketForm.assignTo.value;
	
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if(this.readyState==4 && this.status==200) {
			hideAssignModal();
			refreshTicket(ticketId);		
		}		
	}	
	request.open('GET', '/helpdesk/moderator/assignticket?ticketId='+ticketId+'&assignTo='+assignTo);
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
