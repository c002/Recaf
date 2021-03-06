package me.coley.recaf.ui.component.editor;

import org.controlsfx.control.PropertySheet.Item;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import me.coley.event.Bus;
import me.coley.recaf.bytecode.analysis.Hierarchy;
import me.coley.recaf.bytecode.analysis.Hierarchy.LoadStatus;
import me.coley.recaf.config.impl.ConfASM;
import me.coley.recaf.event.MethodRenameEvent;
import me.coley.recaf.ui.component.ReflectiveMethodNodeItem;
import me.coley.recaf.util.Lang;

/**
 * Editor for method names, emits a rename event when the rename is applied.
 * 
 * @author Matt
 */
public class MethodNameEditor<T extends String> extends StagedCustomEditor<T> {
	public MethodNameEditor(Item item) {
		super(item);
	}

	@Override
	public Node getEditor() {
		ReflectiveMethodNodeItem refItem = (ReflectiveMethodNodeItem) item;
		ClassNode cn = refItem.getNode();
		MethodNode mn = (MethodNode) refItem.getOwner();
		TextField txtName = new TextField();
		txtName.setText(mn.name);
		ConfASM conf = ConfASM.instance();

		if (conf.useLinkedMethodRenaming()) {
			if (Hierarchy.getStatus() != LoadStatus.METHODS) {
				txtName.setDisable(true);
				txtName.setTooltip(new Tooltip(Lang.get("asm.edit.linkedmethods.notfinished")));
			} else if (conf.doLockLibraryMethod() && Hierarchy.INSTANCE.isLocked(cn.name, mn.name, mn.desc)) {
				txtName.setDisable(true);
				txtName.setTooltip(new Tooltip(Lang.get("asm.edit.locklibmethods.locked")));
			}
		}
		txtName.setOnAction(e -> rename(cn, mn, txtName));
		return txtName;
	}

	private void rename(ClassNode owner, MethodNode method, TextField txtName) {
		String text = txtName.getText();
		if (!txtName.isDisabled() && !text.equals(method.name)) {
			// use disable property to prevent-double send
			txtName.setDisable(true);
			// send update
			Bus.post(new MethodRenameEvent(owner, method, method.name, text));
		}
	}
}