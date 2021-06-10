function assignTicket(ticketId, assignTo) {
	return $.ajax({
		method: 'PUT',
		xhrFields: { withCredentials: true },
		url: REST_API + '/tickets/' + ticketId + '/update',
		contentType: 'application/json',
		data: JSON.stringify(assignTo)
	});
}

$(document).ready(function(){
	$(document.assignTicketForm).submit(function(event){
		event.preventDefault();
		var ticketId = event.target.ticketId.value;
		var assignTo = {id: event.target.assignTo.value};
		$.when(assignTicket(ticketId, assignTo)).then(function(data){
			hideAssignModal();
			refreshTicket(data);
		});
	});	
});

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
