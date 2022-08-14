// case 1: Function type expressions
{
    function greeter(fn: (a: string)=>void){
        fn("hello, world");
    }

    function printConsole(s: string){
        console.log(s);
    }
    greeter(printConsole);
}
// (a: string)=> void means "a function with one parameter, named a, of type string, that doesn't have a return value".
// note: parameter name is required. The function type (string)=>void mean's a function with a parameter named string of type any

// case 2: type alias
{
    type GreetFunction = (a: string)=>void;
    function greeter2(fn: GreetFunction){
            fn("hello, world");
    }
}

// case 3: call signatures
{
    type DescribableFunction = {
        description: string;
        (someArg: number): boolean;
    }

    function doSomething(fn: DescribableFunction){
        console.log(fn.description + " return " + fn(6));
    }
}