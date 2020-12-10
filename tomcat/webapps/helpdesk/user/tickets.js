function makeIdLabel(jsonTicket) {
	var text=document.createTextNode('#'+jsonTicket.id);
	var h5=document.createElement('h5');
	h5.className='ticket-id';
	h5.appendChild(text);
	return h5;
}

function makeStatusLabel(jsonTicket) {
	var text=document.createTextNode(jsonTicket.status.toUpperCase());
	var h5=document.createElement('h5');
	h5.className=jsonTicket.status.toLowerCase();
	h5.appendChild(text);
	return h5;
}

function makeIssueParagraph(jsonTicket) {
	var summary = document.createElement('div');
	summary.className = 'ticket-summary';
	summary.innerHTML=jsonTicket.summary;
	var issue = document.createElement('p');
	issue.innerHTML = jsonTicket.issue;
	var div = document.createElement('div');
	div.className='ticket-issue';
	div.appendChild(summary);
	div.appendChild(issue);
	return div;
}

function makeTicketCol(title, value) {
	var letter=document.createTextNode(value[0].toUpperCase());
	var divLogo = document.createElement('div');
	divLogo.className='ticket-logo';
	divLogo.appendChild(letter);
	
	var divTitle = document.createElement('div');
	divTitle.className = 'ticket-col-title';
	divTitle.appendChild(document.createTextNode(title));
	
	var a = document.createElement('a');
	a.className='ticket-col-value';
	a.setAttribute('href', 'mailto:'+value);
	a.appendChild(document.createTextNode(value));
	
	var divInfo = document.createElement('div');
	divInfo.className = 'ticket-col-info';
	divInfo.appendChild(divTitle);
	divInfo.appendChild(a);	
	
	var divCol = document.createElement('div');
	divCol.className='ticket-col';
	divCol.appendChild(divLogo);
	divCol.appendChild(divInfo);

	return divCol;
}


/* 0 - user
 * 1 - admin
 * 2 - moderator
 * 3 - owner
 */

function onreadyFilterTickets(intRole) {
	return function() {
		if(this.readyState==4 && this.status==200){
			var jsonTickets = JSON.parse(this.responseText);
			
			if(intRole>0) {
				document.filter.childNodes[7].innerHTML = 'Found ' + jsonTickets.length + ' tickets.'
			}
			
			var divTickets = document.getElementsByClassName('ticket');
			var length = divTickets.length;
			for(i=0; i<length; i++) {
				divTickets[0].remove();
			}
			
			for(i=0; i<jsonTickets.length; i++) {
				var ticket = buildTicket(jsonTickets[i], intRole);
				document.getElementById('tickets').appendChild(ticket);
			}
		}
	}
}

function buildTicket(jsonTicket, intRole) {
	var status = jsonTicket.status;

	var divTopLeft = document.createElement('div');
	divTopLeft.className='ticket-top-left';
	divTopLeft.appendChild(makeIdLabel(jsonTicket));
	divTopLeft.appendChild(makeStatusLabel(jsonTicket));
	
	var divTopRight = document.createElement('div');
	divTopRight.className='ticket-top-right';
	divTopRight.appendChild(makeIssueParagraph(jsonTicket));
	
	var divTopHalf = document.createElement('div');
	divTopHalf.className='ticket-top-half';
	divTopHalf.appendChild(divTopLeft);
	divTopHalf.appendChild(divTopRight);
	
	var divBottomHalf = document.createElement('div');
	divBottomHalf.className='ticket-bottom-half';
	if(intRole>1) {
		divBottomHalf.appendChild(makeAssignOption(jsonTicket));
	}
	if(intRole>0) {
		if(status=='Open' || status=='Pending'){
			divBottomHalf.appendChild(makeCloseOption(jsonTicket, intRole));
		}
	}
	if(intRole>2) {
		divBottomHalf.appendChild(makeDeleteOption(jsonTicket));
	}
	divBottomHalf.appendChild(makeTicketCol('Opened by', jsonTicket.openedBy));
				
	if(jsonTicket.assignedTo) {
		divBottomHalf.appendChild(makeTicketCol('Assigned to', jsonTicket.assignedTo));
	}
	if(jsonTicket.closedBy) {
		divBottomHalf.appendChild(makeTicketCol('Closed by', jsonTicket.closedBy));
	}	
	
	var ticket=document.createElement('div');
	ticket.className='ticket';
	ticket.setAttribute('id', jsonTicket.id);
	ticket.append(divTopHalf);
	ticket.append(divBottomHalf);
	
	return ticket;
}
