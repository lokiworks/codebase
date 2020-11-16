* api gateway selection
   * can aggregation(merge) multi backend service response by dashboard
   * can limit qps by dashboard
* openapi document platform
  * reference: https://github.com/YMFE/yapi
* design message center
    * **Message Center** 
        * manage message table
        * manage producer/consumer information(account、token、vhost、destination、group etc)
        * checked message status in the table regularly, if nack republish it
    * **Producer Side**
        * place message table with bussiness table in the same database
        * delivery message to messge center
    * **Consumer Side**
        * subscription message with spring cloud stream
        * if consume message succeed, send a messge to message center
    * **tips**: consumer side make conpensation using producer side api , not rely on the message center
* dynamic configure
* iam design
    * reference: keycloak
* function switcher design
       * reference: https://mvnrepository.com/artifact/com.alibaba.csp/spring-boot-starter-ahas-switch-client
* how to design for api multiple version 
* how to build monorepo 
   * reference:https://dl.acm.org/doi/fullHtml/10.1145/2854146
   * reference: https://github.blog/2020-01-17-bring-your-monorepo-down-to-size-with-sparse-checkout/
   * how to interaction with ci
   * how to protect key algorithm (permissions)
