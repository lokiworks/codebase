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

 ### custom ci config path
 ```
.gitlab-ci.yml (default)
.my-custom-file.yml
my/path/.gitlab-ci.yml
my/path/.my-custom-file.yml
 ```
 ### pipeline architectures
 * basic pipelines
 * directed acyclic graph pipelines (**needs** keyword)
 * child/parent pipelines (**trigger** keyword)
 
 .gitlab-ci.yml
 ```yml
 stages:
  - triggers

trigger_a:
  stage: triggers
  trigger:
    include: a/.gitlab-ci.yml
  rules:
    - changes:
        - a/*

trigger_b:
  stage: triggers
  trigger:
    include: b/.gitlab-ci.yml
  rules:
    - changes:
        - b/*
 ```
 a/.gitlab-ci.yml
 ```yml
 stages:
  - build
  - test
  - deploy

image: alpine

build_a:
  stage: build
  script:
    - echo "This job builds something."

test_a:
  stage: test
  needs: [build_a]
  script:
    - echo "This job tests something."

deploy_a:
  stage: deploy
  needs: [test_a]
  script:
    - echo "This job deploys something."
 ```

 ### pipeline reference
 



