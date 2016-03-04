package com.modesteam.urutau.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;
import javax.persistence.EntityManager;

import com.modesteam.urutau.service.setting.system.SettingManagerSystem;

/**
 * Used like {@link Qualifier} for {@link EntityManager} 
 * in {@link SettingManagerSystem}
 */
@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface SystemManager {

}
