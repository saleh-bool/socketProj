package commands;

import models.Department;
import models.StudentValue;

public class MinCommand implements Command {
    @Override
    public StudentValue execute(Department department) {
        return department.min();
    }
}
