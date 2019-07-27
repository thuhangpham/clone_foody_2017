'use strict'
var express = require('express');
var fs = require("fs");
//var shortid = require('shortid');
var router = express.Router();
var ConnectMysql = require('../database/db.js');
var query = require('../database/query.json');
// :cityid/:catetypeid/:categoryid/:typeid?/:districtid?/:streetid?
router.get('/',(req,res)=>{

    var cityid = req.query.cityid,
        catetypeid = req.query.catetypeid,
        categoryid = req.query.categoryid,
        typeid = req.query.typeid,
        districtid = req.query.districtid,
        streetid = req.query.streetid,
        offset = req.query.offset;
        if(typeid===undefined||typeid<0)
            typeid=null;
        if(districtid===undefined || districtid<0)
            districtid = null;     
        if(cityid===undefined|| cityid<0)
            cityid=null;
        if(catetypeid===undefined)
            catetypeid=null;
        if(offset===undefined)
            offset=0;
        if(streetid===undefined || streetid<0)
            streetid = null;
    var arr = [];
    console.log(typeof parseInt(offset,0));
    arr.push(cityid, catetypeid, categoryid, typeid, districtid,streetid, parseInt(offset,10));
    ConnectMysql.query(query.getItem,arr, (err, rows)=>{
        if(err)
         {
             res.json({status: false, msg: err});
         }
         else{
            //res.json({status: true, msg: rows});
            res.json(rows);
         }
    });
});
router.get('/images/res/:img',(req,res)=>{
    var option = {
        root: process.cwd()+"/images/res/"
    }
    res.sendFile(req.params.img,option);
});
router.get('/itemfood',(req,res)=>{
      var cityid = req.query.cityid,
        catetypeid = req.query.catetypeid,
        categoryid = req.query.categoryid,
        typeid = req.query.typeid,
        districtid = req.query.districtid,
        streetid = req.query.streetid,
        offset = req.query.offset;
        if(typeid===undefined||typeid<0)
            typeid=null;
        if(districtid===undefined || districtid<0)
            districtid = null;     
        if(cityid===undefined || cityid<0)
            cityid=null;
        if(catetypeid===undefined || catetypeid<0)
            catetypeid=null;
        if(streetid===undefined || streetid<0)
            streetid = null;
        if(offset===undefined)
            offset=0;
    var arr = [];
    arr.push(cityid, catetypeid, categoryid, typeid, districtid,streetid, parseInt(offset,10));
    ConnectMysql.query(query.itemFood,arr, (err, rows)=>{
        if(err)
         {
             res.json({status: false, msg: err});
         }
         else{
            //res.json({status: true, msg: rows});
            res.json(rows);
         }
    });
});
router.post('/',(req,res)=>{
    
    var name = req.body.address,
        address = req.body.name,
        totalreviews = req.body.totalreviews,
        districtid= req.body.districtid,
        avgrating = req.body.avgrating,
        categoryid = req.body.cateid,
        typeid = req.body.typeid,
        totalpictures= req.body.totalpictures,
        restaurantid=req.body.restaurantid,
        cityid = req.body.cityid,
        catetypeid=req.body.catetypeid,
        latitude = req.body.lat,
        streetid = req.body.streetid,
        longitude=req.body.lng;
    //console.log(req.body);
    var arr=[];
    var fname = fGetFilenameImage();
    console.log(latitude);
    saveIMG(fname,req.body.img);
    arr.push(name,address, 0,fname,districtid,0,categoryid,typeid,0,restaurantid,cityid,categoryid,latitude,longitude,fGetDateTime(),streetid);
    ConnectMysql.query(query.saveItem,arr, (err, rows, fields)=>{
        if(err)
         {
             res.json({status: false, msg: err});
         }
         else{
            res.json({status: true, msg: "Post restaurent successful!"});
         }
    });
});
router.get('/',(req,res)=>{

    var cityid = req.query.cityid,
        catetypeid = req.query.catetypeid,
        categoryid = req.query.categoryid,
        typeid = req.query.typeid,
        districtid = req.query.districtid,
        streetid = req.query.streetid,
        offset = req.query.offset;
        if(typeid===undefined||typeid<0)
            typeid=null;
        if(districtid===undefined || districtid<0)
            districtid = null;     
        if(cityid===undefined|| cityid<0)
            cityid=null;
        if(catetypeid===undefined)
            catetypeid=null;
        if(offset===undefined)
            offset=0;
        if(streetid===undefined || streetid<0)
            streetid = null;
    var arr = [];
    console.log(typeof parseInt(offset,0));
    arr.push(cityid, catetypeid, categoryid, typeid, districtid,streetid, parseInt(offset,10));
    ConnectMysql.query(query.getItem,arr, (err, rows)=>{
        if(err)
         {
             res.json({status: false, msg: err});
         }
         else{
            //res.json({status: true, msg: rows});
            res.json(rows);
         }
    });
});
router.get('/images/res/:img',(req,res)=>{
    var option = {
        root: process.cwd()+"/images/res/"
    }
    res.sendFile(req.params.img,option);
});
router.get('/itemfood',(req,res)=>{
      var cityid = req.query.cityid,
        catetypeid = req.query.catetypeid,
        categoryid = req.query.categoryid,
        typeid = req.query.typeid,
        districtid = req.query.districtid,
        streetid = req.query.streetid,
        offset = req.query.offset;
        if(typeid===undefined||typeid<0)
            typeid=null;
        if(districtid===undefined || districtid<0)
            districtid = null;     
        if(cityid===undefined || cityid<0)
            cityid=null;
        if(catetypeid===undefined || catetypeid<0)
            catetypeid=null;
        if(streetid===undefined || streetid<0)
            streetid = null;
        if(offset===undefined)
            offset=0;
    var arr = [];
    arr.push(cityid, catetypeid, categoryid, typeid, districtid,streetid, parseInt(offset,10));
    ConnectMysql.query(query.itemFood,arr, (err, rows)=>{
        if(err)
         {
             res.json({status: false, msg: err});
         }
         else{
            //res.json({status: true, msg: rows});
            res.json(rows);
         }
    });
});
router.get('/place/nearby',(req,res)=>{
      var city = req.query.city,
        catetypeid = req.query.catetypeid,
        categoryid = req.query.categoryid,
        typeid = req.query.typeid,
        district = req.query.district,
        street = req.query.street,
        offset = req.query.offset;
        if(typeid===undefined||typeid<0)
            typeid=null;
        if(catetypeid===undefined || catetypeid<0)
            catetypeid=null;
        if(offset===undefined)
            offset=0;
    var arr = [];
    arr.push(city, catetypeid, categoryid, typeid, district,street, parseInt(offset,10));
    ConnectMysql.query(query.getItemPlaceNearBy,arr, (err, rows)=>{
        if(err)
         {
             res.json({status: false, msg: err});
         }
         else{
            //res.json({status: true, msg: rows});
            res.json(rows);
         }
    });
});
var fs = require("fs");
var arrayImage = new Array(); 
fs.readdir("images/res/", function(err, files) {
	if (err) {
		throw err;
	}
	files.forEach(function(f) {
		arrayImage.push("images/res/"+f);
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
function fGetFilenameImage(){
	var fname ="upload"+"_"+ fGetDateTime();
	return fname;
};
function saveIMG(filenameImage, img) {
	console.log("SERVER SAVED A NEW IMAGE");
	var filenameIMG = "images/res/fdi"+filenameImage + ".png";
	arrayImage.push(filenameIMG);
	fs.writeFile(filenameIMG, new Buffer(img, 'base64'));
	//filenameIMG.replace("\\","\\\\");
};
module.exports = router