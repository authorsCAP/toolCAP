package org.tzi.use.examplePlugin.metamodel.schedule_constraint;

public interface ScheduleConstraintGenerator<T extends ScheduleConstraintInterface> {
    String generate(
        String contextClass,
        String invariantName,
        T ec
    );
}
