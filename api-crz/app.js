'use strict'
var express = require('express');
var mysqlConnection = require('./database/db.js');
var logger = require('morgan');
var app = express();
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended:true,limit:'50mb'}));
app.use(bodyParser.json({limit: '50mb'}));

var connection = mysqlConnection;
var filenameIMG = null;

app.use(logger('dev'));
app.set('port', process.env.PORT || 3000);

var item = require('./routes/item.js');
var user = require('./routes/user.js');
app.use('/item',item);
app.use('/user', user);
app.get('/', (req,res)=>{
   res.send('Welcome to foody api.');
});
app.listen(app.get('port'),()=>{
   console.log('Sever listen on port '+ app.get('port')); 
});