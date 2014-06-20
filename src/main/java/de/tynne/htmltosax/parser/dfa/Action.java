/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.htmltosax.parser.dfa;

/**
 * An action to be called in a transition.
 * @see Transition
 * @author fury
 */
public interface Action<T extends DFA> {
	public void fire(T dfa, Transition transition, StringBuilder in) throws Exception;
}
