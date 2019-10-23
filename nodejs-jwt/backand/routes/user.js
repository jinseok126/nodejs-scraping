const express = require('express');

const jwt = require('jsonwebtoken');

const secretObj = require('../config/jwt');
const models = require("../models/index");

const router = express.Router();

const middleware = require("../custom/middleware");

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
});

router.post('/insert', function(req, res, next) {

    (async () => {
        
        const roleIdx = await models.Role.findOne({
            where: {roleName: 'user'},
            attributes: ['idx']
        }).then((result) => {
            return result.dataValues.idx;
        })

        const user = req.body;
        user.roleIdx = roleIdx;
        
        const resultData = await models.User.create(user).then((result) => {
            return "success";
        }).catch((err) => {
            return "error";
        });

        res.send({msg: resultData});
        res.end();
    })(); // async
}); // router

// user login check router
router.post('/loginCheck', function(req, res, next) {

    const user = req.body;
    
    (async() => {
        
        // 등급이 user인 사용자만 필터
        const idCheck = await models.User.findOne({
            attributes: [[ models.sequelize.fn("COUNT", "idx"), "count" ]], 
            include: [{ model: models.Role, where: {roleName: 'user'} }],
            where: user
        }).then(result => {
            return result.dataValues.count;
        })

        const token = {
            accessToken: "",
            refreshToken: ""
        }

        // 아이디가 존재할 경우
        if(idCheck === 1) {

            // access token 발급
            token.accessToken = jwt.sign({
                userId: user.userId,
                roleName: 'user',
                exp: Math.floor(Date.now() / 1000),
            }, secretObj.secret);

            // refresh token 발급
            token.refreshToken = jwt.sign({
                userId: user.userId,
                roleName: 'user',
                exp: Math.floor(Date.now() / 1000) + (60*60),
                // exp: Math.floor(Date.now() / 1000),
            }, secretObj.secret);

            // refresh token save
            models.User.update(
                { refreshToken: token.refreshToken },   // SET
                { where: user }                         // WEHRE
            );
        }
        
        res.json({ check: idCheck, token: token.accessToken });
    })();
});

///////////////////////////////////////////////////
// 토큰 체크하는 middleware (interceptor)
router.use(function(req, res, next) {
    middleware.tokenCheck(req, res, next);
});
//////////////////////////////////////////////////
// 이 아래부터는 토큰 체크가 필수인 라우터

router.post('/test', function(req, res, next) {
    
    console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    // es.json({msg: "Token Available"});
    res.end();

}); // router


module.exports = router;