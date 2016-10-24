/*
 * MStorage - storage for notes.
 * 
 * Permission is granted to copy, distribute and/or
 * modify  this  document under  the  terms  of the
 * GNU General Public License
 * 
 * @author: ilya.gulevskiy
 * @email: mstorage.project@gmail.com
 * @date: 2016
 */
package mstorage.utils;

import java.nio.file.*;
import java.io.*;
import java.io.IOException;
import java.lang.reflect.*;

/**
 *
 * @author bass
 * @date Jan 16, 2016
 */
public class FileUtils {

	/**
	 * By default File#delete fails for non-empty directories, it works like
	 * "rm". We need something a little more brutal - this does the equivalent
	 * of "rm -r"
	 *
	 * @param path Root File Path
	 * @return true if the file and all sub files/directories have been removed
	 * @throws FileNotFoundException
	 */
	public static boolean deleteRecursive(File path) {
		if (!path.exists()) {
			return false;
		}

		boolean ret = true;
		if (path.isDirectory()) {
			for (File f : path.listFiles()) {
				ret = ret && FileUtils.deleteRecursive(f);
			}
		}
		return ret && path.delete();
	}

	public static Object cloneObject(Object obj) {
		try {
			Object clone = obj.getClass().newInstance();
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				field.set(clone, field.get(obj));
			}
			return clone;
		} catch (Exception e) {
			return null;
		}
	}

	public static Object deepCloneObject(Object obj) {
		try {
			Object clone = obj.getClass().newInstance();
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.get(obj) == null || Modifier.isFinal(field.getModifiers())) {
					continue;
				}
				if (field.getType().isPrimitive() || field.getType().equals(String.class)
						|| field.getType().getSuperclass().equals(Number.class)
						|| field.getType().equals(Boolean.class)) {
					field.set(clone, field.get(obj));
				} else {
					Object childObj = field.get(obj);
					if (childObj == obj) {
						field.set(clone, clone);
					} else {
						field.set(clone, cloneObject(field.get(obj)));
					}
				}
			}
			return clone;
		} catch (Exception e) {
			return null;
		}
	}
	
}
