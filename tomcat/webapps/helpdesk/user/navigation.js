document.addEventListener("DOMContentLoaded", function(){	
    getMenu();
})

function getMenu() {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function(){
		if(this.readyState==4 && this.status==200) {
			var menuItems = JSON.parse(this.responseText);
			
			document.getElementById('access-level').innerHTML = menuItems[0].role.toUpperCase();
			document.getElementById('welcome-user').innerHTML="Hello, " + menuItems[0].name;
			document.getElementById('welcome-user').setAttribute('href', menuItems[0].url);
			for(i=1; i<menuItems.length; i++) {
				var name = menuItems[i].name;
				var url = menuItems[i].url;
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
		}
	}
	request.open('GET', '/helpdesk/user/navigation');
	request.send();
}
