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
            attributes: [[models.sequelize.fn("COUNT", "idx"), "count"]], 
            include: [{ model: models.Role, where: {roleName: 'user'},  }],
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
            }, secretObj.secret);

            // refresh token save
            models.User.update(
                { refreshToken: token.refreshToken },   // SET
                { where: user }                         // WEHRE
            );
        }
        
        res.json({ check: idCheck, token: token.accessToken });
    })();
})

router.get('/tokenCheck/:token', function(req, res, next) {

    const accessToken = req.params.token;
    let msg = "";
    jwt.verify(accessToken, secretObj.secret, function(err, decoded) {

        // 사용 불가능한 토큰일 경우
        if(err) {
            // 기간이 만료된 토큰일 경우 리프레쉬 토큰을 사용할 수 있는지 확인
            if(err.message === "jwt expired") {
                msg = "Token expiration";
            } else {
                msg = "Token not availble";
            }
        // 사용가능한 토큰일 경우
        } else {
            msg = "Token available";
        }
    });


    res.json({msg: msg});
    res.end();
});

module.exports = router;