// 联合类型定义
function printId(id: number| string){
    console.log("Your ID is: " + id);
}

printId(101);
printId("202");
// error
//printId({myId: 22342});


