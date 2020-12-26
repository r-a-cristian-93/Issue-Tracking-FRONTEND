document.addEventListener("DOMContentLoaded", function(){	
    getMenu();
})

$(document).ready(function(){
	$('#button-logout').click(function() {
		document.cookie = 'JWT=; expires=-1; path=/';
		window.location = CLIENT_URL;	
	});
});

function getMenu() {
	$.ajax({
		method: 'GET',
		dataType: 'json',
		xhrFields: { withCredentials: true},
		url: REST_API + '/usermanagement/myinfo',
		success: function(response) {
			document.getElementById('access-level').innerHTML = response.role.toUpperCase();
			document.getElementById('welcome-user').innerHTML="Hello, " + response.email;
			document.getElementById('welcome-user').setAttribute('href', '');
			document.getElementById('button-logout').setAttribute('href', CLIENT_URL);
			
			addMenuItem('Dashboard', CLIENT_URL + '/user/dashboard.html');
			addMenuItem('New Ticket', CLIENT_URL + '/user/new-ticket.html');
			addMenuItem('My Tickets', CLIENT_URL + '/user/my-tickets.html');
			if(response.role!='User') {
				addMenuItem('Manage Tickets', CLIENT_URL + '/admin/manage-tickets.html');
			}
			if(response.role=='Owner') {
				addMenuItem('Register', CLIENT_URL + '/admin/register.html');
			}
						
			function addMenuItem(name, url) {
				var menuItem = document.createElement('a');
				var nameNode = document.createTextNode(name);
				var icon = document.createElement('img');
				icon.setAttribute('src', '../icons/'+name+'.png');
				icon.className = 'link-icon';
				menuItem.setAttribute('href', url);
				menuItem.appendChild(icon);
				menuItem.appendChild(nameNode);
				document.getElementById('menu').appendChild(menuItem);	
				
			}			
		},
		error: function(request, status, error) {
			window.location = CLIENT_URL + '/login.html';
		}
	});
}

function showMenu() {
	var menu = document.getElementById('menu').style;
	var content = document.getElementById('content').style;
	var footer = document.getElementById('footer').style;
	var menuState = menu.display;
	var contentState = content.display;
	var footerState = footer.display;
	var stateA;
	var stateB;	
		
	if (menu.display == 'block') {
		stateA= 'none';
		stateB= 'block';
	}
	else {
		stateA = 'block';
		stateB = 'none';
	}
	menu.display = stateA;
	content.display = stateB;
	footer.display = stateB;
}
	
