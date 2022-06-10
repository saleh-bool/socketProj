package commands;

import models.Department;
import models.StudentValue;

public class AverageCommand implements Command {
    @Override
    public StudentValue execute(Department department) {
        return department.average();
    }
}
