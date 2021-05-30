document.addEventListener("DOMContentLoaded", function(){	
	getOverviewCount();
    myTickets('All');    
})

function myTickets(status) {
	var filter = {};

	if(status!='All') {
		filter['status'] = status;
	}		
	
	$.ajax({
		method: 'GET',
		dataType: 'json',
		xhrFields: { withCredentials: true },
		data: filter,
		url: REST_API + '/tickets/mytickets',
		success: function(response) {
			onreadyFilterTickets(response, 0);
		}
	});
}


function buildSmallBox(status, count) {
	var img = document.createElement('img');
	img.setAttribute('src', '../icons/big-'+status.toLowerCase()+'.png');
	var divLeft = document.createElement('div')
	divLeft.className='small-left '+ status.toLowerCase();
	divLeft.appendChild(img);
	
	var divR1 = document.createElement('div');
	divR1.className = 'small-box-row';
	divR1.innerHTML = status.toUpperCase();
	var divR2 = document.createElement('div');
	divR2.className = 'small-box-row';
	divR2.innerHTML = count;
	var divRight = document.createElement('div');
	divRight.className = 'small-right';
	divRight.appendChild(divR1);
	divRight.appendChild(divR2);
	
	var divBox = document.createElement('div');
	divBox.className = 'simple-box small '+status.toLowerCase()+'-trans';
	divBox.setAttribute('onclick', 'myTickets(\''+status+'\')');
	divBox.appendChild(divLeft);
	divBox.appendChild(divRight);
		
	return divBox;
}

function getOverviewCount() {
	$.ajax({
		method: 'GET',
		dataType: 'json',
		xhrFields: { withCredentials: true },
		url: REST_API + '/tickets/count',
		success: function(response) {			
			var totalCount = 0;
			for(i=0; i<response.length; i++) {
				var status = response[i].status;
				var count = response[i].count;										
				document.getElementById('overview').appendChild(buildSmallBox(status, count));
				totalCount+=count;
			}
			document.getElementById('overview').appendChild(buildSmallBox('All', totalCount));
		}
	});
}
