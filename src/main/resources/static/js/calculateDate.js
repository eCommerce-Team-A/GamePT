function calculateDate(articleCreateDate, index) {
    var createDate = new Date(articleCreateDate);
    var nowDate = new Date();
    var difference = (nowDate - createDate) / 1000;
    //초
    if (difference < 60) {
        document.getElementById(index).innerHTML = parseInt(difference) + '초 전';
    }
    //분
    else if (difference < 3600 ) {
        document.getElementById(index).innerHTML = parseInt(difference / 60) + '분 전';
    }
    //시
    else if (difference < 86400 ) {
        document.getElementById(index).innerHTML = parseInt(difference / 3600) + '시간 전';
    }
    //일
    else if (difference < 31536000 ) {
        document.getElementById(index).innerHTML = parseInt(difference / 86400) + '일 전';
    }
    //년
    else {
        document.getElementById(index).innerHTML = parseInt(difference / 31536000) + '년 전';
    }
}