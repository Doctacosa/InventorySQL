/**
 * Copyright (c) 2013 Exo-Network
 *
 * This software is provided 'as-is', without any express or implied
 * warranty. In no event will the authors be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *    1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 *
 *    2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 *
 *    3. This notice may not be removed or altered from any source
 *    distribution.
 *
 * manf                   info@manf.tk
 */

package tk.manf.InventorySQL.manager;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Method;
import java.util.HashMap;

public final class UpdateEventManager implements Listener {
    private final HashMap<String, UpdateEvent> events;

    private UpdateEventManager() {
        events = new HashMap<String, UpdateEvent>(7);
        //I dont think its useful to save whenever we're joining
        //put("join", PlayerJoinEvent.class, "doGenericEvent");
        put("quit", PlayerQuitEvent.class, "doGenericEvent");
        put("changeworld", PlayerChangedWorldEvent.class, "doGenericEvent");
        put("respawn", PlayerRespawnEvent.class, "doGenericEvent");
        put("bedenter", PlayerBedEnterEvent.class, "doGenericEvent");
        put("bedleave", PlayerBedLeaveEvent.class, "doGenericEvent");
        put("death", PlayerDeathEvent.class, "doPlayerDeath", false);
    }

    public void initialise(Plugin plugin) {
        UpdateEvent.setManager(plugin.getServer().getPluginManager());
        for (String event : events.keySet()) {
            if (ConfigManager.getInstance().getUpdateEvents().contains(event)) {
                events.get(event).register(this, plugin);
            }
        }
    }

    private void put(String name, Class<? extends Event> event, String method) {
        put(name, event, method, true);
    }

    private void put(String name, Class<? extends Event> event, String method, boolean useSuper) {
        try {
            LoggingManager.getInstance().d("Handling " + event.getName() + " AS " + method + "(Using super: " + useSuper + ")");
            events.put(name, new UpdateEvent(event, getClass().getMethod(method, useSuper ? event.getSuperclass() : event)));
        } catch (Exception ex) {
            LoggingManager.getInstance().log(ex);
        }
    }

    public void doPlayerDeath(PlayerDeathEvent event) {
        doGenericEvent(event, event.getEntity());
    }

    public void doGenericEvent(PlayerEvent event) {
        doGenericEvent(event, event.getPlayer());
    }

    private void doGenericEvent(Event event, Player player) {
        LoggingManager.getInstance().d("on" + event.getEventName() + "(" + player.getUniqueId() + ")");
        DatabaseManager.getInstance().savePlayer(player);
    }

	public static UpdateEventManager getInstance()
	{
		return instance;
	}
	
    private static final UpdateEventManager instance = new UpdateEventManager();
}
