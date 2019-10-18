const express = require('express');
const jwt = require('jsonwebtoken');


const secretObj = require('../config/jwt');
const models = require("../models/index");

const router = express.Router();

// 아이디 체크
router.get('/idCheck/:id', function(req, res, next){

    (async () => {
        const idCheck = await models.User.findAndCountAll({
            where: {userId: req.params.id}
        }).then((results) => {
            return results.count;
        });

        res.json({result: idCheck});
        res.end();
    })();
})

router.post('/insert', function(req, res, next) {

    (async () => {
        const resultData = await models.User.create(req.body).then((result) => {
            return "success";
        }).catch((err) => {
            return "error";
        });

        console.log(resultData);

        res.send({result: resultData});
        res.end();
    })();
    
});

module.exports = router;