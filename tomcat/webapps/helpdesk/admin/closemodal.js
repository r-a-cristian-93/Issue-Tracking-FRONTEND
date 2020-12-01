function showCloseModal(ticketId) {
	document.closeTicketForm.ticketId.value = ticketId;
	document.getElementById("closeId").innerHTML = ticketId;
	document.getElementById("closeModal").style.display="block";
}

function hideCloseModal() {
	document.getElementById("closeModal").style.display="none";
}

function closeTicket() {
	var ticketId = document.closeTicketForm.ticketId.value;
	var status = document.closeTicketForm.status.value;
	
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if(this.readyState==4 && this.status==200) {
			hideCloseModal();
			refreshTicket(ticketId);
		}		
	}	
	request.open('GET', '/helpdesk/admin/closeticket?ticketId='+ticketId+'&status='+status);
	request.send();
}

function makeCloseOption(jsonTicket) {
	var img = document.createElement('img');
	img.className='option-img';
	img.setAttribute('src', '../icons/close.png');
	var div = document.createElement('div');
	div.className='option close-option'; //close-option required for refreshTicket
	div.setAttribute('onclick', 'showCloseModal(' + jsonTicket.id + ')');
	div.appendChild(img);
	return div;
}

function refreshTicket(ticketId) {	
	var request = new XMLHttpRequest();
	request.onreadystatechange = function () {
		if(this.readyState==4 && this.status==200) {
			jsonTicket = JSON.parse(this.responseText);
			var old = document.getElementById(ticketId);
			
			var intRole = 0;
			if (old.getElementsByClassName('close-option').length==1) {
				intRole = 1;
			}
			if (old.getElementsByClassName('assign-option').length==1) {
				intRole = 2;
			}
			if (old.getElementsByClassName('delete-option').length==1) {
				intRole = 3;
			}	
			var ticket = buildTicket(jsonTicket[0], intRole);		
			old.parentNode.replaceChild(ticket, old);
		}
	}
	request.open('GET', '/helpdesk/admin/getticket?id='+ ticketId);
	request.send();
}
