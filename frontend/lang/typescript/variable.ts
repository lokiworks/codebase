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