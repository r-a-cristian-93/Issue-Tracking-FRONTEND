document.addEventListener("DOMContentLoaded", function(){	
	var myChart;
	buildChart();
    getTicketsCount();
});

function buildChart() {
	var ctx = document.getElementById('chartSummary').getContext('2d');
	myChart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: ['Open', 'Pending', 'Solved', 'Unsolved'],
			datasets: [{
				data: [0, 0, 0, 0],
				backgroundColor: [
					'#FFA500',
					'#5EA1C2',
					'#008000',
					'#CE2727'
				]
			}]
		},
		options: {
			title: {
				text: 'Tickets by status type.',
				display: true,
				position: 'bottom',
				fontSize: 15		
			},
			tooltips: {
				enabled: false,
			},
			layout:{
				padding: {
					top: 30
				}
			},		
			maintainAspectRatio: true,
			responsive: true,
			legend: {
				display: false
			},
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true,
						display: false
					},
					gridLines: {
						drawOnChartArea: false,
						drawBorder: false,
						drawTicks: false
					}
				}],
				xAxes: [{
					ticks: {  
						padding:10,
						fontSize: 18,
						fontColor: 'black' 
					},
					gridLines: {
						drawOnChartArea: false,
						drawTicks: false
					},
					
				}]
			},
			plugins: {
			  datalabels: {
				color: [
					'#FFA500',
					'#5EA1C2',
					'#008000',
					'#CE2727'
				],
				anchor: 'end',
				align: 'top',
				formatter: Math.round,
				font: {
				  weight: 'bold',
				  size: '15'
				}
			  }
			}
		}
	});
	myChart.aspectRatio = 0;
}



function getTicketsCount() {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if(this.readyState==4 && this.status==200){
			var c = JSON.parse(this.responseText);
			if(!c.Open){c.Open=0;}
			if(!c.Pending){c.Pending=0;}
			if(!c.Solved){c.Solved=0;}
			if(!c.Unsolved){c.Unsolved=0;}

			myChart.data.datasets[0].data = [c.Open, c.Pending, c.Solved, c.Unsolved];
			myChart.update();
			
			var totalCount = c.Open+c.Pending+c.Solved+c.Unsolved;
			document.getElementById("totalcount").innerHTML = 'You have a total of '+totalCount+' tickets.';
		}		
	}
	request.open('GET', '/helpdesk/user/counttickets');
	request.send();
}


