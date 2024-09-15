package com.bootlabs.demo;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;

/**
 * Base composite annotation for jpa repository tests.
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = ZONKY)
public @interface PostgresDataJpaTest {

    @AliasFor(annotation = DataJpaTest.class)
    boolean showSql() default true;

    @AliasFor(annotation = DataJpaTest.class)
    boolean useDefaultFilters() default true;

    @AliasFor(annotation = DataJpaTest.class)
    Filter[] includeFilters() default {};

    @AliasFor(annotation = DataJpaTest.class)
    Filter[] excludeFilters() default {};

    @AliasFor(annotation = DataJpaTest.class)
    Class<?>[] excludeAutoConfiguration() default {};
}