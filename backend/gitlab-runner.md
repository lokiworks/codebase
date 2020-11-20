* monorepo workflow: https://docs.gitlab.com/ee/user/packages/workflows/monorepo.html

## basic knowledge
> Multiple jobs in the same stage are executed in parallel

* **before_script**  install the dependencies for your app before running anything
* **job** define what to do. For example, jobs that compile or test code
* **stage** define when to do. For example, stages that run tests after stages that compile the code.
  
### Type of pipelines

### keywords
* Using only and except
 ```yaml
 job:on-schedule:
  only:
    - schedules
  script:
    - make world

job:
  except:
    - schedules
  script:
    - make build
 ```



