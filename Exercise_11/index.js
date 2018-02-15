"use strict";

const express = require('express');
const app = express();
const MongoClient = require('mongodb').MongoClient;
const MONGO_URL = 'mongodb://Nesher:1333mlab@ds239217.mlab.com:39217/milab-studio';
const bodyParser = require('body-parser');
const ObjectId = require('mongodb').ObjectId;

app.use(bodyParser.urlencoded({extended: true}))
app.use(bodyParser.json());
app.set('PORT', (process.env.PORT || 5000));          // need PORT???

let myDatabase;

MongoClient.connect(MONGO_URL, (err, db) => {
	if (err) {
		console.error(err);
		return;	
	}
	else {  // connected successfully
		console.log("Connected successfully to the database");
		myDatabase = db.db('milab-studio'); //////////////////////
		
		// need PORT???
		app.listen(app.get('PORT'), () => {
			console.log('listening on port ${PORT}');
		});
		
		let collection = myDatabase.collection("musicCollection");
		//db.collection.createIndex({"name": -1})
		//db.collection.createIndex({"artist": -1})
		//db.collection.createIndex({"album": -1})
	}
});


/***********************************************************
CRUD == CREATE(post), READ(get), UPDATE(put), DELETE(delete) 
************************************************************/

//READ(get)
app.get('/READ', (req, res) => {
	
	collection.find({[req.params.key] : req.params.value}).toArray((err, result) => {
			if(err){
				console.log('err while searching in DB');
				return;
			}
			console.log(`Found ${result.length} records that matched the query`);
			if(!result[0]){
				res.send("Did not find any data");
			}
			
			res.send(JSON.stringify(result[0]));
		});
});

//CREATE(post)
app.post('/CREATE', (req, res) => {
	if (!req.body.name || !req.body.artist || !req.body.album) {
		console.log('Invalid input');
	}

	let song = {
		name: req.body.name,
		artist: req.body.artist,
		album: req.body.album
	};
	
    collection.insert([song], function(err) {
        if (err) {
			console.log('The item to be inserted is not valid!');
			return;
        }

		res.send('Item was added successfully');
    });
});

//UPDATE(put)
app.put('/UPDATE', (req, res) => {
	let item = req.body;
	
	if (!item){
		console.log('The item to be updated is not valid!');
		return;
	}
	
	let song = {
		name: req.body.name,
		artist: req.body.artist,
		album: req.body.album
	};
	
	collection.updateOne({_id: ObjectId(req.body.id)}, {$set: song}, function (err) {
		if(err){
			console.log('Cannot update item');
			return;
		}

		console.log('Item was successfully updated in the collection.');
		res.send('item updated');
	});
});

//DELETE(delete)
app.delete('/DELETE', (req, res) => {
	collection.deleteOne({_id: ObjectId(req.query.id) }, function(err) {
		if (err) {
            console.log('This song could not be deleted');
			return;
		}
		//else
		res.send("${id} was deleted.");
	});
});
