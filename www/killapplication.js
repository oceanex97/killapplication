exports.test = function () {
    cordova.exec(function(){
    	console.log('test success');
    }, function(p_error) {
        console.log('test error');
    }, "Killapplication", "test", []);
};