'use strict'
var express = require('express');
var router = express.Router();
var ConnectMysql = require('../database/db.js');
var query = require('../database/query.json');

router.get('/', (req,res)=>{
    ConnectMysql.query(query.getUser,(err,rows)=>{
        if(err)
          res.json({status: false, msg: err});
        else
         res.json(rows);
    });
});
router.get('/images/avatar/:img',(req,res)=>{
    var option = {
        root: process.cwd()+"/images/user/"
    }
    res.sendFile(req.params.img,option);
});
router.get('/review',(req,res)=>{
    ConnectMysql.query(query.getReviewByItem, [req.query.itemid], (err, rows)=>{
        if(err)
            res.json({status: false, message: err});
        else
            res.json(rows);
    });
});
router.post('/',(req,res)=>{
    var username = req.body.username;
    var name = req.body.name;
    var firstname = req.body.firstname;
    var lastname = req.body.lastname;
    var dob = req.body.dob;
    var password = req.body.pass;
    var email = req.body.email;
    var url = req.body.URL;
    var imageURL = req.body.imageURL;
    var gender = req.body.gender;
    var phone = req.body.phone;
    var relationship = req.body.relationship;
    var created = fGetDateTime();
    console.log(username);
    	ConnectMysql.query(query.checkUser,[email], function(err, rows, fields)
		{
            console.log(rows);
			if(err)
                res.json({status: false, id: 0});
			if(rows.length===0)
			{ 
				ConnectMysql.query(query.saveUser, [name,username,password, email,imageURL, gender, phone,created,  firstname, lastname,url, dob,relationship],(err, row, fields)=>{
                    if(err)
                        res.json({status: false, id:0});
                    else{
                        res.json({status: true,id: row.insertId});
                        //res.json({status: true, msg: rows});
                    }
                    
                });
			}
			else{  
					res.json({status: false, id:-1});	  
				}
		});    
});
router.get('/byid',(req,res)=>{
    ConnectMysql.query(query.getUserById,[req.query.id,],(err, rows)=>{
        if(err)
            res.json({status: false, id:0});
        else
            res.json(rows[0]);
    });
});
router.post('/login',(req,res)=>{
    var email = req.body.email;
	var pass = req.body.pass;    
    console.log(pass);
	ConnectMysql.query(query.countUser,[email,pass], function(err, rows, fields)
		{
			if(err)
                res.json({status: false, id: 0});
			if(rows.length===0)
				{ 
					res.json({status: false, id:0});	
					console.log("Username or password is incorrect!"); 
				}
			else{  
					res.json({status: true, id: rows[0].c});	  
				}
		});    
});
router.get('/update',(req,res)=>{
    var id = req.query.id;
    var username = req.query.username;
    var firstname = req.query.firstname;
    var lastname = req.query.lastname;
    console.log(username);
    ConnectMysql.query(query.updateUser, [username, firstname, lastname,id ],(err, rows, fields)=>{
        if(err)
            res.json({status: false, id: -1});
        else{
            res.json({status: true, id: 0});
        }
        
    });
});
function fGetDateTime(){ 
	var date = new Date();

	var hour = date.getHours();
	hour = (hour < 10 ? "0" : "") + hour;

	var min  = date.getMinutes();
	min = (min < 10 ? "0" : "") + min;
	var sec  = date.getSeconds();
	sec = (sec < 10 ? "0" : "") + sec;

	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	month = (month < 10 ? "0" : "") + month;
	var day  = date.getDate();
	day = (day < 10 ? "0" : "") + day;
	return year + month + day+hour + min + sec;
}; 
module.exports = router;