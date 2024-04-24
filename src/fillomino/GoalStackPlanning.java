package fillomino;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Action {
    String name;
    List<String> preconditions;
    List<String> effects;

    public Action(String name, List<String> preconditions, List<String> effects) {
        this.name = name;
        this.preconditions = preconditions;
        this.effects = effects;
    }

    public boolean isApplicable(List<String> state) {
        return state.containsAll(preconditions);
    }

    public List<String> apply(List<String> state) {
        if (isApplicable(state)) {
            state.addAll(effects);
            state.removeAll(preconditions);
        }
        return state;
    }
}

public class GoalStackPlanning {
    public static void main(String[] args) {
        // Define the initial state and the goal state
        List<String> initialState = new ArrayList<>();
        initialState.add("At(A, Home)");
        initialState.add("Clear(A)");
        initialState.add("On(A, Table)");
        initialState.add("At(B, Table)");
        initialState.add("Clear(B)");
        initialState.add("On(B, Table)");

        List<String> goalState = new ArrayList<>();
        goalState.add("At(A, Table)");
        goalState.add("Clear(A)");
        goalState.add("On(A, B)");

        // Define a set of available actions
        List<Action> actions = new ArrayList<>();
        actions.add(new Action("Move(A, X, Y)", List.of("At(A, X)", "Clear(A)", "On(A, Y)"), List.of("At(A, Y)", "Clear(A)", "On(A, X)")));
        actions.add(new Action("Move(A, X, Table)", List.of("At(A, X)", "Clear(A)", "On(A, Table)"), List.of("At(A, Table)", "Clear(A)", "On(A, X)")));
        actions.add(new Action("Move(A, Table, Y)", List.of("At(A, Table)", "Clear(A)", "On(A, Y)"), List.of("At(A, Y)", "Clear(A)", "On(A, Table)")));

        Stack<String> goalStack = new Stack<>();
        goalStack.addAll(goalState);

        List<String> currentWorldState = new ArrayList<>(initialState);

        // Plan execution
        while (!goalStack.isEmpty()) {
            String currentGoal = goalStack.peek();
            boolean foundAction = false;

            for (Action action : actions) {
                if (action.isApplicable(currentWorldState) && action.effects.contains(currentGoal)) {
                    goalStack.pop(); // Remove the goal from the stack
                    currentWorldState = action.apply(currentWorldState);
                    System.out.println("Executing Action: " + action.name);
                    foundAction = true;
                    break;
                }
            }

            if (!foundAction) {
                System.out.println("Failed to find a plan for the remaining goals.");
                break;
            }
        }

        System.out.println("Final World State: " + currentWorldState);
    }
}