package dev.donutquine.editor.layout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;

/*
 * Adapted from:
 * https://github.com/logisim-evolution/logisim-evolution
 * https://gist.github.com/alanwhite/42502f20390baf879d093691ebb72066
 * https://medium.com/@alan.r.white/trackpad-gestures-on-macos-in-java-1fdef37f3d77
 *
 * To make this work ensure on jdk17.02+2 onwards
 * Compile with -XDignore.symbol.file --add-exports java.desktop/com.apple.eawt.event=ALL-UNNAMED
 * Run with --add-opens java.desktop/com.apple.eawt.event=ALL-UNNAMED
 *
 * This will also compile and run on platforms other than osx as it uses reflection to reach the
 * osx specific classes.
 */

public class GestureUtilities {
    private static final Logger LOGGER = LoggerFactory.getLogger(GestureUtilities.class);

    // Mimics com.apple.eawt.event.MagnificationListener
    @FunctionalInterface
    public interface MagnificationListener {
        void magnify(MagnificationEvent event);
    }

    // Mimics com.apple.eawt.event.RotationListener
    @FunctionalInterface
    public interface RotationListener {
        void rotate(RotationEvent event);
    }

    // Mimics com.apple.eawt.event.MagnificationEvent
    public static class MagnificationEvent {
        private final double amount;
        private final Object appleEvent;

        private MagnificationEvent(Object o) throws Exception {
            appleEvent = o;
            amount = (Double) appleEvent.getClass().getMethod("getMagnification").invoke(appleEvent);
        }

        public double getMagnification() {
            return amount;
        }

        public void consume() {
            try {
                appleEvent.getClass().getMethod("consume").invoke(appleEvent);
            } catch (Throwable t) {
                LOGGER.error("Error consuming apple event", t);
            }
        }
        // protected boolean isConsumed() { }
    }

    // Mimics com.apple.eawt.event.RotationEvent
    public static class RotationEvent {
        private final double amount;
        private final Object appleEvent;

        private RotationEvent(Object o) throws Exception {
            appleEvent = o;
            amount = (Double) appleEvent.getClass().getMethod("getRotation").invoke(appleEvent);
        }

        public double getRotation() {
            return amount;
        }

        public void consume() {
            try {
                appleEvent.getClass().getMethod("consume").invoke(appleEvent);
            } catch (Throwable t) {
                LOGGER.error("Error consuming apple event", t);
            }
        }
        // protected boolean isConsumed() { }
    }

    // Reflection Proxy for com.apple.eawt.event.MagnificationListener
    private static class MagnificationHandler implements InvocationHandler {
        private final MagnificationListener listener;

        private MagnificationHandler(MagnificationListener l) {
            listener = l;
        }

        public Object invoke(Object proxy, Method method, Object[] args) {
            if (method == null || !"magnify".equals(method.getName())) {
                LOGGER.error("Unexpected handler invocation: proxy={} method={}", proxy, method);
                return null;
            }
            if (args == null || args.length != 1) {
                LOGGER.error("Unexpected handler argumnets: method={} args={}", method, args != null ? "" + args.length : "null");
                return null;
            }

            Object event = args[0];
            // event should be com.apple.eawt.event.MagnificationEvent (or subclass?)
            // if (!(event instanceof com.apple.eawt.event.MagnificationEvent)) { ... }
            try {
                listener.magnify(new MagnificationEvent(event));
            } catch (Throwable t) {
                LOGGER.error("Error handling magnification event", t);
            }

            return null;
        }
    }

    // Reflection Proxy for com.apple.eawt.event.RotationListener
    private static class RotationHandler implements InvocationHandler {
        private final RotationListener listener;

        private RotationHandler(RotationListener l) {
            listener = l;
        }

        public Object invoke(Object proxy, Method method, Object[] args) {
            if (method == null || !"rotate".equals(method.getName())) {
                LOGGER.error("Unexpected handler invocation: proxy={} method={}", proxy, method);
                return null;
            }
            if (args == null || args.length != 1) {
                LOGGER.error("Unexpected handler arguments: method={} args={}", method, args != null ? "" + args.length : "null");
                return null;
            }

            Object event = args[0];
            // event should be com.apple.eawt.event.RotationEvent (or subclass?)
            // if (!(event instanceof com.apple.eawt.event.RotationEvent)) { ... }
            try {
                listener.rotate(new RotationEvent(event));
            } catch (Throwable t) {
                LOGGER.error("Error handling rotation event", t);
            }

            return null;
        }
    }

    // Accessing com.apple.eawt.event.GestureUtilities by reflection requires running with java option:
    //   --add-opens java.desktop/com.apple.eawt.event=ALL-UNNAMED
    private static Class<?> javax_swing_JComponent;
    private static Class<?> com_apple_eawt_event_GestureUtilities;
    private static Class<?> com_apple_eawt_event_GestureListener;
    private static Class<?> com_apple_eawt_event_MagnificationListener;
    private static Class<?> com_apple_eawt_event_RotationListener;
    private static boolean appleGestureSupported;

    static {
        try {
            initialize();
        } catch (Throwable t) {
            appleGestureSupported = false;
            LOGGER.error("Apple gesture support: failed to initialize: ", t);
        }
    }

