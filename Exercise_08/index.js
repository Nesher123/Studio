const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs');

let app = express();
app.set('port', (process.env.PORT || 5000));

app.use(bodyParser.json());

app.get('/getFile/:filename',(req, res) => {
	let fileToRead = req.params.filename || "showMyName.txt";
	const src = fs.createReadStream(fileToRead);
	src.on('error', handler);
	src.pipe(res);
});

app.listen(app.get('port'), () => {
	console.log(`Listening on port ${app.get('port')}!`);
});

// error-handling function (written as arrow function)
const handler = err => console.log(err);
