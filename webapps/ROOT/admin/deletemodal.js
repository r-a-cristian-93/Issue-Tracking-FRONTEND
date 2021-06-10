function deleteTicket(ticketId) {
	return $.ajax({
		method: 'DELETE',
		xhrFields: { withCredentials: true },
		url: REST_API + '/tickets/' + ticketId + '/delete'
	});
}

$(document).ready(function(){
	$(document.deleteTicketForm).submit(function(event){
		event.preventDefault();
		var ticketId = event.target.ticketId.value;
		$.when(deleteTicket(ticketId)).then(function(data){
			hideDeleteModal();
			document.getElementById(ticketId).remove();
		});
	});
});

function showDeleteModal(ticketId) {
	document.deleteTicketForm.ticketId.value = ticketId;
	document.getElementById("deleteId").innerHTML = ticketId;
	document.getElementById("deleteModal").style.display="block";
}

function hideDeleteModal() {
	document.getElementById("deleteModal").style.display="none";
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
