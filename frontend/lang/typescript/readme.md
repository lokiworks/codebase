# typescript


## 安装ts实验环境
* 安装ts及ts-node
```shell
# 全局安装ts
tyarn global add typescript
# 全局安装ts-node 
tyarn global add ts-node
```
* 实验文本工具:vscode
* 安装vscode插件: ```code runner```


## 调试antdesign代码

* Step 1: set a debug configration on /.vscode/launch.json,
```json
{
    // Use IntelliSense to learn about possible attributes.
    // Hover to view descriptions of existing attributes.
    // For more information, visit: https://go.microsoft.com/fwlink/?linkid=830387
    "version": "0.2.0",
    "configurations": [
        {
            "type": "pwa-chrome",
            "request": "launch",
            "name": "Launch Chrome against localhost",
            "url": "http://localhost:8080",
            "sourceMaps": true,
            "webRoot": "${workspaceFolder}"
        }
    ]
}
```

* step 2 : In vscode's terminal, run >npm start . The default browser will auto open http://localhost:8000

* step 3 : Open VSCode's debug panel, choose the "Launch Chrome For Debug" and click the Debug button. A chrome browser will be opened.