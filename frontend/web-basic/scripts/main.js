// 切换图片
let myImage = document.querySelector('img');
myImage.onclick = function() {
    let mySrc = myImage.getAttribute('src');
    if(mySrc === 'images/firefox-icon.png') {
      myImage.setAttribute('src','images/firefox2.png');
    } else {
      myImage.setAttribute('src','images/firefox-icon.png');
    }
};

let myButton = document.querySelector('button');
let myHeading = document.querySelector('h1');

// 个性化欢迎信息设置
function setUserName() {
    let myName = prompt('请输入你的名字。');
    if(!myName || myName === null) {
    setUserName();
    }
    else {
    localStorage.setItem('name', myName);
    myHeading.innerHTML = 'Mozilla 酷毙了，' + myName;
    }
}

if(!)