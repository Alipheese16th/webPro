// var str = '안녕00방가00좋은아침00좋은하루'; // 00 -> x 교체

// console.log('원본 str : '+str);
// var splitStr = str.split('00'); // ['안녕','방가','좋은아침','좋은하루']
// console.log('분할된 str : '+splitStr);
// var joinStr = splitStr.join('x');
// console.log('join된 str : '+joinStr);

function replaceAll(str, oldStr, newStr){
    // let splitStr = str.split(oldStr);
    // let joinStr  = splitStr.join(newStr);
    // return joinStr;
    return str.split(oldStr).join(newStr);
}

// var replaceStr = replaceAll('로 것이다. 얼음에 하였으며, 우리의 아니다.',' ','');
// console.log('결과 : '+replaceStr);