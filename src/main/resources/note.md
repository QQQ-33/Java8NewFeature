> 使用 **maven-shade-plugin** (主要用来打包可执行jar) 打jar包  会出现一个 *dependency-reduced-pom.xml* 
> 它的作用是标识出本项目的jar中包含的依赖，避免其他项目依赖本项目时重复引用依赖，可以使用来避免生成它：

```xml
<configuration>
      <createDependencyReducedPom>false</createDependencyReducedPom>
</configuration>
```