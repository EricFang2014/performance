package my.util.performance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


/**
 * log the performance to execute a method, just as an example
 * Created by eric on 4/23/19.
 */
@Aspect
public class ProfileAspect {

    @Around("execution(* my.util.*(..)) && @annotation(Auditable)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable{
        return AuditUtil.log(joinPoint);

    }
}
