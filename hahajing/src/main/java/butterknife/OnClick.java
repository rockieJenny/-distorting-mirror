package butterknife;

import butterknife.internal.ListenerClass;
import butterknife.internal.ListenerMethod;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@ListenerClass(method = {@ListenerMethod(name = "doClick", parameters = {"android.view.View"})}, setter = "setOnClickListener", targetType = "android.view.View", type = "butterknife.internal.DebouncingOnClickListener")
@Retention(RetentionPolicy.CLASS)
public @interface OnClick {
    int[] value() default {-1};
}
