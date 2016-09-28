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
package mstorage.models;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.*;
import javax.swing.tree.DefaultTreeModel;

import StorageCollection.*;

/**
 *
 */
public class StorageCollectionModel implements TreeModel {

	private Vector<TreeModelListener> treeModelListeners = new Vector<>();
	private Folder rootPerson;
	protected boolean ShowFiles = true;

	public StorageCollectionModel(Folder root, boolean showFiles) {
		this.rootPerson = root;
		this.ShowFiles = showFiles;
	}
	
	public Folder getFolder(){
		return this.rootPerson;
	}
	
	public void setFolder(Folder fold){
		this.rootPerson = fold;
	}

	/**
	 * Adds a listener for the TreeModelEvent posted after the tree changes.
	 */
	@Override
	public void addTreeModelListener(TreeModelListener l) {
		treeModelListeners.addElement(l);
	}

	/**
	 * Returns the child of parent at index index in the parent's child array.
	 */
	@Override
	public Object getChild(Object parent, int index) {
		StorageItem si = (StorageItem) parent;
		if (si.getType().equals("file") || si.getType().equals("image")) {
			Object f = new Object();
			return f;
		}

		Folder folder = (Folder) si;
		int cur_ind = 0;
		if (folder.Folders.size() > 0) {
			Set<Map.Entry<String, Folder>> folderSet = folder.Folders.entrySet();
			for (Map.Entry<String, Folder> f : folderSet) {
				if (index == cur_ind) {
					return f.getValue();
				}
				
				cur_ind++;
			}
		}

		if (true == this.ShowFiles && folder.Files.size() > 0) {
			Set<Map.Entry<String, File>> fileSet = folder.Files.entrySet();
			for (Map.Entry<String, File> f : fileSet) {
				if (index == cur_ind) {
					return f.getValue();
				}
				
				cur_ind++;
			}
		}

		Object f = new Object();
		return f;

	}

	/**
	 * Returns the number of children of parent.
	 */
	@Override
	public int getChildCount(Object parent) {
		if (!parent.getClass().getSimpleName().equals("Folder")) {
			return 0;
		}
		
		Folder folder = (Folder) parent;
		int cnt = folder.Folders.size();
		if (true == this.ShowFiles) cnt += folder.Files.size();
		return cnt;
	}

	/**
	 * Returns the index of child in parent.
	 */
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		StorageItem si = (StorageItem) parent;
		if (!si.getType().equals("folder")) {
			return -1;
		}
		
		Folder folder = (Folder) parent;
		StorageItem chi = (StorageItem) child;
		
		int cur_ind = 0;
		if (folder.Folders.size() > 0) {
			Set<Map.Entry<String, Folder>> folderSet = folder.Folders.entrySet();
			for (Map.Entry<String, Folder> f : folderSet) {
				if (f.getValue().getPath().equals(chi.getPath())) {
					return cur_ind;
				}
				
				cur_ind++;
			}
		}

		if (true == this.ShowFiles && folder.Files.size() > 0) {
			Set<Map.Entry<String, File>> fileSet = folder.Files.entrySet();
			for (Map.Entry<String, File> f : fileSet) {
				if (f.getValue().getPath().equals(chi.getPath())) {
					return cur_ind;
				}
				
				cur_ind++;
			}
		}
		
		return -1;
	}

	/**
	 * Returns the root of the tree.
	 */
	@Override
	public Object getRoot() {
		return rootPerson;
	}

	/**
	 * Returns true if node is a leaf.
	 */
	@Override
	public boolean isLeaf(Object node) {
		if (null == node) return true;
		
		String classname = node.getClass().getSimpleName();
		
		if (!classname.equals("Folder")) return true;
		
		
		return false;
	}

	/**
	 * Removes a listener previously added with addTreeModelListener().
	 */
	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		treeModelListeners.removeElement(l);
	}

	/**
	 * Messaged when the user has altered the value for the item identified by
	 * path to newValue. Not used by this model.
	 */
	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {

	}

}
