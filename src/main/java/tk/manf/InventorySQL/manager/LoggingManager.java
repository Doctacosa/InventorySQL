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

import lombok.*;

import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class LoggingManager {
    private static final Logger logger = Bukkit.getLogger();
    @Setter
    private String prefix;
    @Setter
    private int level;

    private LoggingManager() {
    }

    public void d(String msg) {
        log(Level.DEBUG, msg);
    }

    public void d(Exception ex) {
        log(Level.DEBUG, ex);
    }

    public void logDeveloperMessage(String developer, DeveloperMessages msg) {
        log(Level.DEVELOPER_MESSAGE, "[Developer Message from " + developer + "] " + msg.getMessage());
    }

    public void log(Exception ex) {
        log(Level.ERROR, ex);
    }

    public void log(int level, String msg) {
        log(level, java.util.logging.Level.INFO, msg);
    }

    private void log(int level, java.util.logging.Level l, String msg) {
        if (this.level > level) {
            logger.log(l, "[{0}][DEBUG] {1}", new Object[]{prefix, msg});
        }
    }

    private void log(int level, Exception ex) {
        if (this.level > level) {
            logger.log(java.util.logging.Level.INFO, null, ex);
        }
    }
    
    public void setPrefix(String prefix) {
    	this.prefix = prefix;
    }
    
    public void setLevel(int level) {
    	this.level = level;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Level {
        /**
         * Totally Debug Level
         */
        public static final int DEBUG = 999;
        //public static final int NO = 899;
        //public static final int BODY = 799;
        //public static final int CARES = 699;
        //public static final int THE = 599;
        //public static final int DOCTOR = 499;
        //public static final int IS = 399;
        /**
         * Stuff for Addons
         */
        public static final int ADDONS = 299;
        public static final int DEVELOPER = 199;
        public static final int DEVELOPER_MESSAGE = 99;
        /**
         * Errors should be logged anyway
         */
        public static final int ERROR = 0;
    }

	public static enum DeveloperMessages
	{
		METRICS_OFF("You turned metrics off :(. Metrics is a good way to support Developers! ( You should also consider donating :) )"),
		METRICS_LOADED("Thanks for supporting us and using Metrics :)"),
		DEPRECATED_CLASS("Sorry, but the loaded class is deprecated"),
		HANDLING_BROKEN("You did not specify proper a proper Handler. Please specify a correct Classname for the missing Handler");
		
		private String message;
		
		private DeveloperMessages(String message)
		{
			this.message = message;
		}
		
		public String getMessage()
		{
			return this.message;
		}
	}
	
    
    static class Herobrine {
        //Darkness
    }
    
	public static LoggingManager getInstance()
	{
		return instance;
	}
	
    @Getter
    private static final LoggingManager instance = new LoggingManager();
}
