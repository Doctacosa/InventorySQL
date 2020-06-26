package tk.manf.InventorySQL.manager;

import java.lang.reflect.Method;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
final class UpdateEvent {
    private final Class<? extends Event> event;
    private final EventExecutor exec;

    protected UpdateEvent(final Class<? extends Event> type, final Method m) {
        this(type, new EventExecutor() {
            public void execute(Listener listener, Event event) throws EventException {
                try {
                    if (listener instanceof UpdateEventManager) {
                        // Prevent illegal Argument Exception
                        if(m.getParameterTypes()[0] == event.getClass()) {
                             m.invoke(listener, event);
                        }
                    }
                } catch (Exception e) {
                    LoggingManager.getInstance().log(LoggingManager.Level.ERROR, "Error with Event: " + String.valueOf(listener) + " - EV: " + event == null ? "failed" : event.getEventName());
                    LoggingManager.getInstance().log(e);
                }
            }
        });
    }

    public void register(Listener listener, Plugin plugin) {
        LoggingManager.getInstance().d("Registering " + event.getName());
        manager.registerEvent(event, listener, EventPriority.NORMAL, exec, plugin, false);
    }
    @Setter
    private static PluginManager manager;

	public static void setManager(PluginManager pluginManager) {
		manager = pluginManager;
	}
}
