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

import lombok.Getter;

import org.bukkit.command.CommandSender;

import tk.manf.InventorySQL.util.Language;

import java.text.MessageFormat;
import java.util.HashMap;

public class LanguageManager {
    private HashMap<String, MessageFormat> cache;

    private LanguageManager() {
        cache = new HashMap<String, MessageFormat>(Language.PHRASES);
    }

    public void sendMessage(CommandSender player, Language lang, Object... args) {
        String msg = getMessage(lang, args);
        if(msg == null || msg.equalsIgnoreCase("")) {
            return;
        }
        player.sendMessage(msg);
    }

    public String getMessage(Language lang, Object... args) {
        if (!cache.containsKey(lang.getId())) {
            cache.put(lang.getId(), new MessageFormat(lang.getPattern()));
        }
        return cache.get(lang.getId()).format(args);
    }

	public static LanguageManager getInstance()
	{
		return instance;
	}
	
    @Getter
    private static final LanguageManager instance = new LanguageManager();
}
