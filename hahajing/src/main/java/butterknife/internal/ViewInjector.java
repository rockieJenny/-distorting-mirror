package butterknife.internal;

import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class ViewInjector {
    private final String className;
    private final String classPackage;
    private final Map<CollectionBinding, int[]> collectionBindings = new LinkedHashMap();
    private String parentInjector;
    private final String targetClass;
    private final Map<Integer, ViewInjection> viewIdMap = new LinkedHashMap();

    ViewInjector(String classPackage, String className, String targetClass) {
        this.classPackage = classPackage;
        this.className = className;
        this.targetClass = targetClass;
    }

    void addView(int id, ViewBinding binding) {
        getOrCreateViewInjection(id).addViewBinding(binding);
    }

    boolean addListener(int id, ListenerClass listener, ListenerMethod method, ListenerBinding binding) {
        ViewInjection viewInjection = getOrCreateViewInjection(id);
        if (viewInjection.hasListenerBinding(listener, method) && !"void".equals(method.returnType())) {
            return false;
        }
        viewInjection.addListenerBinding(listener, method, binding);
        return true;
    }

    void addCollection(int[] ids, CollectionBinding binding) {
        this.collectionBindings.put(binding, ids);
    }

    void setParentInjector(String parentInjector) {
        this.parentInjector = parentInjector;
    }

    ViewInjection getViewInjection(int id) {
        return (ViewInjection) this.viewIdMap.get(Integer.valueOf(id));
    }

    private ViewInjection getOrCreateViewInjection(int id) {
        ViewInjection viewId = (ViewInjection) this.viewIdMap.get(Integer.valueOf(id));
        if (viewId != null) {
            return viewId;
        }
        viewId = new ViewInjection(id);
        this.viewIdMap.put(Integer.valueOf(id), viewId);
        return viewId;
    }

    String getFqcn() {
        return this.classPackage + "." + this.className;
    }

    String brewJava() {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code from Butter Knife. Do not modify!\n");
        builder.append("package ").append(this.classPackage).append(";\n\n");
        builder.append("import android.view.View;\n");
        builder.append("import butterknife.ButterKnife.Finder;\n\n");
        builder.append("public class ").append(this.className).append(" {\n");
        emitInject(builder);
        builder.append('\n');
        emitReset(builder);
        builder.append("}\n");
        return builder.toString();
    }

    private void emitInject(StringBuilder builder) {
        builder.append("  public static void inject(Finder finder, final ").append(this.targetClass).append(" target, Object source) {\n");
        if (this.parentInjector != null) {
            builder.append("    ").append(this.parentInjector).append(".inject(finder, target, source);\n\n");
        }
        builder.append("    View view;\n");
        for (ViewInjection injection : this.viewIdMap.values()) {
            emitViewInjection(builder, injection);
        }
        for (Entry<CollectionBinding, int[]> entry : this.collectionBindings.entrySet()) {
            emitCollectionBinding(builder, (CollectionBinding) entry.getKey(), (int[]) entry.getValue());
        }
        builder.append("  }\n");
    }

    private void emitCollectionBinding(StringBuilder builder, CollectionBinding binding, int[] ids) {
        builder.append("    target.").append(binding.getName()).append(" = ");
        switch (binding.getKind()) {
            case ARRAY:
                builder.append("Finder.arrayOf(");
                break;
            case LIST:
                builder.append("Finder.listOf(");
                break;
            default:
                throw new IllegalStateException("Unknown kind: " + binding.getKind());
        }
        for (int i = 0; i < ids.length; i++) {
            if (i > 0) {
                builder.append(',');
            }
            builder.append("\n        ");
            emitCastIfNeeded(builder, binding.getType());
            if (binding.isRequired()) {
                builder.append("finder.findRequiredView(source, ").append(ids[i]).append(", \"").append(binding.getName()).append("\")");
            } else {
                builder.append("finder.findOptionalView(source, ").append(ids[i]).append(")");
            }
        }
        builder.append("\n    );");
    }

    private void emitViewInjection(StringBuilder builder, ViewInjection injection) {
        builder.append("    view = ");
        List<Binding> requiredBindings = injection.getRequiredBindings();
        if (requiredBindings.isEmpty()) {
            builder.append("finder.findOptionalView(source, ").append(injection.getId()).append(");\n");
        } else if (injection.getId() == -1) {
            builder.append("target;\n");
        } else {
            builder.append("finder.findRequiredView(source, ").append(injection.getId()).append(", \"");
            emitHumanDescription(builder, requiredBindings);
            builder.append("\");\n");
        }
        emitViewBindings(builder, injection);
        emitListenerBindings(builder, injection);
    }

    private void emitViewBindings(StringBuilder builder, ViewInjection injection) {
        Collection<ViewBinding> viewBindings = injection.getViewBindings();
        if (!viewBindings.isEmpty()) {
            for (ViewBinding viewBinding : viewBindings) {
                builder.append("    target.").append(viewBinding.getName()).append(" = ");
                emitCastIfNeeded(builder, viewBinding.getType());
                builder.append("view;\n");
            }
        }
    }

    private void emitListenerBindings(StringBuilder builder, ViewInjection injection) {
        Map<ListenerClass, Map<ListenerMethod, Set<ListenerBinding>>> bindings = injection.getListenerBindings();
        if (!bindings.isEmpty()) {
            String extraIndent = "";
            boolean needsNullChecked = injection.getRequiredBindings().isEmpty();
            if (needsNullChecked) {
                builder.append("    if (view != null) {\n");
                extraIndent = "  ";
            }
            for (Entry<ListenerClass, Map<ListenerMethod, Set<ListenerBinding>>> e : bindings.entrySet()) {
                int i;
                ListenerClass listener = (ListenerClass) e.getKey();
                Map<ListenerMethod, Set<ListenerBinding>> methodBindings = (Map) e.getValue();
                boolean needsCast = !"android.view.View".equals(listener.targetType());
                builder.append(extraIndent).append("    ");
                if (needsCast) {
                    builder.append("((").append(listener.targetType());
                    if (listener.genericArguments() > 0) {
                        builder.append('<');
                        for (i = 0; i < listener.genericArguments(); i++) {
                            if (i > 0) {
                                builder.append(", ");
                            }
                            builder.append('?');
                        }
                        builder.append('>');
                    }
                    builder.append(") ");
                }
                builder.append(Promotion.ACTION_VIEW);
                if (needsCast) {
                    builder.append(')');
                }
                builder.append('.').append(listener.setter()).append("(\n");
                builder.append(extraIndent).append("      new ").append(listener.type()).append("() {\n");
                for (ListenerMethod method : getListenerMethods(listener)) {
                    builder.append(extraIndent).append("        @Override public ").append(method.returnType()).append(' ').append(method.name()).append("(\n");
                    String[] parameterTypes = method.parameters();
                    int count = parameterTypes.length;
                    for (i = 0; i < count; i++) {
                        builder.append(extraIndent).append("          ").append(parameterTypes[i]).append(" p").append(i);
                        if (i < count - 1) {
                            builder.append(',');
                        }
                        builder.append('\n');
                    }
                    builder.append(extraIndent).append("        ) {\n");
                    builder.append(extraIndent).append("          ");
                    boolean hasReturnType = !"void".equals(method.returnType());
                    if (hasReturnType) {
                        builder.append("return ");
                    }
                    if (methodBindings.containsKey(method)) {
                        Iterator<ListenerBinding> iterator = ((Set) methodBindings.get(method)).iterator();
                        while (iterator.hasNext()) {
                            ListenerBinding binding = (ListenerBinding) iterator.next();
                            builder.append("target.").append(binding.getName()).append('(');
                            List<Parameter> parameters = binding.getParameters();
                            String[] listenerParameters = method.parameters();
                            count = parameters.size();
                            for (i = 0; i < count; i++) {
                                Parameter parameter = (Parameter) parameters.get(i);
                                int listenerPosition = parameter.getListenerPosition();
                                emitCastIfNeeded(builder, listenerParameters[listenerPosition], parameter.getType());
                                builder.append('p').append(listenerPosition);
                                if (i < count - 1) {
                                    builder.append(", ");
                                }
                            }
                            builder.append(");");
                            if (iterator.hasNext()) {
                                builder.append("\n").append("          ");
                            }
                        }
                    } else if (hasReturnType) {
                        builder.append(method.defaultReturn()).append(';');
                    }
                    builder.append('\n');
                    builder.append(extraIndent).append("        }\n");
                }
                builder.append(extraIndent).append("      });\n");
            }
            if (needsNullChecked) {
                builder.append("    }\n");
            }
        }
    }

    static List<ListenerMethod> getListenerMethods(ListenerClass listener) {
        if (listener.method().length == 1) {
            return Arrays.asList(listener.method());
        }
        try {
            List<ListenerMethod> methods = new ArrayList();
            Class<? extends Enum<?>> callbacks = listener.callbacks();
            for (Enum<?> callbackMethod : (Enum[]) callbacks.getEnumConstants()) {
                ListenerMethod method = (ListenerMethod) callbacks.getField(callbackMethod.name()).getAnnotation(ListenerMethod.class);
                if (method == null) {
                    throw new IllegalStateException(String.format("@%s's %s.%s missing @%s annotation.", new Object[]{callbacks.getEnclosingClass().getSimpleName(), callbacks.getSimpleName(), callbackMethod.name(), ListenerMethod.class.getSimpleName()}));
                }
                methods.add(method);
            }
            return methods;
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    private void emitReset(StringBuilder builder) {
        builder.append("  public static void reset(").append(this.targetClass).append(" target) {\n");
        if (this.parentInjector != null) {
            builder.append("    ").append(this.parentInjector).append(".reset(target);\n\n");
        }
        for (ViewInjection injection : this.viewIdMap.values()) {
            for (ViewBinding viewBinding : injection.getViewBindings()) {
                builder.append("    target.").append(viewBinding.getName()).append(" = null;\n");
            }
        }
        for (CollectionBinding collectionBinding : this.collectionBindings.keySet()) {
            builder.append("    target.").append(collectionBinding.getName()).append(" = null;\n");
        }
        builder.append("  }\n");
    }

    static void emitCastIfNeeded(StringBuilder builder, String viewType) {
        emitCastIfNeeded(builder, "android.view.View", viewType);
    }

    static void emitCastIfNeeded(StringBuilder builder, String sourceType, String destinationType) {
        if (!sourceType.equals(destinationType)) {
            builder.append('(').append(destinationType).append(") ");
        }
    }

    static void emitHumanDescription(StringBuilder builder, List<Binding> bindings) {
        switch (bindings.size()) {
            case 1:
                builder.append(((Binding) bindings.get(0)).getDescription());
                return;
            case 2:
                builder.append(((Binding) bindings.get(0)).getDescription()).append(" and ").append(((Binding) bindings.get(1)).getDescription());
                return;
            default:
                int count = bindings.size();
                for (int i = 0; i < count; i++) {
                    Binding requiredField = (Binding) bindings.get(i);
                    if (i != 0) {
                        builder.append(", ");
                    }
                    if (i == count - 1) {
                        builder.append("and ");
                    }
                    builder.append(requiredField.getDescription());
                }
                return;
        }
    }
}
