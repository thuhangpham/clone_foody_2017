var mysql = require('mysql');

var db_config = {
  host     : 'us-cdbr-iron-east-03.cleardb.net',
  user     : 'b09fc18801091a',
  password : 'a032192e',
  database : 'heroku_e27c914987aec2e',
  connectionLimit:1000
};
// var db_config = {
//   host     : '127.0.0.1',
//   user     : 'root',
//   database : 'foody',
//   connectionLimit:1000
// };

function handleDisconnect() {
    var connection;
    console.log('1. connecting to db:');
    connection = mysql.createPool(db_config); // Recreate the connection, since
													// the old one cannot be reused.
    connection.on('release', function (connection) {
        console.log('Connection %d released', connection.threadId);
    });
    connection.on('enqueue', function () {
        console.log('Waiting for available connection slot');
    });
    connection.on('error', function(err) {
        console.log('3. db error', err);
        if (err.code === 'PROTOCOL_CONNECTION_LOST') { 	// Connection to the MySQL server is usually
            handleDisconnect();                      	// lost due to either server restart, or a
        } else {                                      	// connnection idle timeout (the wait_timeout
            throw err;                                  // server variable configures this)
        }
    });
    return connection;
}

module.exports = new handleDisconnect();