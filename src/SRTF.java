import java.util.*;

/**
 * TODO: implement the SRTF (Shortest Remaining Time First) scheduling algorithm.
 *
 * SRTF is also known as preemptive SJF
 */

public class SRTF extends Algorithm{

    private final List<Process> readyProcesses = new ArrayList<>();

    private final Queue<Process> processesToArrive;

    private int now = 0;

    public SRTF(List<Process> allProcesses){
        super(allProcesses);
        processesToArrive = new LinkedList<>(allProcesses);
    }

    @Override
    public void schedule(){
        System.out.println("Shortest Remaining Time First");

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
                int burst = readyProcesses.get(i).getRemainingTime();
                if(burst < smallestValue){
                    smallestValue = burst;
                    smallest = i;
                }
            }

            Process currentProcess = readyProcesses.remove(smallest);

            int runTime;

            if(processesToArrive.isEmpty()){
                runTime = currentProcess.getRemainingTime();

                System.out.print("At time " + now + ": ");
                CPU.run(currentProcess, runTime);
            } else{
                Process nextProcess = processesToArrive.peek();

                runTime = Math.min(currentProcess.getRemainingTime(), nextProcess.getArrivalTime() - now);

                System.out.print("At time " + now + ": ");
                CPU.run(currentProcess, runTime);
            }

            now += runTime;

            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - runTime);
            if (currentProcess.getRemainingTime() == 0) {
                currentProcess.setFinishTime(now);
            }else {

                readyProcesses.add(currentProcess);
            }

            while(!processesToArrive.isEmpty() &&
                    processesToArrive.peek().getArrivalTime()<=now) {
                readyProcesses.add(processesToArrive.remove());
            }
        }
    }

}
