package commands;

import models.Department;
import models.StudentValue;

public class SortCommand implements Command{
    @Override
    public StudentValue execute(Department department) {
        return department.sort();
    }
}
