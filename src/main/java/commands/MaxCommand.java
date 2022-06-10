package commands;

import models.Department;
import models.StudentValue;

public class MaxCommand implements Command {
    @Override
    public StudentValue execute(Department department) {
        return department.max();
    }
}
