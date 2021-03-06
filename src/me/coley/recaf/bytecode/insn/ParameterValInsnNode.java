package me.coley.recaf.bytecode.insn;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.ParameterNode;

import me.coley.recaf.bytecode.OpcodeUtil;

/**
 * Dummy opcode to be used in the stack-watcher window. This be used for
 * variables set by method arguments.
 * 
 * @author Matt
 */
public class ParameterValInsnNode extends InsnNode {
	public static final int PARAM_VAL = 302;
	/**
	 * Type of value.
	 */
	private final Type type;
	/**
	 * Parameter node of value. May be null.
	 */
	private ParameterNode parameter;

	public ParameterValInsnNode(Type type, ParameterNode parameter) {
		super(PARAM_VAL);
		this.type = type;
		this.parameter = parameter;
	}

	/**
	 * @return Value type of variable.
	 */
	public Type getValueType() {
		return type;
	}

	/**
	 * @return Parameter node of value. May be null.
	 */
	public ParameterNode getParameter() {
		return parameter;
	}

	@Override
	public int getType() {
		return OpcodeUtil.CUSTOM;
	}
}
