package seedu.commands;

import seedu.expenditure.Expenditure;
import seedu.expenditure.ExpenditureList;

public class CheckBudgetCommand extends Command {
    public static final String COMMAND_WORD = "check";
    public CheckBudgetCommand() {

    }

    @Override
    public CommandResult execute(ExpenditureList expenditures) {
        double budget = expenditures.getBudgetSet();
        double totalAmount = 0;
        double borrowedAmount = 0;
        double lentAmount = 0;
        for (Expenditure individualExpenditure : expenditures.getExpenditures()){
            if (individualExpenditure.getExpenditureType().equals("B")) {
                borrowedAmount += individualExpenditure.getValue();
            } else if (individualExpenditure.getExpenditureType().equals("L")) {
                lentAmount += individualExpenditure.getValue();
            } else if (!individualExpenditure.getPaidIcon().equals("[X]")) {
                totalAmount += individualExpenditure.getValue();
            }
        }
        return getCheckCommandResult(budget, totalAmount, borrowedAmount, lentAmount);
    }

    private static CommandResult getCheckCommandResult(double budget, double totalAmount,
                                                       double borrowedAmount, double lentAmount) {
        if (budget == 0) {
            return new CommandResult("Your current budget is set at 0, please use the 'set' command to set a budget.");
        } else if (budget >= totalAmount) {
            // Remaining budget available
            double difference = budget - totalAmount;
            return new CommandResult(String.format(
                    "You are $%.2f away from exceeding your budget of $%.2f." + "\n" +
                    "Your current spending stands at: $%.2f" + "\n" + "Sum of money borrowed: $%.2f"
                    + "\n" + "Sum of money lent: $%.2f",
                    difference, budget, totalAmount, borrowedAmount, lentAmount
            ));
        } else {
            double difference = totalAmount - budget;
            return new CommandResult(String.format(
                    "You have exceeded your budget of $%.2f by $%.2f! " + "\n" +
                    "Your current spending stands at: $%.2f " + "\n" + "Sum of money borrowed: $%.2f" + "\n"
                    + "Sum of money lent: $%.2f",
                    budget, difference, totalAmount, borrowedAmount, lentAmount
            ));
        }
    }
}
