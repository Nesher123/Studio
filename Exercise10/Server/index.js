// API KEY: BVQ0TBJTX4WPV0H0
const alphavantage = require('alphavantage')({key: 'BVQ0TBJTX4WPV0H0'});
const app = require('express')();
var replaceall = require('replaceall');
const server = require('http').Server(app)
const io = require('socket.io')(server);
const PORT = (process.env.PORT || 5000)
const https = require('https');
const bodyParser = require('body-parser');

let m_stockName;
let m_socket;


io.on('connect', (socket) => {
	//console.log(socket);
	m_socket = socket;
	console.log('emitted');
	socket.on('StockName', (stockName) => {
		m_stockName = stockName;
		console.log(stockName);
		startCyclicPing();
	});
});

let startCyclicPing = () => {
		// TODO: timer every 15 sec call getDataFromAlphaAdvantage
		getDataFromAlphaAdvantage();
}

let getDataFromAlphaAdvantage = () => {
	let parsedData, newTrimdata; 
	alphavantage.data.intraday(m_stockName)
		.then((data) => {
			//console.log(data);
			//let stringData = Buffer.from(data, 'base64').toString('ascii');
			//fix(stringData);
			//parsedData = JSON.parse(data);
			console.log(replaceall("\'", "\"", data['Time Series (1min)']));
			
			//parsedData = Object.keys(data['Time Series (1min)']);
			//newTrimdata = data['Time Series (1min)'][parsedData]["1. open"];
			//console.log(newTrimdata);
			//m_socket.emit('stockData', parsedData); // emit an event to the sockets
			//io.emit('broadcast', 'test'); // emit an event to all connected sockets
		})
		
}

let fix = (string) => {
	
}

server.listen(PORT, () => {
	console.log(`Listening on port ${PORT}!`);
});

// error-handling function (written as arrow function)
const handler = err => console.log(err);