# WiklGraph

# `背景`
#### 图谱学习之路，图谱的构建

 > ### 目录结构
 
 ```
 WiklGraph
 ├── wikl-cloud
       ├── wikl-eureka                      #服务注册与发现中心               -->端口：8761
 ├── wikl-common                            #公共模块
       ├── common-entity
       ├── common-util
       ├── common-neo4
       ├── common-orientdb
       ├── common-exception
 ├── wikl-component                         #系统组件                        -->端口：8768
 ├── wikl-janusgraph                        #JanusGraph模块                  -->端口：8766
 ├── wikl-java                              #Java模块                        -->端口：8767
 ├── wikl-neo4j                             #Neo4j模块                       -->端口：8082
         ├── neo4j-gremlin                                                   -->端口：8765
         ├── neo4j-jdbc                                                      -->端口：8764
 ├── wikl-orientdb                          #OrientDb模块    
         ├── orientdb-gremlin                                                -->端口：8763
         ├── orientdb-jdbc                                                   -->端口：8762
         ├── orientdb-spark                                                  -->端口：8769
 ├── wikl-portal                            #Portal模块                      -->端口：8081
 ├── wikl-elk
         ├── wikl-zipkin    8770  
```

# `图数据库 `

   > OrientDB  
 
   > Neo4j

# `涉及技术`

  - ### 后台

    > Apache TinkerPop
   
    > WebSocket
   
    > SpringCloud
   
    > Spring Cloud Data Flow
    
    > Elasticsearch
    
   - ### 前台
   
     > angular.js  
         
     > themleaf  

# `端口`

```
wikl-eureka : 8761
orientdb-jdbc: 8762
orientdb-gremlin: 8763
neo4j-jdbc: 8764
neo4j-gremlin: 8765
```

# `踩坑`

* OrientDB  

  * 已解决

    ######  [1. Missing session and token](https://community.orientdb.org/t/missing-session-and-token/299)
    ###### [2. Define SQL Result Data](https://community.orientdb.org/t/define-sql-result-data/442)
    ###### [3. Gremlin with OResultSet.close() exception](https://community.orientdb.org/t/gremlin-with-oresultset-close-exception/445)
    ###### [4. Can not delete edge](https://community.orientdb.org/t/can-not-delete-edge/88)
    ###### [5. Session expireTimeout](https://community.orientdb.org/t/session-expiretimeout/437)
    ###### [6. Exception In Query With Gremlin](https://discourse.orientdb.org/t/exception-in-query-with-gremlin/1033)
    ###### [7. UnsupportedOperationException In Match](https://discourse.orientdb.org/t/unsupportedoperationexception-in-match/1031)
    ###### [8. Create cluster error with console](https://discourse.orientdb.org/t/create-cluster-error-with-console/958)
 
   * 未解决
     ###### [1. Can not use the method of shortestPath()](https://community.orientdb.org/t/can-not-use-the-method-of-shortestpath/431)
     ###### [2. Pattern Matching’ difference between OrientDB and Gremlin](https://community.orientdb.org/t/pattern-matching-difference-between-orientdb-and-gremlin/458)
     ###### [4. Property is absent In Creating Index](https://discourse.orientdb.org/t/property-is-absent-in-creating-index/1034)
     ###### [5. Save String data error](https://discourse.orientdb.org/t/save-string-data-error/769)
     ###### [6. Letters in orientdb and gremlin](https://community.orientdb.org/t/letters-in-orientdb-and-gremlin/374)
