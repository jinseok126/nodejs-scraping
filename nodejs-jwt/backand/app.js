
const express = require('express');
const sequelize = require('./models/index').sequelize;
const cors = require('cors');

const indexRouter = require('./routes/index');
const userRouter = require('./routes/user');

const app = express();
sequelize.sync();

const corsOptions = {
    origin: 'http://127.0.0.1:8080'
}

app.use(cors(corsOptions));
app.use(express.json());     // body-parser

app.use('/', indexRouter);
app.use('/user', userRouter);

app.listen(3000, function(){
    console.log('Port Number: 3000');
});