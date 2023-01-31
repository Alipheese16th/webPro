

var arr = [];


for (var i = 0; i < 5; i++) {
    arr[i] = function () {
        return i;
    };
}

for (var i = 0; i < 5; i++) {
    arr[i] = function (index) {
        return function () {
            return index;
        };
    }(i);
}

for (var func of arr) {
    console.log(func());
}




