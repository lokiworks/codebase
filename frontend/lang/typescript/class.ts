class Point{
    x = 0;
    y = 0;
}

const pt = new Point();
console.log(pt);

class GoodGreeter{
    name:string;
    constructor(){
        this.name = "hello";
    }
}

let g = new GoodGreeter();
console.log(g.name);


class Box<Type> {
    contents: Type;
    constructor(value : Type) {
        this.contents = value;
    }
}

const b  = new Box("hello!!!");
console.log(b.contents);