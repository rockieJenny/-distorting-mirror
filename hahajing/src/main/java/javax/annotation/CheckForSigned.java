package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifierNickname;
import javax.annotation.meta.When;

@TypeQualifierNickname
@Nonnegative(when = When.MAYBE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckForSigned {
}
