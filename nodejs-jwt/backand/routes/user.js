const express = require('express');

const jwt = require('jsonwebtoken');
const jwt_decode = require('jwt-decode');

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
                exp: Math.floor(Date.now() / 1000) + (30),
            }, secretObj.secret);

            // refresh token 발급
            token.refreshToken = jwt.sign({
                userId: user.userId,
                roleName: 'user',
                // exp: Math.floor(Date.now() / 1000) + (60*60),
                exp: Math.floor(Date.now() / 1000),
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
    const accessToken = req.headers.authorization;
    let msg = "";
    
    jwt.verify(accessToken, secretObj.secret, function(err, decoded) {
        (async () => {
            let afterAccessToken = null;

            // 사용 불가능한 토큰일 경우
            if(err) {
                // 기간이 만료된 토큰일 경우 리프레쉬 토큰을 사용할 수 있는지 확인
                if(err.message === "jwt expired") {
                    
                        // jwt decode
                        const tokenDecoded = jwt_decode(accessToken);
                        
                        const resultToken = await models.User.findOne({
                            attributes: ['refreshToken'],
                            include: [{model: models.Role, attributes: ['roleName']}],
                            where: {userId: tokenDecoded.userId}
                        }).then(result => {
                            return result;
                        });
                        
                        const refreshToken = resultToken.dataValues.refreshToken;   // refresh token
                        const roleName = resultToken.Role.dataValues.roleName;      // role name
                        
                        jwt.verify(refreshToken, secretObj.secret, function(err, decoded) {
                            // 사용가능한 refreshToken일 경우
                            if(!err) {
                                // accessToken 재발급
                                afterAccessToken = jwt.sign({
                                    userId: tokenDecoded.userId,
                                    roleName: roleName,
                                    exp: Math.floor(Date.now() / 1000),
                                }, secretObj.secret);
                                
                                console.log(afterAccessToken);
                                res.header("Authorization", afterAccessToken);
                                msg = "Token Available";
                            } else {
                                msg = "Token expiration";
                            } // if
                        }); // jwt.verify
                        
                } else {
                    msg = err.message;
                }
            // 사용가능한 토큰일 경우
            } else {
                msg = "Token Available";
            }
            
            if(msg === "Token Available") {
                next();
            } else {
                res.json({msg: msg});
                res.end();
            }
        })(); // async
    }); // jwt.verify
});
//////////////////////////////////////////////////
// 이 아래부터는 토큰 체크가 필수인 라우터

router.post('/test', function(req, res, next) {
    
    console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    // es.json({msg: "Token Available"});
    res.end();

}); // router


module.exports = router;