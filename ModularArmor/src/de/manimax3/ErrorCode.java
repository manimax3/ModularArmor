package de.manimax3;

public enum ErrorCode {
	DESERIALIZATION_Fail,
	Wrong_Player_In_Inventory;
	
	public void out(){
		ModularArmor.console.sendMessage(ModularArmor.PREFIX + this.toString() + " Exception. Contact Developer!");
	}
}
