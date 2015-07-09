package de.manimax3;

public enum ErrorCode {
	WRONG_Player_In_Inventory;
	
	public void out(){
		ModularArmor.console.sendMessage(ModularArmor.PREFIX + this.toString() + " Exception. Contact Developer!");
	}
}
