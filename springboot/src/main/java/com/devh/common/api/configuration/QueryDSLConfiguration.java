package com.devh.common.api.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <pre>
 * Description :
 *     QueryDSL 사용을 위한 설정
 * ===============================================
 * Member fields :
 *     
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-10-21
 * </pre>
 */
@Configuration
@Slf4j
public class QueryDSLConfiguration {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        log.info("Setting up JPAQueryFactory...");
        return new JPAQueryFactory(entityManager);
    }

}
