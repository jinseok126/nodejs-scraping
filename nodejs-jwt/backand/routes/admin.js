const express = require('express');
const router = express.Router();

router.all(function(req, res, next) {
    console.log("1");
    next();
});

router.get('/', function(req, res, next) {
    res.write("Test");
    res.end();
});

router.get('/test', function(req, res, next) {
    res.write("Test2");
    res.end();
});

module.exports = router;