/*
Html2Sax - A HTML parser that creates SAX API calls
Copyright (C) 2008  Stephan Fuhrmann

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/
package de.sfuhrm.htmltosax.parser.dfa;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Deterministic Finite Automaton as a character input acceptor.
 * 
 * The kinds of objects this class interacts with are:
 * <ul>
 * <li> {@link State} is a state the automaton is in.
 * <li> {@link Transition} moves the DFA from one state to another after some event. This is usually a character seen.
 * <li> {@link Action} is an action executed when firing a transition.
 * </ul>
 * 
 * There are certain specialties you should note.
 * There's a certain initial state where everything begins
 * which is passed to the {@link #DFA(java.io.Reader, de.sfuhrm.htmltosax.parser.dfa.State) constructor}.
 * 
 * @author Stephan Fuhrmann
 */
public class DFA {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(DFA.class);

    /** The source of input. */
    private final Reader in;
    
	/** The current input reader line.
	 */
    private int line;
	
	/** The current input reader column.
	 */
    private int column;
	
	/** The state to start with. */
	private State initialState;

    protected DFA(Reader reader, State init) {
        if (reader == null)
            throw new NullPointerException("Reader is null");
		if (init == null)
            throw new NullPointerException("State is null");
        in = reader;
        line = 1;
        column = 1;
		initialState = init;
    }
	
    protected DFA(Reader reader, State init, Transition transitions[]) {
		this(reader, init);
		
        if (transitions == null)
            throw new NullPointerException("transitions is null");
        
        init(transitions);
        
        line = 1;
        column = 1;
    }
    
    public int line() {
        return line;
    }
    
    public int column() {
        return column;
    }
    
    protected final void init(Transition transitions[]) {
        Map<State, List<Transition>> fromMap = new HashMap<State, List<Transition>>();
        // first group by from state
        for (Transition trans : transitions) {
            List<Transition> list = fromMap.get(trans.from());
            if (list == null) {
                list = new ArrayList<Transition>();
                fromMap.put(trans.from(), list);
            }
            list.add(trans);
        }
        
        stateCharMaps = new HashMap<State, StateCharMap>();
        // second step: create statecharmaps
        for (Map.Entry<State, List<Transition>> entry : fromMap.entrySet()) {
            int hi = highestChar(entry.getValue());
            Transition ts[] = new Transition[hi + 1];
			Transition defaultTrans = null;
			Transition eofTrans = null;
            for (Transition t : entry.getValue()) {
				if (t.chars() == null) {
					defaultTrans = t;
					continue;
				}
				
				if (t.chars().isEmpty()) {
					eofTrans = t;
					continue;
				}
				
                for (int i=0; i < t.chars().length(); i++) {
                    Transition old = ts[t.chars().charAt(i)];
                    if (old != null) {
                        throw new IllegalStateException("Overwriting transition "+old);
                    }
                    ts[t.chars().charAt(i)] = t;
                }
            }
                
            StateCharMap scm = new StateCharMap(entry.getKey(), defaultTrans, eofTrans, ts);
            stateCharMaps.put(scm.state, scm);
        }
    }
    
    /** Gets the highest valued referenced character in a set of transitions. */
    private static char highestChar(Collection<Transition> trans) {
        char hi = 0;
        for (Transition t : trans) {
			if (t.chars() == null)
				continue;
            char cur = highestChar(t.chars());
            if (cur > hi)
                hi = cur;
        }
        return hi;
    }
    
