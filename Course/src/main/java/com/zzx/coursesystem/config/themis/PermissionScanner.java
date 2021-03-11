package com.zzx.coursesystem.config.themis;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.zzx.coursesystem.config.themis.annotation.Admin;
import com.zzx.coursesystem.config.themis.annotation.Login;
import com.zzx.coursesystem.config.themis.annotation.NoLimit;
import com.zzx.coursesystem.config.themis.annotation.Student;
import com.zzx.coursesystem.config.themis.annotation.Teacher;
import com.zzx.coursesystem.model.constant.UserType;

@Component
public class PermissionScanner {
    public Permission scan(Method method) {
        Admin annotation;
        if (getAnnotation(method, NoLimit.class) != null) {
            return new Permission(false);

        } else if (getAnnotation(method, Login.class) != null) {
            return new Permission(UserType.NO, 0);

        } else if (getAnnotation(method, Student.class) != null) {
            return new Permission(UserType.STUDENT);

        } else if (getAnnotation(method, Teacher.class) != null) {
            return new Permission(UserType.TEACHER);

        } else if ((annotation = getAnnotation(method, Admin.class)) != null) {
            return new Permission(UserType.ADMIN, annotation.value());
        }

        return new Permission(false);
    }

    private <T extends Annotation> T getAnnotation(Method method, Class<T> annotationClass) {
        T annotation = method.getDeclaredAnnotation(annotationClass);
        if (annotation == null) {
            annotation = method.getDeclaringClass().getDeclaredAnnotation(annotationClass);
        }

        return annotation;
    }
}
