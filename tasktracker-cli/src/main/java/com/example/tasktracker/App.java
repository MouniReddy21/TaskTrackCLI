package com.example.tasktracker;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if(args.length<1){
            System.out.println("No command provided");
            System.out.println("Usage: tasktracker-cli <command> [arguments]");
            return;
        }

        String command = args[0];
        // loading the existing tasks;
        TaskManager taskManager = new TaskManager();

        switch(command){
            case "add":
                if(args.length >= 2){
                    String taskName = String.join(" ",Arrays.copyOfRange(args, 1, args.length));
                    taskManager.addTask(new Task(taskName));
                    taskManager.saveTasks();
                }
                else{
                    System.out.println("Usage: tasktracker-cli add \"<task-name>\"");

                }
                break;
            case "update":
                if(args.length >= 3){
                    int id = Integer.parseInt(args[1]);
                    String updatedName = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                    taskManager.updateTask(id, updatedName);
                    taskManager.saveTasks();
                }
                else {
                    System.out.println("Usage: tasktracker-cli update \"<task-name>\"");
                }
                break;

            case "delete":
                if (args.length == 2) {
                    int id = Integer.parseInt(args[1]);
                    taskManager.deleteTask(id);
                    taskManager.saveTasks();
                    System.out.println("Task deleted successfully (ID: " + args[1] + ")");
                } else {
                    System.out.println("Usage: tasktracker-cli delete <id>");
                }
                break;
            
            case "mark-in-progress":
            case "mark-done":
                if (args.length == 2) {
                    int id = Integer.parseInt(args[1]);
                    StatusType newStatus = command.equals("mark-done") ? StatusType.DONE : StatusType.IN_PROGRESS;
                    taskManager.markTask(id, newStatus);
                    taskManager.saveTasks();
                } else {
                    System.out.println("Usage: tasktracker-cli " + command + " <id>");
                }
                break;
            
            case "list":
                 if (args.length < 2) {
                    taskManager.listAll();
                } else {
                    StatusType filterStatus;
                    try {
                        filterStatus = StatusType.valueOf(args[1].toUpperCase().replace("-", "_"));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status: " + args[1]);
                        return;
                    }
                    taskManager.listByStatus(filterStatus);
                }
                break;

            default:
                System.out.println("Unknown command: " + command);
                break;
        }



    }
}
