package net.minecraft.src;

public class YYBlockHopper extends FCBlockHopper
{
	public YYBlockHopper(int id)
	{
		super(id);
	}
	
	@Override
	public boolean IsIncineratedInCrucible() {
		return false;
	}
}
