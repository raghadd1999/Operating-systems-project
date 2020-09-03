/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_fifo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OS_FIFO {

    public static void debug(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here

        PrintWriter writer = new PrintWriter(new File("Jobs.txt"));
        PrintWriter outputWriter = new PrintWriter(new File("Results.txt"));
        int jobsCount = 300;
        Job[] jobs = new Job[jobsCount];
        List<Process> readyQueue = new ArrayList<>();
        boolean CPUIsAvailable = true;
        int CUT = 0;
        Random random = new Random(1000);
        int min = 16;
        int max = 512;

        // initialize the jobs array and create the jobs file.
        for (int i = 0; i < jobsCount; i++) {
            int JID = 1 + i;
            int ECU = random.nextInt(max - min + 1) + min;

            writer.append(JID + "\t" + ECU + "\r\n");
            writer.flush();

            jobs[i] = new Job(JID, ECU);
        }

        writer.close();

        for (int i = 0; i < jobsCount; i++) {
            Job job = jobs[i];
            Process process = new Process(job);
            process.setState(Process.STATE.READY);
            readyQueue.add(process);
        }

        double ratio;
        int[] stats = {0, 0, 0};

        while (readyQueue.size() > 0) {
            Process process = readyQueue.get(0);
            process.setState(Process.STATE.RUNNING);

            if (process.getCut() >= process.getEcu()) {
                process.setState(Process.STATE.TERMINATED);
                outputWriter.println(process.getPid() + "\t" + process.getCut() + "\t" + "NORMAL");
                readyQueue.remove(0);
                stats[1]++;
                continue;
            }

            // check the interuption.
            // check of normal termination.
            ratio = random.nextDouble();

            boolean inturrptUsed = false;
            if (ratio <= 0.10) {
                process.setState(Process.STATE.TERMINATED);
                outputWriter.println(process.getPid() + "\t" + process.getCut() + "\t" + "NORMAL");
                readyQueue.remove(0);
                inturrptUsed = true;
                stats[1]++;
                continue;
            }

            ratio = random.nextDouble();

            if (ratio <= 0.05 && !inturrptUsed) {
                process.setState(Process.STATE.TERMINATED);
                outputWriter.println(process.getPid() + "\t" + process.getCut() + "\t" + "ABNORMAL");
                readyQueue.remove(0);
                stats[2]++;
                continue;
            }

            process.increateCUT();
        }

        outputWriter.println("\n==============================================\n");
        outputWriter.println("Total Processes Count : " + (stats[1] + stats[2]));
        outputWriter.println("Total Normal Terminated Processes Count : " + stats[1]);
        outputWriter.println("Total Abnormal Terminated Processes Count : " + stats[2]);

        outputWriter.flush();
        outputWriter.close();
    }

}
