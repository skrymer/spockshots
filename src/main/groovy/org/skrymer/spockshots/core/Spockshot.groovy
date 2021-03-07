package org.skrymer.spockshots.core

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Spockshot {
    String rootFolder() default "./src/test/java"
}