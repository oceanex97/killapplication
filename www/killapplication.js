exports.test = function (p_message) {
	console.log("gonna call exec from js.");
    cordova.exec(function(p_success){
    	console.log('test success. Message : ', p_success);
    }, function(p_error) {
        console.log('test error', p_error);
    }, "Killapplication", "test", [p_message]);
};
exports.kill = function () {
    cordova.exec(function(p_success){
    	console.log('kill success.');
    }, function(p_error) {
        console.log('kill error', p_error);
    }, "Killapplication", "kill", []);
};