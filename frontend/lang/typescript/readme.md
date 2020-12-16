## Modules
in typescript, any file containing a top-level **import** or **export** is considered a module
### 示例
StringValidator.ts
```typescript
export interface StringValidator {
  isAcceptable(s: string): boolean;
}
```
ZipCodeValidator.ts

```typescript
import { StringValidator } from "./StringValidator";

export const numberRegexp = /^[0-9]+$/;

export class ZipCodeValidator implements StringValidator {
  isAcceptable(s: string) {
    return s.length === 5 && numberRegexp.test(s);
  }
}
```