const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs');

let app = express();
app.set('port', (process.env.PORT || 5000));

app.use(bodyParser.json());

app.get('/getTime', (req, res) => {
	let time = myTimeFunction();
	res.send(`The time now is: ${time}`);
});

app.get('/getFile/:filename',(req, res) => {
	let fileToRead = req.params.filename || "showMyName.txt";
	fs.readFile(fileToRead,(err, content) => {
		if (err) {
			console.error(err);
			return;
		}
	
	
	res.writeHead(200, {'Content-Type': 'text/html'});
	res.write(content);
	res.end();
	});
});

app.listen(app.get('port'), () => {
	console.log(`Listening on port ${app.get('port')}!`);
});

const myTimeFunction = () => {
	let date = new Date(),
	hours = (date.getHours() < 10 ? '0' : '') + date.getHours(),
	minutes = (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
	
	return  (hours + ':' + minutes);
}
