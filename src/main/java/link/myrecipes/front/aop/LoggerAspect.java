package link.myrecipes.front.aop;

import link.myrecipes.front.dto.logger.LoggerMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@ConditionalOnProperty("spring.rabbitmq")
@Slf4j
public class LoggerAspect {
    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing.call}")
    private String routingCall;

    @Value("${app.rabbitmq.routing.fail}")
    private String routingFail;

    @Value("${app.artifactId}")
    private String artifactId;

    private final RabbitTemplate rabbitTemplate;

    public LoggerAspect(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Before("execution(* link.myrecipes.front.controller..*.*(..))")
    public void controllerLogger(JoinPoint joinPoint) {
        LoggerMessage loggerMessage = LoggerMessage.builder()
                .logType("call")
                .system(artifactId)
                .method(joinPoint.getSignature().toShortString())
                .arguments(Arrays.toString(joinPoint.getArgs()))
                .registerDate(LocalDateTime.now().toString())
                .build();

        rabbitTemplate.convertAndSend(exchange, routingCall, loggerMessage);
    }

    @AfterThrowing(value = "execution(* link.myrecipes.front.controller..*.*(..))", throwing = "exception")
    public void controllerFailLogger(JoinPoint joinPoint, Exception exception) {
        LoggerMessage loggerMessage = LoggerMessage.builder()
                .logType("fail")
                .system(artifactId)
                .method(joinPoint.getSignature().toShortString())
                .arguments(Arrays.toString(joinPoint.getArgs()))
                .exception(exception.getClass().getSimpleName())
                .exceptionMessage(exception.getMessage())
                .registerDate(LocalDateTime.now().toString())
                .build();

        rabbitTemplate.convertAndSend(exchange, routingFail, loggerMessage);
    }
}
