// API KEY: BVQ0TBJTX4WPV0H0

const express = require('express');
const bodyParser = require('body-parser');
//const fs = require('fs');
// const https = require('https');
const server = require('http').server(app)
const io = require('socket.io')(Server);
let app = express();


app.set('port', (process.env.PORT || 5000));

app.use(bodyParser.json());
let stockName;
let m_socket;

app.get('/stock/:stockName',(req, res) => {
	res.writeHead(200);
	res.end();
	console.log("reached get");
	stockName = req.params.stockName;
	startCyclicPing();
});


let startCyclicPing = () => {
		// timer every 15 sec call getDataFromAlphaAdvantage
		getDataFromAlphaAdvantage();
		
}

let getDataFromAlphaAdvantage = () => {
	let host = `https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=${stockName}&interval=1min&apikey=BVQ0TBJTX4WPV0H0`;

	https.get(host, (res) => {
	
		res.on('data', (d) => {
			//d = d.
			m_socket.emit('answer', d); // emit an event to the sockets
			//io.emit('broadcast', 'test'); // emit an event to all connected sockets
		});

		}).on('error', (e) => {
			console.error(e);
		});
}

io.on('connection', function (socket) {
	console.log(socket);
	console.log('emitted');
	m_socket = socket
	// startCyclicPing(socket);
});


app.listen(app.get('port'), () => {
	console.log(`Listening on port ${app.get('port')}!`);
});

// error-handling function (written as arrow function)
const handler = err => console.log(err);