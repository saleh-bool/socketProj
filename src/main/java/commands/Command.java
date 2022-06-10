package commands;

import models.Department;
import models.StudentValue;

public interface Command {
    StudentValue execute(Department department);
}
