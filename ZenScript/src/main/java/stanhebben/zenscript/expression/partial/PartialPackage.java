/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression.partial;

import org.openzen.zencode.symbolic.expression.IPartialExpression;
import java.util.Collections;
import java.util.List;
import org.openzen.zencode.symbolic.scope.IScopeMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionInvalid;
import org.openzen.zencode.symbolic.symbols.IZenSymbol;
import stanhebben.zenscript.symbols.SymbolPackage;
import stanhebben.zenscript.type.ZenType;
import org.openzen.zencode.symbolic.method.IMethod;
import org.openzen.zencode.symbolic.unit.SymbolicFunction;
import org.openzen.zencode.util.CodePosition;

/**
 *
 * @author Stanneke
 */
public class PartialPackage implements IPartialExpression {
	private final CodePosition position;
	private final IScopeMethod environment;
	private final SymbolPackage contents;
	
	public PartialPackage(CodePosition position, IScopeMethod environment, SymbolPackage contents) {
		this.position = position;
		this.environment = environment;
		this.contents = contents;
	}

	@Override
	public Expression eval() {
		environment.error(position, "Cannot use package name as expression");
		return new ExpressionInvalid(position, environment);
	}

	@Override
	public Expression assign(CodePosition position, Expression other) {
		environment.error(position, "Cannot assign to a package");
		return new ExpressionInvalid(position, environment);
	}

	@Override
	public IPartialExpression getMember(CodePosition position, String name) {
		IZenSymbol member = contents.get(name);
		if (member == null) {
			environment.error(position, "No such member: " + name);
			return new ExpressionInvalid(position, environment);
		} else {
			return member.instance(position, environment);
		}
	}

	@Override
	public Expression call(CodePosition position, IMethod method, Expression[] arguments) {
		environment.error(position, "cannot call a package");
		return new ExpressionInvalid(position, environment);
	}

	@Override
	public IZenSymbol toSymbol() {
		return null; // not supposed to be used as symbol
	}

	@Override
	public ZenType getType() {
		return null;
	}

	@Override
	public ZenType toType(List<ZenType> types) {
		environment.error(position, "not a valid type");
		return environment.getTypes().ANY;
	}

	@Override
	public List<IMethod> getMethods() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public IPartialExpression via(SymbolicFunction function) {
		return this;
	}
}
