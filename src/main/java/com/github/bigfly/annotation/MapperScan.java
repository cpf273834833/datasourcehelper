package com.github.bigfly.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author chengpengfei
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({MapperScannerRegistrar.class,MapperScannerRegistrar.class})
public @interface MapperScan {
}
