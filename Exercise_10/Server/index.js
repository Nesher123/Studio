
/************
Shahaf Ben-Yakir & Ofir Nesher - Ex 10
*************/

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
	if(!m_socket){
		console.log('no socket');
		return;
	}
	else if(!m_stockName){
		socket.emit('cannot find');
        console.log('No such stock exists');
        return;
	}
	else {
		setInterval(getDataFromAlphaAdvantage, 15000);
	}
};


let getDataFromAlphaAdvantage = () => {
	let firstKey, newTrimdata; 
	alphavantage.data.intraday(m_stockName)
		.then((data) => {
			firstKey = Object.keys(data['Time Series (1min)'])[0]; // receive the first key in the object
			newTrimdata = data['Time Series (1min)'][firstKey]; // retrieve the last updated data
			console.log(newTrimdata);
			m_socket.emit('stockData', newTrimdata); // emit an event to the sockets
			//io.emit('broadcast', 'test'); // emit an event to all connected sockets
		})
		.catch((err) => {
			console.error(`error getting stock: ${m_stockName} info`);
			m_socket.emit('stockData', 'error getting stock info, please try again'); // in case this stock does not exist
		})
}

server.listen(PORT, () => {
	console.log(`Listening on port ${PORT}!`);
});

// error-handling function (written as arrow function)
const handler = err => console.log(err);
