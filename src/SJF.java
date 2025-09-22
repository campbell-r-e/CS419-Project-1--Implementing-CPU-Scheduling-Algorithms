
import java.util.*;

/**
 * TODO: Implement the non-preemptive SJF (Shortest-Job First) scheduling algorithm.
 */

public class SJF extends Algorithm {

    private final List<Process> readyProcesses = new ArrayList<>();

    private final Queue<Process> processesToArrive;

    private int now = 0;

    public SJF(List<Process> allProcessList){
        super(allProcessList);
        processesToArrive = new LinkedList<>(allProcessList);
    }

    @Override
    public void schedule() {
        System.out.println("Shortest Job First");

        while (!readyProcesses.isEmpty() || !processesToArrive.isEmpty()) {
            if (readyProcesses.isEmpty()) {
                Process process = processesToArrive.remove();
                if (now < process.getArrivalTime()) {
                    //advance the simulation clock to the next process's arrival time
                    now = process.getArrivalTime();
                }
                readyProcesses.add(process);
            }

            int smallest = -1;
            int smallestValue = Integer.MAX_VALUE;

            for(int i = 0; i < readyProcesses.size(); i ++){
                int burst = readyProcesses.get(i).getBurstTime();
                if(burst < smallestValue){
                    smallestValue = burst;
                    smallest = i;
                }
            }

            Process currentProcess = readyProcesses.remove(smallest);

            int runTime = currentProcess.getBurstTime();

            System.out.print("At time " + now + ": ");
            CPU.run(currentProcess, runTime);

            now += runTime;

            currentProcess.setRemainingTime(0);
            currentProcess.setFinishTime(now);

            while(!processesToArrive.isEmpty() &&
                    processesToArrive.peek().getArrivalTime()<=now) {
                readyProcesses.add(processesToArrive.remove());
            }
        }

    }
}
