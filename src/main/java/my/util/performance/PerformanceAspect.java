package my.util.performance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

/**
 * log the performance
 * Created by eric on 9/17/19.
 */
@Aspect
public class PerformanceAspect {
    public Object logPerformance(ProceedingJoinPoint joinPoint) throws Throwable{
        return AuditUtil.log(joinPoint);
    }
}
