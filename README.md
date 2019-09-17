# Performance Log

#### log the consumed time to execute a method

#### based on java 8, aspectj 1.7.4 and slf4j api 1.7.25

## Example

Mark a method need to be log

@Auditable(moduleName = "CampaignMessageService", methodName = "add")
public ResultStatus add(CampaignMessage cm) {

}


## Two ways to log the consumed time with spring framework

### 1, aspectj-autoproxy

An implement to log with @Aspect and @Around

package my.util.aop.performance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;


/**
 * log the performance to execute a method, just as an example
 * Created by eric on 4/23/19.
 */
@Aspect
public class ProfileAspect {

    @Around("execution(* com.usadata.leadservice.services..*.*(..)) && @annotation(my.util.performance.Auditable)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable{
       return AuditUtil.log(joinPoint);

    }
}

Then configure it in applicationContext-Serivce.xml below,

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd
                           http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.0.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd">

    <context:property-placeholder ignore-unresolvable="true" location="classpath*:/config.properties" />

    <context:component-scan base-package="com.usadata.leadservice.services"><!-- base-package separate with "," if more than one package -->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <aop:aspectj-autoproxy />

<bean id="performanceAspect" class="my.util.aop.performance.PerformanceAspect" />

.
.
.

</beans>


### 2, Use <aop:config> style, no need to add class, this is more flexible

    <aop:config>
        <aop:aspect ref="performanceAspect">
            <aop:pointcut expression="execution(* com.usadata.leadservice.services..*.*(..)) &amp;&amp; @annotation(my.util.performance.Auditable)" id="performanceointCuts" />
            <aop:around method="logPerformance" pointcut-ref="performanceointCuts" />
        </aop:aspect>

    </aop:config>


## Note:
he recommended usage pattern is to use either just the <aop:config> style, or just the AutoProxyCreator style. reference - https://docs.spring.io/spring/docs/2.0.x/reference/aop.html

## Build

Gradle build