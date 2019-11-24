# myOwnMybatisFarmeWork
自己手写实现的mybatis框架

>1 从解析configuration文件
>>1-1 使用XMLConfigurationBuilder处理environments节点>datasource;settings;plugins;typeAliases,....
>>1-2 解析mappers节点>package 扫描 ；url,resource,class ; 使用XMLMapperBuilder处理mapper

>2 解析mapper文件
>>2-1 处理namespace,cache-ref,cache,parameterMap,resultMap,sql...
>>2-2 解析mappedStatement 使用XMLStatementBuilder处理<select>等节点
>>2-3 解析parameterType,resultType,
>>2-4 处理SqlSource 使用XMLScriptBuilder 
>>>2-4-1 使用MixedSqlNode 处理动态标签（<select>下的children 都视为动态标签），用一个List封装各种SqlNode，并对外提供apply(DynamicContext)方法
>>>>2-4-1-1 DynamicContext本质就是一个StringBuffer,一个map<String,Object> bindings封装入参对象,当<if>做逻辑判断时，需要外部传入参数做判断，即从该map中获取
>>>>2-4-1-2 当Node节点为文本时，使用TextSqlNode封装，直接将sql（包含${}）放入该对象，如果没有${}则使用StaticSqlNode
>>>>2-4-1-3 当node节点为元素Element时，使用各种NodeHandler处理 ifHandler where..choose
>>>2-4-2 当MixedSqlNode处理过程中如果有动态标签等，isDynamic 则为True SqlSource的实现则为DynamicSqlSource 否则为 RawSqlSource里面存放了 MixedSqlNode,2者区别是 DynamicSqlSource 每获取一次sql语句都需要根据参数重新处理，后者则只需要处理一次sql即可
>>2-5 MappedStatement 就是封装了 parameterType，resultType，以及SqlSource的对象
>>2-6 Configuration对象 包含了 MappedStatement
  
>3 执行sql语句过程
>>3-1 
    
