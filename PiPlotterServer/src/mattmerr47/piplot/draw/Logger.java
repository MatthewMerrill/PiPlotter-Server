package mattmerr47.piplot.draw;

import java.util.ArrayList;

public class Logger {
	
	/**
	 * @author Matthew Merrill
	 * DEBUG_FINE	tiny debugging details; 							e.g. Calculations for intervals
	 * DEBUG_ROUGH	fewer, more "" details; 							e.g. "Now moving to (7,47)"
	 * NORMAL 		regular features that should always be displayed; 	e.g. "Press Enter to Continue."
	 *
	 */
	public static enum LEVEL {
		DEBUG_FINE, DEBUG_ROUGH, NORMAL,
	};
	
	private static LEVEL displayLevel = LEVEL.NORMAL;
	
	private static ArrayList<String> depth = new ArrayList<String>();
	
	public static void setDisplayLevel(LEVEL displayLevel) {
		Logger.displayLevel = displayLevel;
	}
	
	public static LEVEL getDisplayLevel() {
		return displayLevel;
	}
	
	public static boolean shouldDisplay(LEVEL level) {
		
		switch (level) {
			case DEBUG_FINE : {
				return (getDisplayLevel() == LEVEL.DEBUG_FINE);} 
			case DEBUG_ROUGH: {
				return (getDisplayLevel() != LEVEL.NORMAL);}
			case NORMAL : {
				return true;}
			default: {
				return true;}
		}
		
	}
	
	public static void addDepthLayer(Object text) {
		
		print(LEVEL.NORMAL, "\n----- < " + text.toString() + "> -----");
		depth.add(text.toString());
	}
	public static void removeDepthLayer() {
		int i = depth.size() - 1;
		
		String text = depth.get(i);
		depth.remove(i);
		
		print(LEVEL.NORMAL, "----- </" + text.toString() + "> -----\n");
	}
	public static int getDepth() {
		return depth.size();
	}
	
	public static void print(LEVEL level, Object obj) {
		
		if (shouldDisplay(level)) {
			
			String text = "";
			for (int i = 0; i < depth.size(); i++) {
				text += "|";
			}
			text += obj.toString();
			
			System.out.println(text);
			
		}
		
	}
}
