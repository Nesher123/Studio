let prompts = require("readline").createInterface(process.stdin, process.stdout);

prompts.question('Please enter a word and press Enter: ', userInput => {
	console.log('Your results are:');
	console.log(userInput);
	
	for(let i = 0; i < userInput.length - 1; i++) {
		userInput = userInput.slice(-1) + userInput.slice(0, -1);
		console.log(userInput);
	}
	
	process.exit();
});
