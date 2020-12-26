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
	request.withCredentials = true;
	request.onreadystatechange = function() {
		if(this.readyState==4 && this.status==200) {
			hideCloseModal();
			var jsonTicket = JSON.parse(request.responseText);
			refreshTicket(jsonTicket);
		}		
	}	
	request.open('PUT', REST_API+'/tickets/'+ticketId+'/close?status=' + status);
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

function refreshTicket(jsonTicket) {
	var old = document.getElementById(jsonTicket.id);
	
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
	var ticket = buildTicket(jsonTicket, intRole);		
	old.parentNode.replaceChild(ticket, old);
}
