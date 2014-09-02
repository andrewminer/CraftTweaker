/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package zenscript.parser.statement;

import zenscript.IZenErrorLogger;
import zenscript.lexer.ZenTokener;
import static zenscript.lexer.ZenTokener.*;
import zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public class ParsedStatementDefault extends ParsedStatement {
	public static ParsedStatementDefault parse(ZenTokener tokener, IZenErrorLogger errorLogger) {
		ZenPosition position = tokener.required(T_DEFAULT, "default expected").getPosition();
		tokener.required(T_COLON, ": expected");
		
		return new ParsedStatementDefault(position);
	}
	
	public ParsedStatementDefault(ZenPosition position) {
		super(position);
	}
}