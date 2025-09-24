import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * TODO: implement the RR (Round Robin) scheduling algorithm
 */
public class RR extends Algorithm{
     // The ready queue
    private final Queue<Process> readyQueue = new LinkedList<>();

    // Processes that have not yet arrived
    private final Queue<Process> processesToArrive;
     private int now = 0;

    public RR(List<Process> allProcesses){
        super(allProcesses);
        processesToArrive = new LinkedList<>(allProcesses);
    }

    @Override
    public void schedule(){
     //
     System.out.println("Round,Robin:");
     int quantum = 5;

        while (!readyQueue.isEmpty() || !processesToArrive.isEmpty()) {
            if (readyQueue.isEmpty()) {
                Process process = processesToArrive.remove();
                if (now < process.getArrivalTime()) {
                    //advance the simulation clock to the next process's arrival time
                    now = process.getArrivalTime();
                }
                readyQueue.add(process);
            }

            // Round robin scheduling, the next process to schedule is the one
            // at the head of the ready queue
            Process currentProcess = readyQueue.remove();

           
            int runTime = Math.min(currentProcess.getRemainingTime(), quantum);


            // Execute the selected process
            System.out.print("At time " + now + ": ");
            CPU.run(currentProcess, runTime);

            // Advance the simulation clock
            now += runTime;

            // If any processes have arrived by 'now', add them to ready queue
            while(!processesToArrive.isEmpty() &&
                    processesToArrive.peek().getArrivalTime()<=now){
                readyQueue.add(processesToArrive.remove());
            }

            // Mark this process as completed
            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - runTime);

            if (currentProcess.getRemainingTime() == 0) {
             currentProcess.setFinishTime(now);
            }else {
                
                 readyQueue.add(currentProcess);
            }



        }
    }
}
