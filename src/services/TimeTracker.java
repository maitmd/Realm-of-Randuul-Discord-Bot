package services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;

public class TimeTracker implements Runnable {

    @Override
    public void run() {
        removeOldThreads();
    }
    
    private LocalDateTime getTime(){
        return LocalDateTime.now(ZoneId.systemDefault());
    }

    private void removeOldThreads(){
        for( ThreadChannel thread : DataHandler.getThreadsToRemove()){
            if(Math.abs(ChronoUnit.DAYS.between(getTime(), thread.getTimeCreated().atZoneSameInstant(ZoneId.systemDefault()))) > 3){
                thread.delete().complete();
            }
        }
    }
}
