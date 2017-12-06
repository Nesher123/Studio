let rotate = (string) => {
	console.log(string);
	
	for(let i = 0; i < string.length - 1; i++) {
		string = string.slice(-1) + string.slice(0, -1);
		console.log(string);
	}
}

rotate('milab');
