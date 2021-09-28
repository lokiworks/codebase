// 联合类型定义
{
    function printId(id: number | string) {
        console.log("Your ID is: " + id);
    }

    printId(101);
    printId("202");
}


// 可选参数
{
    function fun(x?: number) {
        console.log("fun " + x?.toString());

    }
    fun();
    fun(100);
}

// 模板字符串
const message = "hello world!";
console.log(`test ${message}`);

// infer 内省关键字

// 变量赋值
let msg = "hello there!";
let myName: string= "Alice";
console.log(myName);
//  可选属性 ?:
function printName(obj:{first: string;last?:string}){

    console.log(`test ${obj?.first} ${obj?.last}`);
}
printName({first:"loki"})
// 联合类型
{
    function printId(id: number|string){
        console.log("Your ID is: "+id);
    }

    printId(101);
}
// typeof
{
    function printId(id: number|string){
        if(typeof id === "string"){
            console.log(id.toUpperCase());
        }else{
            console.log("Your ID is: "+id);
        }
    }
    printId("hello, world");
}
// 类型别名
{
    type Point ={
        x: number;
        y: number;
    }
    function printCoord(pt:Point) {
        console.log(`x:${pt.x} y:${pt.y}`);
    
    }

    printCoord({x: 100, y:200})
}

// 接口
{
    interface Window{
        title: string
    }

}

// 类型断言
{
   // const myCanvas = document.getElementById("main_canvas") as HTMLCanvasElement;
   // const myCanvas = <HTMLCanvasElement>document.getElementById("main_canvas");

}



