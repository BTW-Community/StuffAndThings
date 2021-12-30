package net.minecraft.src;

public class YYBlockChest extends FCBlockChest
{
	public YYBlockChest(int id)
	{
		super(id);
	}

	@Override
	public boolean IsIncineratedInCrucible() {
		return false;
	}
}
