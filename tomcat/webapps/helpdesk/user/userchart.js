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
			labels: [],
			datasets: [{
				data: [],
				backgroundColor: []
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
				color: [],
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
	$.ajax({
		method: 'get',
		dataType: 'json',
		xhrFields: { withCredentials: true },	//takes the cookie
		url: REST_API+'/tickets/count',
		success:function(response){
			var totalCount = 0;
			for(i=0; i<response.length; i++) {
				var status = response[i].status;
				var count = response[i].count;
				var color;
				myChart.data.labels[i] = status;
				myChart.data.datasets[0].data[i] = count;											
				switch (response[i].status) {
					case 'Open': color  = '#FFA500'; break;
					case 'Pending': color = '#5EA1C2'; break;
					case 'Solved': color = '#008000'; break;
					case 'Unsolved': color = '#CE2727'; break;
					default:
				}
				myChart.data.datasets[0].backgroundColor[i] = color;
				myChart.options.plugins.datalabels.color[i] = color;
				totalCount+=count;
			}
			myChart.update();
			document.getElementById("totalcount").innerHTML = 'You have a total of '+totalCount+' tickets.';
		}
	});
}