    private static void initialize() throws Exception {
        String osname = System.getProperty("os.name").toLowerCase();
        if (!osname.startsWith("mac") && !osname.startsWith("darwin")) return;

        javax_swing_JComponent = Class.forName("javax.swing.JComponent");
        com_apple_eawt_event_GestureUtilities = Class.forName("com.apple.eawt.event.GestureUtilities");
        com_apple_eawt_event_MagnificationListener = Class.forName("com.apple.eawt.event.MagnificationListener");
        com_apple_eawt_event_RotationListener = Class.forName("com.apple.eawt.event.RotationListener");
        com_apple_eawt_event_GestureListener = Class.forName("com.apple.eawt.event.GestureListener");

        appleGestureSupported = true;
        LOGGER.info("Apple gesture support initialized");
    }

    private GestureUtilities() {
    }

    private static final Object lock = new Object();

    private static void reportError(Throwable t) {
        synchronized (lock) {
            if (!appleGestureSupported) {
                return;
            }

            appleGestureSupported = false;
            if (t instanceof IllegalAccessException) {
                String opt = "--add-opens=java.desktop/com.apple.eawt.event=ALL-UNNAMED";
                String jvmCmd = ProcessHandle.current().info().commandLine().orElse("error");
                if (!Arrays.asList(jvmCmd.split(" ")).contains(opt)) { // not perfect, but good enough
                    LOGGER.info("Note: To enable Apple gesture support, use JVM option '{}'.\n", opt);
                    return;
                }
            }

            LOGGER.error("Apple gesture support failed, will be disabled: ", t);
        }
    }

    public static void addMagnificationListenerTo(JComponent comp, MagnificationListener listener) {
        if (!appleGestureSupported) {
            return;
        }

        try {
            synchronized (lock) {
                //noinspection unchecked
                HashMap<MagnificationListener, Object> proxies = (HashMap<MagnificationListener, Object>) comp.getClientProperty("apple-gesture-magnification-listeners");
                if (proxies == null) {
                    proxies = new HashMap<>();
                    comp.putClientProperty("apple-gesture-magnification-listeners", proxies);
                }

                Object proxy = proxies.get(listener);
                if (proxy == null) {
                    proxy = Proxy.newProxyInstance(com_apple_eawt_event_MagnificationListener.getClassLoader(), new Class[]{com_apple_eawt_event_MagnificationListener}, new MagnificationHandler(listener));
                    proxies.put(listener, proxy);
                }

                com_apple_eawt_event_GestureUtilities.getMethod("addGestureListenerTo", javax_swing_JComponent, com_apple_eawt_event_GestureListener).invoke(null, comp, proxy);
            }
        } catch (Throwable t) {
            reportError(t);
        }
    }

    public static void removeMagnificationListenerFrom(JComponent comp, MagnificationListener listener) {
        if (!appleGestureSupported) return;
        try {
            synchronized (lock) {
                HashMap<MagnificationListener, Object> proxies;
                //noinspection unchecked
                proxies = (HashMap<MagnificationListener, Object>) comp.getClientProperty("apple-gesture-magnification-listeners");
                if (proxies == null) return;
                Object proxy = proxies.remove(listener);
                if (proxy == null) return;

                com_apple_eawt_event_GestureUtilities.getMethod("removeGestureListenerFrom", javax_swing_JComponent, com_apple_eawt_event_GestureListener).invoke(null, comp, proxy);
            }
        } catch (Throwable t) {
            reportError(t);
        }
    }

    public static void addRotationListenerTo(JComponent comp, RotationListener listener) {
        if (!appleGestureSupported) return;
        try {
            synchronized (lock) {
                //noinspection unchecked
                HashMap<RotationListener, Object> proxies = (HashMap<RotationListener, Object>) comp.getClientProperty("apple-gesture-rotation-listeners");
                if (proxies == null) {
                    proxies = new HashMap<>();
                    comp.putClientProperty("apple-gesture-rotation-listeners", proxies);
                }

                Object proxy = proxies.get(listener);
                if (proxy == null) {
                    proxy = Proxy.newProxyInstance(com_apple_eawt_event_RotationListener.getClassLoader(), new Class[]{com_apple_eawt_event_RotationListener}, new RotationHandler(listener));
                    proxies.put(listener, proxy);
                }

                com_apple_eawt_event_GestureUtilities.getMethod("addGestureListenerTo", javax_swing_JComponent, com_apple_eawt_event_GestureListener).invoke(null, comp, proxy);
            }

        } catch (Throwable t) {
            reportError(t);
        }
    }

    public static void removeRotationListenerFrom(JComponent comp, RotationListener listener) {
        if (!appleGestureSupported) return;
        try {
            synchronized (lock) {
                HashMap<RotationListener, Object> proxies;
                //noinspection unchecked
                proxies = (HashMap<RotationListener, Object>) comp.getClientProperty("apple-gesture-rotation-listeners");
                if (proxies == null) return;
                Object proxy = proxies.remove(listener);
                if (proxy == null) return;

                com_apple_eawt_event_GestureUtilities.getMethod("removeGestureListenerFrom", javax_swing_JComponent, com_apple_eawt_event_GestureListener).invoke(null, comp, proxy);
            }
        } catch (Throwable t) {
            reportError(t);
        }
    }
}