    /** Gets the highest valued character in a string. */
    private static char highestChar(String s) {
        char hi = 0;
        for (int i=0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > hi)
                hi = c;
        }
        return hi;
    }

    /** For each state contains the character matching tables and the
     * state transitions. The key is the from-state, and the value
     * is the possible transitions going from there.
     */
    private Map<State, StateCharMap> stateCharMaps;

    public long byteSize() {
        long bs = 0;
        for (StateCharMap scm : stateCharMaps.values())
            bs += scm.byteSize();
        return bs;
    }
    
    /** A table for each character to match in one state.
     */
    static class StateCharMap {
		/** The state this map is for. */
        private final State state;
		/** Transition per character code.
		 */
        private final Transition transitions[];
		
		/** Transition to return if none else applies. */
		private final Transition defaultTransition;
		
		/** Transition to return if EOF applies. */
		private final Transition eofTransition;
        
        StateCharMap(State state, Transition defaultTransition, Transition eofTransition, Transition transitions[]) {
			if (state == null)
				throw new NullPointerException("state is null");
            this.state = state;
			this.defaultTransition = defaultTransition;
			this.eofTransition = eofTransition;
			if (transitions == null)
				throw new NullPointerException("transitions is null");
            this.transitions = transitions;
        }
        
        /** Gets the transition for the given character or
         * null if there is no transition for this character.
         */
        public Transition getFor(char c) {
			Transition result = defaultTransition;
            if (transitions.length > c) {
                Transition tableValue = transitions[c];
				if (tableValue != null)
					result = tableValue;
			}
			return result;
        }

		public Transition getEofTransition() {
			return eofTransition;
		}
        
        public long byteSize() {
            return transitions.length * 4;
        }
        
        public int size() {
            return transitions.length;
        }
    }
    
    protected void leftState(State s, StringBuilder sb) {
    }
    
    protected void enteredState(State s, StringBuilder sb) {
    }
    
    protected String pos() {
        return "l"+line()+"c"+column();
    }
	
	/** A state a transition could force us to. */
	private State forcedState;
    
	/** Forces the lexer into a different state. Can only be called
	 * from within a transition while parsing.
     * @param state the state to force the DFA into.
	 */
	protected void forceTo(State state) {
		this.forcedState = state;
	}
	
    /** Parses the data from the reader. 
     * Reads the reader until the end of file.
     * Appends every character seen to the
     * internal string builder first that is
     * passed on tho the 
     * {@link Transition#fire(de.sfuhrm.htmltosax.parser.dfa.DFA, java.lang.StringBuilder) transition}
     * then.
     * Using and updating the string builder is up to the transitions {@link Action} code.
     * The state can be brutally manipulated out-of-order using the
     * {@link #forceTo(de.sfuhrm.htmltosax.parser.dfa.State) forceTo()} method.
     * @throws IOException if there's a problem parsing the file. To keep things
     * simple, many exceptions are wrapped into an IOException. This is something
     * to change in the future.
     */
    public void parse() throws IOException {
        int intValue;
        State state = initialState;
        StateCharMap scm = stateCharMaps.get(state);
        final StringBuilder sb = new StringBuilder();
        enteredState(state, sb);
        
        while ((intValue = in.read()) != -1) {
            final char charValue = (char)intValue;
            
            if (charValue == '\n') {
                line++;
                column = 1;
            } else {
				column++;
			}
            
            final Transition transition = scm.getFor(charValue);
            sb.append(charValue);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("{}:{}, Char={}, Buffer: {}", new Object[]{pos(), state, charValue, sb});
			}
            if (transition != null) {
				try {
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("{}:{}, Match: {}, to: {}", new Object[]{pos(), state, charValue, transition.to()});
					}
					transition.fire(this, sb);
					if (transition.from() != transition.to()) {
						leftState(state, sb);
						enteredState(transition.to(), sb);
					}
					
					state = transition.to();
					{
						State myForced = forcedState;
						if (myForced != null) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("Forced to state {} when state {} would be the transitions choice", new Object[]{myForced, state});
							}
							state = myForced;
							forcedState = null;
						}
					}
					scm = stateCharMaps.get(state);
					if (scm == null) {
						throw new IllegalStateException("SCM for "+state+" not found");
					}
				} catch (Exception ex) {
					throw new IOException(ex);
				}
            }
        }
		
		if (scm.getEofTransition() != null) {
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("Executing EOF transition");
			try {
				scm.getEofTransition().fire(this, sb);
			} catch (Exception ex) {
				throw new IOException(ex);
			}
		}
    }
}
