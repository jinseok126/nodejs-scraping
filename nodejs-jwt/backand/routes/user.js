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

        res.send({msg: resultData});
        res.end();
    })();
    
});

router.post('/loginCheck', function(req, res, next) {

    const user = req.body;

    (async() => {
        const idCheck = await models.User.findAndCountAll({
            where: user
        }).then(result => {

            return result.count;
        })

        let accessToken = "";
        if(idCheck === 1) {
            jwt.sign({
                userId: user.userId
            }, secretObj.secret, function(err, token) {
                if(!err) { 
                    accessToken = token;
                    console.log(accessToken);
                }
            });
        }
        
        res.json({check: idCheck, token: accessToken});
    })();
})

module.exports = router;