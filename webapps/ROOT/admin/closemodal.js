function closeTicket(ticketId, status) {
	return $.ajax({
		method: 'PUT',
		xhrFields: { withCredentials: true },
		url: REST_API + '/tickets/' + ticketId + '/close',
		contentType: 'application/json',
		data: JSON.stringify(status)
	});
}

$(document).ready(function(){
	$(document.closeTicketForm).submit(function(event){
		event.preventDefault();
		var ticketId = event.target.ticketId.value;
		var status = { value: event.target.status.value };
		$.when(closeTicket(ticketId, status)).then(function(data){
			hideCloseModal();
			refreshTicket(data);
		});
	});	
});


function showCloseModal(ticketId) {
	document.closeTicketForm.ticketId.value = ticketId;
	document.getElementById("closeId").innerHTML = ticketId;
	document.getElementById("closeModal").style.display="block";
}

function hideCloseModal() {
	document.getElementById("closeModal").style.display="none";
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
