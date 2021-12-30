package net.minecraft.src;

public class YYBlockTorchSparklingUnlit extends FCBlockTorchFiniteUnlit
{
	protected YYBlockTorchSparklingUnlit(int id)
	{
		super(id);
		setUnlocalizedName("yyBlockTorchSparklingIdle");
	}
	
	@Override
	protected int GetLitBlockID()
	{
		return YYStuffAndThings.yyBlockTorchSparklingBurning.blockID;
	}
}
