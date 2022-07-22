package net.minecraft.src;

public class YYTileEntityDynamicLightSource extends TileEntity
{
	private int age = 0;
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		age++;
		int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
		if(id == DLMain.lightsourceinvis.blockID && age>3)
		{
			((DLLightSource)DLMain.lightsourceinvis).checkToRemove(worldObj, xCoord, yCoord, zCoord);
		}
	}
	
}
