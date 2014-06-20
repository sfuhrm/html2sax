/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.htmltosax.parser.dfa;

/**
 * A transition from one state to another, fired on a certain
 * character input.
 * 
 * Special transitions:
 * <ul>
 * <li> the transition with the <code>null</code> characters is the default
 * transition to apply when no other transition applies.
 * <li> the transition with the empty String ("") is the EOF transition that
 * applies and end of file.
 * </ul>
 * 
 * @see Action the action to perform when fired
 * @see State the state that this transition comes from or leads to
 * @author Stephan Fuhrmann
 */
public final class Transition<T extends DFA> {
    /** The source state. */
    private final State fromState;
    /** One of the strings characters needs to match. */
    private final String chars;
    /** This state is switched to on acceptance. */
    private final State toState;

	/** The actions to perform on firing. */
	private final Action<T> actions[];
	
	/** The action that does nothing. */
	final static Action NullAction = new Action() {
		@Override
		public void fire(DFA dfa, Transition transition, StringBuilder in) throws Exception {
		}
	};
	
	/** The action that clears the buffer. */
	public final static Action ClearAction = new Action() {
		@Override
		public void fire(DFA dfa, Transition transition, StringBuilder in) throws Exception {
			in.setLength(0);
		}
	};
	
	/** Deletes the last char in the buffer. */
	public final static Action DeleteLastAction = new Action() {
		@Override
		public void fire(DFA dfa, Transition transition, StringBuilder in) throws Exception {
			in.setLength(in.length() - 1);
		}
	};
	
    public Transition(State from, String chars, State to, Action... actions) {
		if (from == null)
			throw new NullPointerException("from state is null");
        this.fromState = from;
		// chars may be null if it's the catch-all-transition
        this.chars = chars;
		if (to == null)
			throw new NullPointerException("to state is null");
        this.toState = to;
		if (actions == null)
			throw new NullPointerException("actions is null");
		this.actions = actions;
    }
	    
    public State from() {
        return fromState;
    }
    
    public State to() {
        return toState;
    }
    
	/** The chars this transition matches. Can be null if this
	 * is the default or catch-all-transition that applies when
	 * no other transition applies.
	 */
    public String chars() {
        return chars;
    }
    
    /** Fires the transition. All associated actions are executed.
     * Gets called when a character is accepted.
	 * @throws Exception when the processing gets an exception state and can't go on.
     */
    void fire(T dfa, StringBuilder cs) throws Exception {
		for (int i=0; i < actions.length; i++) {
			actions[i].fire(dfa, this, cs);
		}
    }
    
    @Override
    public String toString() {
        return from()+"->"+to()+
				chars() != null ? " \""+chars()+"\"" : " <<default>>";
    }
}
