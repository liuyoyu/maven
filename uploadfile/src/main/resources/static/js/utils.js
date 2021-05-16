//判断浏览器大小方法
function getScreenSizeType() {
    //获取当前窗口的宽度
    var width = layui.$(window).width();
    var type = -1;
    if (width > 1200) {
        type = 3;   //大屏幕
    } else if (width > 992) {
        type = 2;   //中屏幕
    } else if (width > 768) {
        type = 1;   //小屏幕
    } else {
        type = 0;   //超小屏幕
    }
    return type;
}

//文本超出的部分用省略号
function lossInfo(text, length) {
    var len = text.length;
    if (len > length) {
        return text.substring(0, length) + "...";
    }
    return text;
}