package my.util.performance;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by eric on 4/23/19.
 */
public class AuditUtil {
    private static final Logger logger = LoggerFactory.getLogger(AuditUtil.class);

    //@Around("execution(* my.util.*(..)) && @annotation(Auditable)")
    public static Object log(ProceedingJoinPoint joinPoint) throws Throwable{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try{
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.warn("Error occurs when try to log the method {} of class {}, message: {}",
                    joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName()
                    , throwable.getMessage());
            throw throwable;

        }finally {
            stopWatch.stop();
            logger.info("Method {} of {} consumed {} milliseconds",
                    joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName(),
                    stopWatch.getTime(TimeUnit.MILLISECONDS));

            /**
             * Log with Auditable arguments
             */
//            MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
//            Auditable auditable = methodSignature.getMethod().getAnnotation(Auditable.class);
//            if (auditable != null){
//                logger.info("Method {} of {} consumed {} milliseconds",
//                        auditable.methodName(), auditable.moduleName(),
//                        stopWatch.getTime(TimeUnit.MILLISECONDS));
//            }

        }

    }
}
