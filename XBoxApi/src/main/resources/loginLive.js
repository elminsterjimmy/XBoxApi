var system = require('system');
var fs = require('fs');
var address;

var startTime = new Date();
var liveUrl = 'https://login.live.com/login.srf?wa=wsignin1.0&rpsnv=12&ct=1437466506&rver=6.2.6289.0&wp=MBI_SSL&wreply=https:%2F%2Faccount.xbox.com:443%2Fpassport%2FsetCookies.ashx%3Frru%3Dhttps%253a%252f%252faccount.xbox.com%252fen-US%252fAccount%252fSignin%253freturnUrl%253dhttp%25253a%25252f%25252fwww.xbox.com%25252fen-US%25252f%25253flc%25253d1033&lc=1033&id=292543&cbcxt=0';

function GetTime(dateM, datetype) {
  var s;
  var MinMilli = 1000 * 60;
  var HrMilli = MinMilli * 60;
  var DyMilli = HrMilli * 24;
  s = "";
  if (datetype == "d") {
    s += Math.round(Math.abs(dateM / DyMilli)) + "day";
  } else if (datetype == "h") {
    s += Math.round(Math.abs(dateM / HrMilli)) + "hour"
  } else if (datetype == "m") {
    s += Math.round(Math.abs(dateM / MinMilli)) + "min";
  } else {
    s += Math.round(Math.abs(dateM / 1000)) + "sec"
  }
  return (s);
}

function GetUseTime() {
  return GetTime((Date.parse(new Date()) - Date.parse(startTime)), "s");
}

function refresh() {
  phantom.exit(0);
}

var casper = require('casper').create({
  // clientScripts:['includes/jquery.min.js' ],
  verbose : true,
  logLevel : 'warning',
  pageSettings : {
    javascriptEnabled : true
  },
  viewportSize : {
    width : 1024,
    height : 768
  }
});
casper.options.waitTimeout = 10;

var live_userid = casper.cli.get(0);
var live_passwd = casper.cli.get(1);
var RequestVerificationToken = "";

casper.onError = function(msg) {
  this.echo("onError:" + msg);
};
casper.start(liveUrl, function(response) {
  // require('utils').dump(response);
  this.echo(GetUseTime())
  this.echo('Title:' + this.getTitle() + ' start:' + live_userid);
  this.fill('[name="f1"]', {
    'loginfmt' : live_userid,
    'passwd' : live_passwd
  }, false);
  //this.captureSelector('img/'+live_userid+'_live1.png', 'html');
  //this.captureSelector('img/'+live_userid+'_live2.png', 'form[name="f1"]');

});

casper.then(function() {
  this.click('input[id="idSIButton9"]');
  //this.echo('login...');
});

casper.then(function() {
  this.echo(GetUseTime());
  this.wait(2000, function() {
    this.echo(GetUseTime());

    var title = this.getTitle();
    this.echo('Title:' + title);
    if ('Home' == title) { // check login
      this.echo("login success");
      var cookies = [];
      for (var i in phantom.cookies) {
        cookies[cookies.length] = phantom.cookies[i]['name'] + '=' + phantom.cookies[i]['value'];
      }
      file = fs.open("cookie/" + live_userid + ".txt", 'a'); // store cookies
      file.write(cookies.join('&'));
      file.close();
    } else {
      this.echo("login fail!");
      this.capture('img/login/' + live_userid + '_fail' + new Date() + '.png');
      phantom.exit(1);
    }
  });
});

casper.then(function() {
  this.echo('End useTime:' + GetUseTime());
});
casper.run(refresh);