package Commands;

import net.dv8tion.jda.core.entities.MessageChannel;

public class RollDice {
	int diceValue;
	int diceAmount;
	int[] rolls;
	String message;
	
	public RollDice(MessageChannel channel, String content) {
		message = " ";
		try{
			diceAmount = Integer.parseInt(content.substring(content.indexOf(" ")+1, content.indexOf("d")));
			diceValue = Integer.parseInt(content.substring(content.indexOf("d")+1));
		}catch(Exception e){
			channel.sendMessage("My creation, you do not understand what a dice is do you?").complete();
			return;
		}
		
		rolls = new int[diceAmount];
		
		rollDice(rolls, diceAmount, diceValue);
		
		for(int i = 0; i < rolls.length; i++) {
			message = message + " [" + String.valueOf(rolls[i]) + "]";
		}
		
		channel.sendMessage(message).complete();
	}
	
	public int[] rollDice(int[] list, int amount, int value) {
		int[] rolls = list;
		int random;
		for(int i = 0; i < amount; i++) {
			random = (int)(Math.random()*value)+1;
			rolls[i] = random;
		}
		
		return rolls;
	}
}
