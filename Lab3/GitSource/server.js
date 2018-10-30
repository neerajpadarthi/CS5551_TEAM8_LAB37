var express = require('express')
var app = express();

const nodemailer = require ('nodemailer');
const xoauth2 =  require ('xoauth2') ;

var port = process.env.PORT || 5000;

app.use(express.static(__dirname + '/public'));

app.get('/', function(req, res) {
	res.render('index');
})

app.listen(port, function() {
	console.log('app running')
    console.log("Example app listening at http://:%s", port)
})




app.get('/getData', function (req, res) {
    var searchKeywords = req.query.searchkey;
    var searchKeywords1 = req.query.searchkey1;
    var searchKeywords2 = req.query.searchkey2;

    console.log("Param are "+searchKeywords);
    console.log("Param mes are "+searchKeywords1);
    console.log("Param mes are "+searchKeywords2);
    const transporter = nodemailer.createTransport({
        service: 'gmail',
        auth: {
            type: 'OAuth2',
            user: 's.pallavidesai@gmail.com',
            clientId: '319573095737-2c98cnr7fhjnurbi5es3h907klpd0hpb.apps.googleusercontent.com',
            clientSecret: '',
            refreshToken: '',
            accessToken: '',
        },
    });
    var mailoption = {

        from : 'Pallavi <s.pallavidesai@gmail.com>',
        to : searchKeywords,
        subject : searchKeywords2,
        text : searchKeywords1
    }

    transporter.sendMail(mailoption, function (err , res) {

        if(err)
        {
            console.log('error' );
        }
        else

        {
            console.log('mail sent' );
        }

    })
});