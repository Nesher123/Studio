"use strict";

const express = require('express');
const app = express();
const MongoClient = require('mongodb').MongoClient;
const MONGO_URL = 'mongodb://localhost:5000/mongotest'
//'mongodb://Nesher:1333mlab@ds239217.mlab.com:39217/milab-studio'; not working properly...
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
		db.collection.createIndex({"name": 1})
		db.collection.createIndex({"artist": 1})
		db.collection.createIndex({"album": 1})
	}
});


/***********************************************************
CRUD == CREATE(post), READ(get), UPDATE(put), DELETE(delete) 
************************************************************/

//READ(get)
app.get('/READ', (req, res) => {
	if (req.query.name) {
		readSong(req.query.name, res);
	} else if (req.query.artist) {
		readArtist(req.query.artist, res);
	} else if (req.query.album) {
		readAlbum(req.query.album, res);
	} else {
		res.send("invalid input!");
	}
});

const readSong = (name, res) => {
	collection.findOne({"name": name}, function(err, song) {
		if(err || !song) {
			res.send("Cannot find a song named " + name);
		} else {
			printSongDetails(song, res);
			res.end();
		}
  });
};

const readArtist = (artist, res) => {
	collection.find({artist: artist}).toArray((err, results) => {
		if(err || !results[0]) {
			res.send("Cannot find an artist named " + artist);
		} else {
			res.send('These are the songs that were found: \n');
			results.forEach(song => printSongDetails(song, res));
			res.end();
		}
	});
};

const readAlbum = (album, res) => {
	collection.find({album: album}).toArray((err, results) => {
		if(err || !results[0]) {
			res.send("Cannot find an album named " + album);
		} else {
			res.send('These are the songs that were found: \n');
			results.forEach(song => printSongDetails(song, res));
			res.end();
		}
	});
};

/* get a song item and print its details into the given result */
const printSongDetails = (song, result) => {
	result.send(`
	ID: ${song._id}\n
	Name: ${song.name}\n
	Artist: ${song.artist}\n
	Album: ${song.album}\n\n
	`);
};
	
//CREATE(post)
app.post('/CREATE', (req, res) => {
	if (!req.body.name || !req.body.artist || !req.body.album) {
		console.log('Invalid input!');
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

		res.send('song was added successfully');
    });
});

//UPDATE(put)
app.put('/UPDATE', (req, res) => {
	if (!req.body){
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
		//else
		console.log('Item was successfully updated in the collection.');
		res.send('item updated');
	});
});

//DELETE(delete)
app.delete('/:id', (req, res) => {
	collection.deleteOne({_id: ObjectId(req.params.id)}, function(err) {
		if (err) {
            console.log('This song could not be deleted');
			return;
		}
		//else
		res.send("Deleted successfully.");
	});
});
