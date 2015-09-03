package com.modesteam.urutau.annotation;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Vraptor works with methods that will call views, 
 * such as this methods don't have any differentiation of rest, 
 * this annotation have the purpose to inform.
 */
@Target({METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface View {
	
}
