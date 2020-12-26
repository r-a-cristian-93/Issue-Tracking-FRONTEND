function showDeleteModal(ticketId) {	
	document.deleteTicketForm.ticketId.value = ticketId;
	document.getElementById("deleteId").innerHTML = ticketId;
	document.getElementById("deleteModal").style.display="block";
}

function hideDeleteModal() {
	document.getElementById("deleteModal").style.display="none";
}

function deleteTicket() {
	var ticketId = document.deleteTicketForm.ticketId.value;
	
	var request = new XMLHttpRequest();
	request.withCredentials = true;
	request.onreadystatechange = function() {
		if(this.readyState==4 && this.status==200) {
			hideDeleteModal();
			document.getElementById(ticketId).remove();
		}		
	}	
	request.open('DELETE', REST_API+'/tickets/'+ticketId+'/delete');
	request.send();
}

function makeDeleteOption(jsonTicket) {
	var img = document.createElement('img');
	img.className='option-img';
	img.setAttribute('src', '../icons/delete.png');
	var div = document.createElement('div');
	div.className='option delete-option'; //delete-option required for refreshTicket
	div.setAttribute('onclick', 'showDeleteModal(' + jsonTicket.id + ');');
	div.appendChild(img);
	return div;
}
