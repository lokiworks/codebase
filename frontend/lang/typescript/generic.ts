
// case 1
{
    function identity<Type>(arg: Type): Type {
        return arg;
    }

    // method 1
    {
        let output = identity<string>("mystring");
        console.log(output);
    }

    // method 2
    {
        let output = identity("mystring");
        console.log(output);
    }
}

// case 2
{
    function loggingIdentity<Type>(arg: Array<Type>): Array<Type> {
        console.log(arg.length);
        return arg;
    }

    {
        let output = loggingIdentity(["abc", "abcd"]);

    }
}

// case 3
{
    function identity3<Type>(arg: Type): Type {
        return arg;
    }
    let myIdentity: <Type>(arg: Type) => Type = identity3;
    console.log(myIdentity("123"));
}

// case 4
{
    interface GenericIdentifyFn<P={}>{
        (p :P):void;
    }

    let myIdentity : GenericIdentifyFn<{name:string}> = ({name})=>{
        console.log(name)
    };

    myIdentity({name:"defdefdefdefdefdef"});
}
