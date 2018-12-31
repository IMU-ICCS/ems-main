/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.segment;

import lombok.extern.slf4j.Slf4j;
import org.statefulj.fsm.FSM;
import org.statefulj.fsm.Persister;
import org.statefulj.fsm.RetryException;
import org.statefulj.fsm.model.Action;
import org.statefulj.fsm.model.State;
import org.statefulj.fsm.model.impl.StateImpl;
import org.statefulj.persistence.memory.MemoryPersisterImpl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class SegmentFSM {
    // ------------------------------------------------------------------------
    // Private fields

    @org.statefulj.persistence.annotations.State
    private String state;
    private FSM<SegmentFSM> fsm;

    private String initialState;
    private Map<String, State<SegmentFSM>> states;
    private List<String> events;
    private Map<Function<Object, Object>, Action<SegmentFSM>> actions;
    private List<TransitionSpec> transitions;

    // ------------------------------------------------------------------------
    // Public API part

    public SegmentFSM(List<TransitionSpec> specs, String initialState) {
        initialize("Segment FSM", specs, initialState);
    }

    public SegmentFSM(String name, List<TransitionSpec> specs, String initialState) {
        initialize(name, specs, initialState);
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String s) {
        initialState = s;
    }

    public String getState() {
        return state;
    }

    public State<SegmentFSM> onEvent(String event, Object... args) {
        String fromState = getState();
        String toState = null;
        try {
            return fsm.onEvent(this, event, args);
        } catch (Exception ex) {
            toState = "EXCEPTION THROWN";
            throw new RuntimeException(ex);
        } finally {
            //log.debug("On {} transition from {} to {}", event, fromState, getState());
            log.debug("{} --[ {} ]--> {}", fromState, event, toState == null ? getState() : toState);
        }
    }

    // ------------------------------------------------------------------------
    // Private part

    private void initialize(String name, List<TransitionSpec> specs, String initialState) {
        checkInitState(specs, initialState);
        initStates(specs);
        initEvents(specs);
        initActions(specs);
        initTransitions(specs);
        Persister<SegmentFSM> persister = new MemoryPersisterImpl<SegmentFSM>(states.values(), states.get(initialState));
        fsm = new FSM<SegmentFSM>(name, persister);
    }

    private void checkInitState(List<TransitionSpec> specs, String initialState) {
        for (TransitionSpec ts : specs) {
            if (ts.fromState.equals(initialState)) return;
        }
        throw new IllegalArgumentException("Initial state is not the source state in any transition: " + initialState);
    }

    private void initStates(List<TransitionSpec> specs) {
        // Collect state names from transition specs
        HashSet<String> stateNames = new HashSet<String>();
        for (TransitionSpec ts : specs) {
            stateNames.add(ts.fromState);
            stateNames.add(ts.toState);
        }
        log.trace("State-Names:\n{}", stateNames);

        // Initialize states
        states = stateNames.stream().collect(Collectors.toMap(o -> o, StateImpl::new));
        log.trace("States:\n{}", states);
    }

    private void initEvents(List<TransitionSpec> specs) {
        // Collect event names from transition specs
        Set<String> eventNames = specs.stream().map(ts -> ts.event).collect(Collectors.toSet());
        events = new LinkedList<>(eventNames);
        log.trace("Events:\n{}", events);
    }

    private void initActions(List<TransitionSpec> specs) {
        // Collect action names from transition specs
        Set<Function<Object, Object>> functions = specs.stream().filter(ts -> ts.action != null).map(ts -> ts.action).collect(Collectors.toSet());
        log.trace("Action-function-refs:\n{}", functions);
        // Initialize actions
        actions = new HashMap<>();
        for (Function<Object, Object> f : functions) {
            actions.put(f, new SegmentAction(f));
        }
        log.trace("Actions:\n{}", actions);
    }

    private void initTransitions(List<TransitionSpec> specs) {
        for (TransitionSpec ts : specs) {
            State<SegmentFSM> fromState = states.get(ts.fromState);
            State<SegmentFSM> toState = states.get(ts.toState);
            Action<SegmentFSM> action = (ts.action != null) ? actions.get(ts.action) : null;
            //
            if (action != null) fromState.addTransition(ts.event, toState, action);
            else fromState.addTransition(ts.event, toState);
        }
        transitions = specs;
        log.trace("Transitions:\n{}", transitions);
    }

    // ------------------------------------------------------------------------
    // Member classes

    public static class TransitionSpec {
        String fromState;
        String toState;
        String event;
        Function<Object, Object> action;

        public TransitionSpec(String f, String t, String e) {
            fromState = f;
            toState = t;
            event = e;
        }

        public TransitionSpec(String f, String t, String e, Function<Object, Object> a) {
            this(f, t, e);
            action = a;
        }

        public String toString() {
            return fromState + " -> " + toState + " on " + event + " calling " + action;
        }
    }

    private static class SegmentAction<SegmentFSM> implements Action<SegmentFSM> {
        private Function<Object, Object> function;

        public SegmentAction(Function<Object, Object> f) {
            function = f;
        }

        public void execute(SegmentFSM segmentFsm, String event, Object... args) throws RetryException {
            function.apply(args);
        }
    }
}