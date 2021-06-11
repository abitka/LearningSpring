package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class IdProvider implements InitializingBean, DisposableBean, BeanPostProcessor {

    private final Logger logger = Logger.getLogger(IdProvider.class);

    public String providerId(Book book) {
        return this.hashCode() + "_" + book.hashCode();
    }

    private void initIdProvider() {
        logger.info("provider INIT");
    }

    private void destroyIdProvider() {
        logger.info("provider DESTROY");
    }

    private void defaultInit() {
        logger.info("default INIT in provider");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in provider");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("afterPropertiesSet provider");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("destroy provider");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessBeforeInitialization invoked by bean " + beanName);
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessAfterInitialization invoked by bean " + beanName);
        return null;
    }

    @PostConstruct
    public void postConstructIdProvider() {
        logger.info("postConstructIdProvider annotated method called");
    }

    @PreDestroy
    public void preDestroyIdProvider() {
        logger.info("preDestroyIdProvider annotated method called");
    }
}
