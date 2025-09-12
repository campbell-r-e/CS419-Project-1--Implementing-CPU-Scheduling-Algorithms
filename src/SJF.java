
import java.util.*;

/**
 * TODO: Implement the non-preemptive SJF (Shortest-Job First) scheduling algorithm.
 */

public class SJF extends Algorithm {

    private final List readyQueue = new LinkedList<>();

    private final Queue<Process> processesToArrive;

    private int now = 0;

    public SJF(List<Process> allProcessList){
        super(allProcessList);
        processesToArrive = new LinkedList<>(allProcessList);
    }

    @Override
    public void schedule() {
        System.out.println("Shortest Job First");

        while (!readyQueue.isEmpty() || !processesToArrive.isEmpty()) {
            if (readyQueue.isEmpty()) {
                Process process = processesToArrive.remove();
                if (now < process.getArrivalTime()) {
                    //advance the simulation clock to the next process's arrival time
                    now = process.getArrivalTime();
                }
                readyQueue.add(process);
            }

            Collections.sort(readyQueue);

            Process currentProcess = readyQueue.remove();

            int runTime = currentProcess.getBurstTime();

            System.out.print("At time " + now + ": ");
            CPU.run(currentProcess, runTime);

            now += runTime;

            currentProcess.setRemainingTime(0);
            currentProcess.setFinishTime(now);

            while(!processesToArrive.isEmpty() &&
                    processesToArrive.peek().getArrivalTime()<=now) {
                readyQueue.add(processesToArrive.remove());
            }
        }

    }
}
