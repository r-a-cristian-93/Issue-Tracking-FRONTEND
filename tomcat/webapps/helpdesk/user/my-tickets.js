document.addEventListener("DOMContentLoaded", function(){	
	getOverviewCount();
    myTickets('All');    
})

function myTickets(status) {
	var request = new XMLHttpRequest;
	request.onreadystatechange = onreadyFilterTickets(0);
	request.open('GET', '/helpdesk/user/my-tickets?status='+status);
	request.send();
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
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if(this.readyState==4 && this.status==200){
			var c = JSON.parse(this.responseText);
			if(!c.Open){c.Open=0;}
			if(!c.Pending){c.Pending=0;}
			if(!c.Solved){c.Solved=0;}
			if(!c.Unsolved){c.Unsolved=0;}
			
			var totalCount = c.Open+c.Pending+c.Solved+c.Unsolved;
			
			document.getElementById('overview').appendChild(buildSmallBox('Open', c.Open));
			document.getElementById('overview').appendChild(buildSmallBox('Pending', c.Pending));
			document.getElementById('overview').appendChild(buildSmallBox('Solved', c.Solved));
			document.getElementById('overview').appendChild(buildSmallBox('Unsolved', c.Unsolved));			
			document.getElementById('overview').appendChild(buildSmallBox('All', totalCount));			
		}		
	}
	request.open('GET', '/helpdesk/user/counttickets');
	request.send();
}
