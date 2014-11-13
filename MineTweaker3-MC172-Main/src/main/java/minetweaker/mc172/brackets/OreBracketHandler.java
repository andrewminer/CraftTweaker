/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.brackets;

import java.util.List;
import minetweaker.IBracketHandler;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.BracketHandler;
import static minetweaker.api.minecraft.MineTweakerMC.getOreDict;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.compiler.IScopeGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import org.openzen.zencode.symbolic.expression.IPartialExpression;
import zenscript.lexer.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
@BracketHandler
public class OreBracketHandler implements IBracketHandler {
	public static IOreDictEntry getOre(String name) {
		return getOreDict(name);
	}
	
	@Override
	public IZenSymbol resolve(IScopeGlobal environment, List<Token> tokens) {
		if (tokens.size() > 2) {
			if (tokens.get(0).getValue().equals("ore") && tokens.get(1).getValue().equals(":")) {
				return find(environment, tokens, 2, tokens.size());
			}
		}
		
		return null;
	}
	
	private IZenSymbol find(IScopeGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
		StringBuilder valueBuilder = new StringBuilder();
		for (int i = startIndex; i < endIndex; i++) {
			Token token = tokens.get(i);
			valueBuilder.append(token.getValue());
		}
		
		return new OreReferenceSymbol(environment, valueBuilder.toString());
	}
	
	private class OreReferenceSymbol implements IZenSymbol {
		private final IScopeGlobal environment;
		private final String name;
		
		public OreReferenceSymbol(IScopeGlobal environment, String name) {
			this.environment = environment;
			this.name = name;
		}
		
		@Override
		public IPartialExpression instance(ZenPosition position) {
			IJavaMethod method = MineTweakerAPI.getJavaMethod(
					OreBracketHandler.class,
					"getOre",
					String.class);
			
			return new ExpressionCallStatic(
					position,
					environment,
					method,
					new ExpressionString(position, name));
		}
	}
}
