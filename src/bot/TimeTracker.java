package bot;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import net.dv8tion.jda.api.entities.Member;
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
        for( ThreadChannel thread : GensoRanduul.getThreadsToRemove()){
            if(ChronoUnit.DAYS.between(getTime(), thread.getTimeCreated()) > 1){
                for(Member mem : thread.getMembers()){
                    thread.removeThreadMember(mem);
                }
            }
        }
    }
}
