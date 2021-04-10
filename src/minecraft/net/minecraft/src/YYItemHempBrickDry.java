package net.minecraft.src;

public class YYItemHempBrickDry extends FCItemPlacesAsBlock
{
    public YYItemHempBrickDry(int var1)
    {
        super(var1, YYBaitMod.yyBlockHempBrickDry.blockID);
        this.m_bRequireNoEntitiesInTargetBlock = true;
        this.setUnlocalizedName("yyItemBrickHempDry");
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
}
